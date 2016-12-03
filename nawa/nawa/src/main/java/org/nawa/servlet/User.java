package org.nawa.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.Conf;
import org.nawa.common.CookieBox;
import org.nawa.common.JwtToken;
import org.nawa.common.ServletUtil;
import org.nawa.common.UTF8MultipartRequest;
import org.nawa.dao.UserInfoDao;
import org.nawa.data.ResponseCode;
import org.nawa.exception.DuplicateException;
import org.nawa.exception.InsufficientAuthorityException;
import org.nawa.img.ImageResize;
import org.nawa.service.LoginService;
import org.nawa.service.UserPageService;
import org.nawa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWTVerifyException;
import com.google.common.base.Preconditions;
import com.sun.jersey.api.uri.UriTemplate;

public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(User.class);
	
	private UserService userService = UserService.getInstance();
	private LoginService loginService = LoginService.getInstance();
	private UserPageService userPageService = UserPageService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;
		ServletOutputStream outputStream = null;

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			Map<String, String> pathParams=new HashMap<String, String>();

			if(new UriTemplate("/{email}").match(request.getPathInfo(), pathParams)) {
				output=response.getWriter();
				getUser(request, response, output, pathParams);
			} else if(new UriTemplate("/isEmailExists/{email}").match(request.getPathInfo(), pathParams)) {
				output=response.getWriter();
				isEmailExists(request, response, output, pathParams);
			} else if(new UriTemplate("/Relation/Following/{email}").match(request.getPathInfo(), pathParams)) {
				output=response.getWriter();
				getFollowing(request, response, output, pathParams);
			} else if(new UriTemplate("/Relation/Followed/{email}").match(request.getPathInfo(), pathParams)) {
				output=response.getWriter();
				getFollowed(request, response, output, pathParams);
			} else if(new UriTemplate("/Profile/{email}").match(request.getPathInfo(), pathParams)) {
				output=response.getWriter();
				getUserProfile(request, response, output, pathParams);
			} else if(new UriTemplate("/Pic/{email}").match(request.getPathInfo(), pathParams)) {
				outputStream = response.getOutputStream();
				getUserPic(request, response, outputStream, pathParams);
			} else{
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null){
				output.flush();
				output.close();
			} //if
			if(outputStream != null){
				outputStream.flush();
				outputStream.close();
			} //if
		} //finally
	} //doGet
	
	private void getUser(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String userEmail = pathParams.get("email");
		String myEmail = (String) JwtToken.getPayloadValueAs(CookieBox.get(request, "token"), "email", String.class);
		
		try {
			Preconditions.checkNotNull(userEmail, "email is null");
		} catch (NullPointerException e) {
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} //catch
		
		JSONObject user = null;
		if(myEmail == null)
			user = userService.loadUser(userEmail);
		else
			user = userService.loadUserWithRelation(userEmail, myEmail);
			
		if(user == null){
			String msg = "failed to load user " + userEmail;
			ServletUtil.handleError(output, msg, ResponseCode.NO_DATA);
			return;
		} //if
		
		output.println(new JSONObject()
				.put("success", "1")
				.put("user", user).toString());
	} //getUser
	
	private void isEmailExists(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String email = pathParams.get("email");

		try{
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()));
			return;
		} //catch

		boolean isEmailExists=userService.isEmailExists(email);
		output.println(new JSONObject()
			.put("success", "1")
			.put("isEmailExists", isEmailExists));
	} //isEmailExists

	private void getFollowing(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String email = pathParams.get("email");
		
		try {
			Preconditions.checkNotNull(email, "email is null");
		} catch (NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
	
		JSONArray followingUsers = userService.getFollowing(email);
		output.println(new JSONObject()
			.put("success", "1")
			.put("followings", followingUsers).toString());
		return;
	} //getFollowing
	
	private void getFollowed(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String email = pathParams.get("email");
		
		try {
			Preconditions.checkNotNull(email, "email is null");
		} catch (NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
	
		JSONArray followedUsers = userService.getFollowed(email);
		output.println(new JSONObject()
			.put("success", "1")
			.put("followeds", followedUsers).toString());
		return;
	} //getFollowed
	
	private void getUserProfile(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String email = pathParams.get("email");
		
		try {
			Preconditions.checkNotNull(email, "email is null");
		} catch (NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
	
		JSONObject userProfile = userPageService.getUserPage(email);
		
		if(userProfile == null) {
			String msg = String.format("failed to load user profile : %s", email);
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR);
			return;
		} //if
		
		output.println(new JSONObject()
			.put("success", "1")
			.put("profile", userProfile).toString());
		return;
	} //getFollowed
	
	private void getUserPic(HttpServletRequest request, HttpServletResponse response, ServletOutputStream outputStream, Map<String, String> pathParams) throws FileNotFoundException, IOException{
		String email = pathParams.get("email");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		
		try {
			Preconditions.checkNotNull(email, "email is null");
		} catch (NullPointerException e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			response.setStatus(HttpStatus.SC_BAD_REQUEST);
			return;
		} //catch
		
		response.setContentType("image/jpeg");
		byte[] picBytes = userService.getUserPic(email, width, height);
		outputStream.write(picBytes);
	} //getUserPic
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter output = null;
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output = response.getWriter();
			
			UriTemplate postUserRelationUri =new UriTemplate("/Relation");
			Map<String, String> pathParams=new HashMap<String, String>();

			if(request.getPathInfo()==null){
				postUser(request, response, output);
			} else if(postUserRelationUri.match(request.getPathInfo(), pathParams)){
				follow(request, response, output);
			} else{
				logger.error("invalid path info : " + request.getPathInfo());
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		}
		catch(Exception e)
		{
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject().put("success", "0")
						.put("rescode", ResponseCode.UNKNOWN_ERROR)
						.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		}
		finally
		{
			if(output!=null)
			{
				output.flush();
				output.close();
			} //if
		} //finally
	} //doPost

	private void postUser(HttpServletRequest request, HttpServletResponse response, PrintWriter output) throws IOException{
		UTF8MultipartRequest multiRequest = new UTF8MultipartRequest(request);
		
		String email = multiRequest.getParameter("email"); 
		String password = multiRequest.getParameter("password");
		String name = multiRequest.getParameter("name");
		if(name!=null) name = URLDecoder.decode(name, "utf8");
		int gender = Integer.parseInt(multiRequest.getParameter("gender"));
		String fbToken = request.getParameter("facebook_access_token"); //후에 다시!
		
		File rfile = multiRequest.getFile("userPic"); //사용자가 넣은 사진 받아서

		if(rfile!=null){ //제대로 받아졌다면
			File folderPath = new File(Conf.getUserPicPath(), email);
			folderPath.mkdirs();
			int[][] sizes = { {120, 120}, {70, 70}, {60, 60}, {50, 50}, {40, 40}};

			for(int[] size : sizes){
				File newFile = new File(folderPath, String.format("%s_%s.%s", size[0], size[1], FilenameUtils.getExtension(rfile.getName()))); 
				ImageResize.resize(rfile, size[0], size[1], newFile);
			} //for size
		} //if

		try {
			Preconditions.checkNotNull(email, "email is null");
			Preconditions.checkNotNull(password, "password is null");
			Preconditions.checkNotNull(name, "name is null");
			Preconditions.checkNotNull(gender, "gender is null");

			Pattern p = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
			Matcher m = p.matcher(email);
			Preconditions.checkArgument(m.matches(), "invalid parameter, email : " + email);
			Preconditions.checkArgument(password.length() <= 128, "invalid parameter, password : " + password);
			Preconditions.checkArgument(name.length() <= 20, "invalid parameter, name : " + name);
			Preconditions.checkArgument(fbToken==null || fbToken.length() <= 100, "invalid parameter, fb_token : " + fbToken);
			Preconditions.checkArgument(gender==UserInfoDao.GENDER_MALE || gender==UserInfoDao.GENDER_FEMALE, "invalid parameter, gender : " + gender);
		} catch(NullPointerException | IllegalArgumentException e) {
			logger.warn(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()));
			output.println(new JSONObject().put("success", "0")
					.put("rescode", ResponseCode.PRECONDITION_FAILED)
					.put("msg", e.getMessage()));
			return;
		} //catch

		try {
			userService.insert(email, password, name, gender, new Date(), fbToken);
		} catch(DuplicateException e){
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.DUPLICATED);
			return;
		} //catch

		String loginToken=loginService.generateLoginToken(email, password);

		if(loginToken == null){
			String msg = String.format("failed to create login token for %s", email);
			logger.error(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode",ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg));
			return;
		} //if

		logger.info("{} logined", email);
		output.println(new JSONObject().put("success", "1").toString());
		CookieBox.set(response, "token", loginToken, -1);
		return;
	} //postUser
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			UriTemplate editUserUri = new UriTemplate("/{email}");
			Map<String, String> pathParams=new HashMap<String, String>();
			
			if(editUserUri.match(request.getPathInfo(), pathParams)) {
				editUser(request, response, output, pathParams);
			} else{
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null){
				output.flush();
				output.close();
			} //if
		} //finally
	} //doPut

	private void editUser(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws IOException{
		UTF8MultipartRequest multiPartRequest = new UTF8MultipartRequest(request);

		String token = CookieBox.get(request, "token");
		String email = pathParams.get("email");
		String password = multiPartRequest.getParameter("password");
		String name = multiPartRequest.getParameter("name"); 
		if(name!=null) name = URLDecoder.decode(name, "utf8");
		int gender = Integer.parseInt(multiPartRequest.getParameter("gender"));
		String fbToken = request.getParameter("facebook_access_token"); //후에 다시!
		File rfile=multiPartRequest.getFile("userPic");
		
		String requestEmail = null;

		if(rfile != null){
			File folderPath = new File(Conf.getUserPicPath(), email);
			folderPath.mkdirs();
			int[][] sizes = { {120, 120}, {70, 70}, {60, 60}, {50, 50}, {40, 40}};

			for(int[] size : sizes){
				File newFile = new File(folderPath, String.format("%s_%s.%s", size[0], size[1], FilenameUtils.getExtension(rfile.getName()))); 
				ImageResize.resize(rfile, size[0], size[1], newFile);
			} //for size
		} //if
		
		try {
			Preconditions.checkNotNull(email, "email is null");
			Preconditions.checkNotNull(password, "password is null");
			Preconditions.checkNotNull(name, "name is null");
			Preconditions.checkNotNull(gender, "gender is null");
			Preconditions.checkNotNull(token, "token is null");
			requestEmail = (String) new JwtToken(token).get("email");
			Preconditions.checkNotNull(requestEmail, "requestEmail is null");
			
			Pattern p = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
			Matcher m = p.matcher(email);
			Preconditions.checkArgument(m.matches(), "invalid parameter, email : " + email);
			Preconditions.checkArgument(password.length() <= 128, "invalid parameter, password : " + password);
			Preconditions.checkArgument(name.length() <= 20, "invalid parameter, name : " + name);
			Preconditions.checkArgument(fbToken==null || fbToken.length() <= 100, "invalid parameter, fb_token : " + fbToken);
			Preconditions.checkArgument(gender==UserInfoDao.GENDER_MALE || gender==UserInfoDao.GENDER_FEMALE, "invalid parameter, gender : " + gender);
		} catch(NullPointerException | IllegalArgumentException e) {
			logger.warn(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()));
			output.println(new JSONObject().put("success", "0")
					.put("rescode", ResponseCode.PRECONDITION_FAILED)
					.put("msg", e.getMessage()));
			return;
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException | JWTVerifyException e) {
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			logger.error(msg);
			output.println(new JSONObject().put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", msg));
			return;
		} //catch

		try {
			userService.update(requestEmail, email, password, name, gender, fbToken);
		} catch (InsufficientAuthorityException e) {
			String msg = String.format("insufficient authority, requestEmail:%s, email:%s", requestEmail, email);
			ServletUtil.handleError(output, msg, ResponseCode.UNAUTHORIZED);
		} //catch
		
		String loginToken=loginService.generateLoginToken(email, password);

		if(loginToken == null){
			String msg = String.format("failed to create login token for %s", email);
			logger.error(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode",ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg));
			return;
		} //if

		logger.info("{} logined", email);
		output.println(new JSONObject().put("success", "1").toString());
		CookieBox.set(response, "token", loginToken, -1);
		return;
	} //postUser
	
	private void follow(HttpServletRequest request, HttpServletResponse response, PrintWriter output){
		String token = CookieBox.get(request, "token");
		String targetEmail = request.getParameter("targetEmail");
		String email = null;

		try{
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(targetEmail, "targetEmail is null");
			email = (String) new JwtToken(token).get("email");
			Preconditions.checkNotNull(email, "invalid token");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject().put("success", "0")
					.put("rescode", ResponseCode.PRECONDITION_FAILED)
					.put("msg", e.getMessage()));
			return;
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			logger.warn(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg).toString());
			return;
		} //catch

		boolean isAlreadyFollow = userService.isAFollowingB(email, targetEmail);

		if(isAlreadyFollow == true){
			String msg = String.format("%s already follow %s", email, targetEmail);
			logger.warn(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.ALREADY_APPLIED)
				.put("msg", msg));
			return;
		} //if

		try {
			userService.addFollowing(email, targetEmail);
		} catch (IllegalAccessException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		logger.info("{} follow {}", email, targetEmail);
		output.println(new JSONObject().put("success", "1"));
		return;
	} //follow

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output = response.getWriter();
			UriTemplate unfollowUri = new UriTemplate("/Relation/{targetEmail}");
			Map<String, String> pathParams = new HashMap<String, String>();

			if(request.getPathInfo() == null){
				//TODO
			} else if(unfollowUri.match(request.getPathInfo(), pathParams)) {
				unfollow(request, response, output, pathParams);
			} else{
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null){
				output.flush();
				output.close();
			} //if
		} //finally
	} //doDelete

	private void unfollow(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String token = CookieBox.get(request, "token");
		String targetEmail = pathParams.get("targetEmail");
		String email = null;

		try{
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(targetEmail, "targetEmail is null");
			email = (String) new JwtToken(token).get("email");
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			logger.warn(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg).toString());
		} //catch
		
		boolean isFollowing = userService.isAFollowingB(email, targetEmail);
		if(isFollowing == false){
			logger.warn("failed to unfollow {} to {}, {} not following {}", email, targetEmail, email, targetEmail);
			output.println(new JSONObject().put("success", "0")
					.put("rescode", ResponseCode.PRECONDITION_FAILED)
					.put("msg", "not following").toString());
			return;
		} //if

		userService.unfollow(email, targetEmail);

		logger.info("{} unfollow {}", email, targetEmail);
		output.println(new JSONObject().put("success", "1").toString());
	} //unfollow
} //class

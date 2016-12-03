package org.nawa.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.nawa.dao.ProjectInfoDao;
import org.nawa.data.ResponseCode;
import org.nawa.exception.DuplicateException;
import org.nawa.exception.InsufficientAuthorityException;
import org.nawa.exception.InvalidOperationException;
import org.nawa.exception.NotExistsException;
import org.nawa.img.ImageResize;
import org.nawa.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.uri.UriTemplate;

public class Project extends HttpServlet {
	private static final long serialVersionUID = -6585773365208672458L;
	private static final Logger logger = LoggerFactory.getLogger(Project.class);
	private ProjectService projectService = ProjectService.getInstance();

	public Project() {
		super();
	} // INIT

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;
		ServletOutputStream outputStream = null;

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams=new HashMap<String, String>();
			
			if(new UriTemplate("/{projectId}").match(pathInfo, pathParams)){
				output=response.getWriter();
				getProject(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/List/{page}").match(pathInfo, pathParams)){
				output=response.getWriter();
				getList(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/isParticipated/{projectId}").match(pathInfo, pathParams)){
				output=response.getWriter();
				isParticipated(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/WaitingAcceptUsers/{projectId}").match(pathInfo, pathParams)){
				output=response.getWriter();
				getWaitings(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/ParticipatingUsers/{projectId}").match(pathInfo, pathParams)){
				output=response.getWriter();
				getParticipatingUsers(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/ParticipatingProjects/{email}").match(pathInfo, pathParams)){
				output=response.getWriter();
				getParticipatingProjects(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/LeadingProjects/{email}").match(pathInfo, pathParams)){
				output=response.getWriter();
				getLeadingProjects(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/Pic/{id}").match(pathInfo, pathParams)){
				outputStream = response.getOutputStream();
				getProjectPic(request, response, outputStream, pathParams);
			} else{
				String msg = String.format("invalid path info : " + request.getPathInfo());
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR, e);
		} finally{
			if(output != null) {
				output.flush();
				output.close();
			} //if
			if(outputStream != null){
				outputStream.flush();
				outputStream.close();
			} //if
		} //finally
	} //doGet
	
	
	private void getProject(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws NotExistsException {
		String projectId = pathParams.get("projectId");
		
		try {
			Preconditions.checkNotNull(projectId, "projectId is null");
		} catch(NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
		
		logger.debug("project id : {}", projectId);

		JSONObject foundProject = projectService.get(projectId);

		if(foundProject == null) {
			String msg = String.format("failed to get project : %s", projectId);
			ServletUtil.handleError(output, msg, ResponseCode.PRECONDITION_FAILED);
			return;
		} //if
		
		output.println(new JSONObject()
			.put("success", "1")
			.put("project", new JSONObject(foundProject.toString())));
	} //getProject
	
	private void getList(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) {
		int page;
		
		try {
			String pageStr = pathParams.get("page");
			Preconditions.checkArgument(pageStr != null, "page is null");
			page = Integer.parseInt(pageStr);
			Preconditions.checkArgument(page > 0, "invalid page : " + pageStr);
		} catch(IllegalArgumentException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
		
		JSONArray projectJsons = projectService.list(page);
		output.println(new JSONObject()
			.put("success", "1")
			.put("projects", projectJsons));
	} //getList

	private void isParticipated(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws NotExistsException {
		String projectId = pathParams.get("projectId");
		String email = request.getParameter("email");

		try {
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		boolean result = projectService.isParticipated(projectId, email);
		output.println(new JSONObject()
			.put("success", "1")
			.put("isParticipated", result).toString());
	}//isParticipated

	private void getWaitings(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws NotExistsException {
		String projectId = pathParams.get("projectId");
		String token = CookieBox.get(request, "token");
		String email = null;

		try {
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(token, "token is null");
			email = (String) JwtToken.getPayloadValue(token, "email");
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		JSONArray waitings = projectService.getWaitingAcceptUsers(email, projectId);

		if(waitings == null) {
			String msg = String.format("failed to load waitingAcceptUsers, email : %s, project : %s", email, projectId);
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR);
			return;
		} //if

		output.println(new JSONObject()
			.put("success", "1")
			.put("waitings", new JSONArray(waitings.toString())));
	}//getWaitings
	
	private void getParticipatingUsers(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws NotExistsException {
		String projectId = pathParams.get("projectId");

		try {
			Preconditions.checkNotNull(projectId, "projectId is null");
		} catch(NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		JSONArray participatingUsers = projectService.getParticipatingUsers(projectId);

		if(participatingUsers == null) {
			String msg = String.format("failed to load participatingUsers, project : %s", projectId);
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR);
			return;
		} //if

		output.println(new JSONObject()
			.put("success", "1")
			.put("participatings", participatingUsers).toString());
	} //getParticipatingUser
	
	private void getParticipatingProjects(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String email = pathParams.get("email");
		boolean includeNotAccepted = false;
		if("true".equals(request.getParameter("include-not-accepted")))
			includeNotAccepted = true;
		
		try{
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e){
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
		
		JSONArray participatingProjects = projectService.getParticipatingProjects(email, includeNotAccepted);
		
		if(participatingProjects == null){
			String msg = String.format("failed to load participatingProjects, email : %s", email);
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR);
			return;
		} //if
		
		output.println(new JSONObject()
			.put("success", "1")
			.put("participatings", participatingProjects).toString());
	} //getparticipatingProjects
	
	private void getLeadingProjects(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String email = pathParams.get("email");
		
		try{
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e){
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch
		
		JSONArray leadingProjects = projectService.getLeadingProjects(email);
		
		if(leadingProjects == null){
			String msg = String.format("failed to load leadingProjects, email : %s", email);
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR);
			return;
		} //if
		
		output.println(new JSONObject()
			.put("success", "1")
			.put("leading-projects", leadingProjects).toString());
	} //getLeadingProjects
	
	private void getProjectPic(HttpServletRequest request, HttpServletResponse response, ServletOutputStream outputStream, Map<String, String> pathParams) throws FileNotFoundException, NotExistsException, IOException{
		String id = pathParams.get("id");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		
		try {
			Preconditions.checkNotNull(id, "project id is null");
		} catch (NullPointerException e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			response.setStatus(HttpStatus.SC_BAD_REQUEST);
			return;
		} //catch
		
		response.setContentType("image/jpeg");
		byte[] picBytes = projectService.getProjectPic(id, width, height);
		outputStream.write(picBytes);
	} //getProjectPic
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;
		
		try {
			output=response.getWriter();
	
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams = new HashMap<String, String>();
			
			if(pathInfo == null){
				postProject(request, response, output);
				return;
			} else if(new UriTemplate("/Participate/{projectId}").match(pathInfo, pathParams)){
				participate(request, response, output, pathParams);
				return;
			} else if(new UriTemplate("/AcceptParticipate/{projectId}").match(pathInfo, pathParams)){
				acceptParticiate(request, response, output, pathParams);
				return;
			} else {
				String msg = String.format("invalid path info : %s", request.getPathInfo());
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR, e);
		} finally{
			if(output!=null) {
				output.flush();
				output.close();
			} //if
		} //finally
	} //doPost

	private void postProject(HttpServletRequest request, HttpServletResponse response, PrintWriter output) throws Exception {
		UTF8MultipartRequest multiRequest = new UTF8MultipartRequest(request);
		
		String token = CookieBox.get(request, "token");
		String id = ProjectInfoDao.generateId();
		String title = multiRequest.getParameter("title");
		String description = multiRequest.getParameter("description");
		String descriptionTitle = multiRequest.getParameter("description_title");
		String leaderEmail = null;
		Date regdate = new Date();
		String place = multiRequest.getParameter("place");
		Date meetingDate = multiRequest.getParameterAsDate("meeting_date", "yyyy-MM-dd");
		String meetingTime = multiRequest.getParameter("meeting_time");
		Integer maxUserCount = multiRequest.getParameterAsInt("max_user_count");
		Date recruitDueDate = multiRequest.getParameterAsDate("recruit_due_date", "yyyy-MM-dd");
		Integer longProject = multiRequest.getParameterAsInt("long_project");
		File projectPics = multiRequest.getFile("project_pics");
		
		try{
			Preconditions.checkArgument(token != null, "token is null");
			Object leaderEmailObj = JwtToken.getPayloadValue(token, "email");
			Preconditions.checkArgument(leaderEmailObj != null, "failed to find email in token");
			leaderEmail = (String) leaderEmailObj;
			Preconditions.checkArgument(title != null, "title is null");
			Preconditions.checkArgument(description != null, "description is null");
			Preconditions.checkArgument(descriptionTitle != null, "description_title is null");
			Preconditions.checkArgument(place!= null, "place is null");
			Preconditions.checkArgument(meetingDate != null, "meeting_date is null");
			Preconditions.checkArgument(meetingTime != null, "meeting_time is null");
			Preconditions.checkArgument(maxUserCount != null, "max_user_count is null");
			Preconditions.checkArgument(recruitDueDate != null, "recruit_due_date is null");
			Preconditions.checkArgument(longProject != null, "long_project is null");
			
			Preconditions.checkArgument(longProject == ProjectInfoDao.LONG_PROJECT_LONG ||
					longProject == ProjectInfoDao.LONG_PROJECT_SHORT , "invalid long_project: " + longProject);
		} catch(IllegalArgumentException e){
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
		} //catch
		
		if(projectPics != null){
			File folderPath = new File(Conf.getProjectPicPath(), id);
			folderPath.mkdirs();
			ImageResize.resize(projectPics, 380, 300, new File(folderPath, String.format("%s_%s.%s", 380, 300, FilenameUtils.getExtension(projectPics.getName()))));
			ImageResize.resize(projectPics, 60, 60, new File(folderPath, String.format("%s_%s.%s", 60, 60, FilenameUtils.getExtension(projectPics.getName()))));
		} //if
		
		JSONObject projectInfo = new JSONObject();
		projectInfo.put("id", id);
		projectInfo.put("title", title);
		projectInfo.put("description", description);
		projectInfo.put("description_title", descriptionTitle);
		projectInfo.put("leader_email", leaderEmail);
		projectInfo.put("regdate", regdate);
		projectInfo.put("place", place);
		projectInfo.put("meeting_date", meetingDate);
		projectInfo.put("meeting_time", meetingTime);
		projectInfo.put("max_user_count", maxUserCount);
		projectInfo.put("recruit_due_date", recruitDueDate);
		projectInfo.put("long_project", longProject);
		
		try{
			projectService.insert(projectInfo);
			output.println(new JSONObject().put("success", "1").toString());
		} catch(DuplicateException e){
			logger.warn(e.getMessage());
			if (output != null)
				output.println(new JSONObject().put("success", "0")
						.put("rescode", ResponseCode.DUPLICATED)
						.put("msg", e.getMessage()));
		} //catch
	}//postProject
	
	private void participate(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws NotExistsException, DuplicateException {
		String projectId = pathParams.get("projectId");
		String token = CookieBox.get(request, "token");
		String email = null;

		try {
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(projectId, "projectId is null");
			email = (String) JwtToken.getPayloadValue(token, "email");
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		projectService.participate(projectId, email);
		output.println(new JSONObject().put("success", "1").toString());
	}//participate
	
	private void acceptParticiate(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws NotExistsException {
		String projectId = pathParams.get("projectId");
		String participationUserEmail = request.getParameter("participationUserEmail");
		boolean accept = Boolean.parseBoolean(request.getParameter("accept"));
		String token =CookieBox.get(request, "token");
		String email = null;

		try {
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(participationUserEmail, "participatinoUserEmail is null");
			email = (String) JwtToken.getPayloadValue(token, "email");
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e) {
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		try{
			projectService.acceptParticipation(email, participationUserEmail, projectId, accept);
		} catch(InsufficientAuthorityException e){
			logger.warn("insufficient authority, leading user : {}, project : {}", email, projectId);
			output.println(new JSONObject().put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", "insufficient authority").toString());
			return;
		} //catch

		output.println(new JSONObject().put("success", "1").toString());
		return;
	}//acceptParticiate

	//project Update
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams=new HashMap<String, String>();
			
			if(pathInfo == null){
				putProject(request, response, output, pathParams);
				return;
			} else {
				String msg = String.format("invalid path info : %s", pathInfo);
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e) {
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR, e);
		} finally {
			if(output!=null) {
				output.flush();
				output.close();
			} //if
		} //finally
	} //doPut

	private void putProject(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws Exception {
		UTF8MultipartRequest multiRequest = new UTF8MultipartRequest(request);
		
		String token = CookieBox.get(request, "token");
		String id = multiRequest.getParameter("id");
		String title = multiRequest.getParameter("title");
		String description = multiRequest.getParameter("description");
		String descriptionTitle = multiRequest.getParameter("description_title");
		String leaderEmail = null;
		String place = multiRequest.getParameter("place");
		String meetingDate = multiRequest.getParameter("meeting_date");
		String meetingTime = multiRequest.getParameter("meeting_time");
		Integer maxUserCount = multiRequest.getParameterAsInt("max_user_count");
		Date recruitDueDate = multiRequest.getParameterAsDate("recruit_due_date", "yyyy-MM-dd");
		Integer longProject = multiRequest.getParameterAsInt("long_project");
		File projectPics = multiRequest.getFile("project_pics");
		
		try{
			Preconditions.checkArgument(id != null, "id is null");
			Preconditions.checkArgument(token != null, "token is null");
			Object leaderEmailObj = JwtToken.getPayloadValue(token, "email");
			Preconditions.checkArgument(leaderEmailObj != null, "failed to find email in token");
			leaderEmail = (String) leaderEmailObj;
			Preconditions.checkArgument(title != null, "title is null");
			Preconditions.checkArgument(description != null, "description is null");
			Preconditions.checkArgument(descriptionTitle != null, "description_title is null");
			Preconditions.checkArgument(place!= null, "place is null");
			Preconditions.checkArgument(meetingDate != null, "meeting_date is null");
			Preconditions.checkArgument(meetingTime != null, "meeting_time is null");
			Preconditions.checkArgument(maxUserCount != null, "max_user_count is null");
			Preconditions.checkArgument(recruitDueDate != null, "recruit_due_date is null");
			Preconditions.checkArgument(longProject != null, "long_project is null");
			
			Preconditions.checkArgument(longProject == ProjectInfoDao.LONG_PROJECT_LONG ||
					longProject == ProjectInfoDao.LONG_PROJECT_SHORT , "invalid long_project: " + longProject);
		} catch(IllegalArgumentException e){
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
		} //catch
		
		if(projectPics != null){
			File folderPath = new File(Conf.getProjectPicPath(), id);
			folderPath.mkdirs();
			ImageResize.resize(projectPics, 300, 100, new File(folderPath, String.format("%s_%s.%s", 300, 100, FilenameUtils.getExtension(projectPics.getName()))));
		} //if
		
		JSONObject projectInfo = new JSONObject();
		projectInfo.put("id", id);
		projectInfo.put("title", title);
		projectInfo.put("description", description);
		projectInfo.put("description_title", description);
		projectInfo.put("leader_email", leaderEmail);
		projectInfo.put("place", place);
		projectInfo.put("meeting_date", meetingDate);
		projectInfo.put("meeting_time", meetingTime);
		projectInfo.put("max_user_count", maxUserCount);
		projectInfo.put("recruit_due_date", recruitDueDate);
		projectInfo.put("long_project", longProject);
		
		try {
			projectService.update(projectInfo);
			output.println(new JSONObject().put("success", "1").toString());
		} catch (InsufficientAuthorityException e) {
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
		} //catch
	}//putProject
	
	//project delete
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output = response.getWriter();
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams=new HashMap<String, String>();
			
			if(new UriTemplate("/Participate/{projectId}").match(pathInfo, pathParams)){
				unparticipateProject(request, response, output, pathParams);
				return;
			} else{
				String msg = String.format("invalid path info : %s", pathInfo);
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			ServletUtil.handleError(output, msg, ResponseCode.UNKNOWN_ERROR, e);
		} finally{
			if(output!=null) {
				output.flush();
				output.close();
			} //if
		} //finally
	} //doDelete
	
	private void unparticipateProject(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams) throws IOException, InvalidOperationException, NotExistsException {
		String projectId = pathParams.get("projectId");
		String token = CookieBox.get(request, "token");
		String email = null;

		try{
			Preconditions.checkNotNull(token, "token is null");
			Preconditions.checkNotNull(projectId, "projectId is null");
			email = (String) JwtToken.getPayloadValue(token, "email");
			Preconditions.checkNotNull(email, "email is null");
		} catch(NullPointerException e){
			ServletUtil.handleError(output, e.getMessage(), ResponseCode.PRECONDITION_FAILED);
			return;
		} //catch

		projectService.unparticipate(projectId, email);
		output.println(new JSONObject().put("success", "1").toString());
	}//deleteProject
} // class
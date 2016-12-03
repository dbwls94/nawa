package org.nawa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.CookieBox;
import org.nawa.common.JwtToken;
import org.nawa.data.ResponseCode;
import org.nawa.exception.InvalidOperationException;
import org.nawa.exception.NotExistsException;
import org.nawa.service.ProjectReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.uri.UriTemplate;

public class Comment extends HttpServlet {
	private static final long serialVersionUID = 179798828666099571L;
	private static final Logger logger = LoggerFactory.getLogger(Comment.class);
	private ProjectReplyService projectReplyService = ProjectReplyService.getInstance();

	public Comment() {
		super();
	} // INIT

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			if(request.getPathInfo() == null){
				postComment(request, response, output);
			} else{
				logger.error("invalid path info : " + request.getPathInfo());
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally {
			if (output != null) {
				output.flush();
				output.close();
			} // if
		} // finally	
	} //doPost
	
	private void postComment(HttpServletRequest request, HttpServletResponse response, PrintWriter output) throws NotExistsException {
		String token=CookieBox.get(request, "token");
		String projectId=request.getParameter("projectId");
		String content=request.getParameter("content");
		String parentSeqStr=request.getParameter("parent_seq");
		Long parentSeq=parentSeqStr==null ? null : Long.parseLong(parentSeqStr);
		String userEmail=null;
		Date regDate = new Date();

		try {
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(content, "content is null");

			JwtToken jwtToken=new JwtToken(token);
			userEmail=(String) jwtToken.get("email");
			Preconditions.checkNotNull(userEmail, "failed to find email from token");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			logger.error(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg).toString());
		} //catch
		
		projectReplyService.insert(userEmail, projectId, content, parentSeq, regDate);

		output.println(new JSONObject().put("success", "1").toString());
		return;
	} //postComment

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			if(request.getPathInfo() == null){
				updateComment(request, response, output);
			} else{
				logger.error("invalid path info : " + request.getPathInfo());
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally {
			if (output != null) {
				output.flush();
				output.close();
			} // if
		} // finally	
	} //doPut
	
	private void updateComment(HttpServletRequest request, HttpServletResponse response, PrintWriter output) throws NotExistsException, InvalidOperationException{
		String token=CookieBox.get(request, "token");
		String seq = request.getParameter("seq");
		String projectId = request.getParameter("projectId");
		String content = request.getParameter("content");
		String userEmail=null;
		Long seqLong = null;

		try {
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(seq, "seq is null");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(content, "content is null");
			
			seqLong = Long.parseLong(seq);

			JwtToken jwtToken=new JwtToken(token);
			userEmail=(String)jwtToken.get("email");
			Preconditions.checkNotNull(userEmail);
		} catch(NullPointerException e) {
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} catch(NumberFormatException e) {
			String msg = "invalid seq, seq : " + seq;
			logger.warn(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", msg).toString());
			return;
		} catch(Exception e) {
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			logger.error(msg, e);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg).toString());
			return;
		} //catch

		projectReplyService.update(seqLong, projectId, userEmail, content);
		output.println(new JSONObject().put("success", "1").toString());
	} //updateComment

	//Comment delete
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
		
			output=response.getWriter();
			
			if(request.getPathInfo() == null){
				deleteComment(request, response, output);
			} else{
				logger.error("invalid path info : " + request.getPathInfo());
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.BAD_REQUEST)
					.put("msg", "failed to parse path parameter").toString());
			} //if
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally {
			if (output != null)
			{
				output.flush();
				output.close();
			} // if
		} // finally	
	} //doDelete
	
	private void deleteComment(HttpServletRequest request, HttpServletResponse response, PrintWriter output) throws InvalidOperationException, NotExistsException{
		String seq = request.getParameter("seq");
		String projectId=request.getParameter("projectId");
		String token = CookieBox.get(request, "token");
		Long seqLong = null;

		String userEmail=null;
		try{
			Preconditions.checkNotNull(token, "token is null, unauthorized");
			Preconditions.checkNotNull(projectId, "projectId is null");
			
			seqLong = Long.parseLong(seq);

			JwtToken jwtToken=new JwtToken(token);
			userEmail=(String)jwtToken.get("email");
			Preconditions.checkNotNull(userEmail, "failed to found email");
		} catch(NullPointerException e){
			logger.error(e.getMessage(), e);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} catch(NumberFormatException e){
			String msg = String.format("failed to parse seq, seq : " + seq);
			logger.warn(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", msg).toString());
			return;
		} catch(Exception e){
			String msg = String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage());
			logger.error(msg);
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.UNKNOWN_ERROR)
				.put("msg", msg).toString());
			return;
		} //catch

		projectReplyService.remove(userEmail, projectId, seqLong);
		output.println(new JSONObject().put("success", "1").toString());
	} //deleteComment
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			UriTemplate commentUri=new UriTemplate("/{projectId}");
			Map<String, String> pathParams=new HashMap<String, String>();

			if(commentUri.match(request.getPathInfo(), pathParams)){
				getComment4Project(request, response, output, pathParams);
			} else{
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", "failed to parse path parameter").toString());
				return;
			} //if
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject()
					.put("success", "0")
					.put("rescode", ResponseCode.UNKNOWN_ERROR)
					.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally {
			if (output != null) {
				output.flush();
				output.close();
			} // if
		} // finally
	} // doGet
	
	private void getComment4Project(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String projectId=pathParams.get("projectId");
		
		JSONArray comments = projectReplyService.getReplyByProjectId(projectId);

		if(comments==null){
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.UNKNOWN_ERROR)
				.put("msg", "failed to load comments").toString());
			return;
		} else{
			JSONObject retJson=new JSONObject().put("success", "1");
			retJson.put("comments", comments);
			output.println(retJson.toString());
			return;
		} //if	
	} //getComment4Project
} // class
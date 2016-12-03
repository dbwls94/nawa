package org.nawa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.CookieBox;
import org.nawa.common.ServletUtil;
import org.nawa.data.ResponseCode;
import org.nawa.etc.NawaApiDoc;
import org.nawa.rdb.ConnectionSource;
import org.nawa.rdb.DbHandler;
import org.nawa.rdb.SchemaChecker;
import org.nawa.service.AdminService;
import org.nawa.service.ProjectReplyService;
import org.nawa.service.ProjectService;
import org.nawa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.object.RdbmsOperation;

import com.sun.jersey.api.uri.UriTemplate;

public class Admin extends HttpServlet {
	private static final long serialVersionUID = 3716182089256021019L;
	private static final Logger logger = LoggerFactory.getLogger(Admin.class);
	
	private AdminService adminService=AdminService.getInstance();
	private UserService userService=UserService.getInstance();
	private ProjectService projectService=ProjectService.getInstance();
	private ProjectReplyService projectReplyService=ProjectReplyService.getInstance();
	
	public Admin() {
		super();
	} //INIT

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;
		
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams=new HashMap<String, String>();
			
			if(new UriTemplate("/UserInfo").match(pathInfo, pathParams)){
				JSONArray userInfos = userService.loadAllUsers(true);
				output.println(new JSONObject().put("success", "1").put("userInfos", userInfos).toString());
				return;
			} else if(new UriTemplate("/ProjectInfo").match(pathInfo, pathParams)){
				JSONArray projectInfos = projectService.loadAll();
				output.println(new JSONObject().put("success", "1").put("projectInfos", projectInfos).toString());
				return;
			} else if(new UriTemplate("/ProjectReply").match(pathInfo, pathParams)){
				JSONArray replies = projectReplyService.getAllReply();
				output.println(new JSONObject().put("success", "1").put("projectReplies", replies).toString());
				return;
			} else if(new UriTemplate("/Test").match(pathInfo, pathParams)){
				String name = request.getParameter("name");
				ConnectionSource.getJdbcTemplate().update("update user_info set name = ? where email = 'test5@test.com'", name);
				output.println(new JSONObject().put("success", "1").put("name", name).toString());
			} else if(new UriTemplate("/ApiDoc").match(pathInfo, pathParams)){
				output.println(new JSONObject().put("success", "1").put("doc", NawaApiDoc.getApiDoc()).toString());
				return;
			} else{
				String msg = String.format("failed to parse path parameter : %s", request.getPathInfo());
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject().put("success", "0").
						put("rescode", ResponseCode.UNKNOWN_ERROR).
						put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null){
				output.flush();
				output.close();
			} //if
		} //finally
	} //doGet

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;
		
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams=new HashMap<String, String>();
	
			if(new UriTemplate("/InsertRandomUserInfo/{count}").match(pathInfo, pathParams)){
				String count=pathParams.get("count");
				adminService.insertRandomUserInfo(Integer.parseInt(count));
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/InsertRandomProjectInfo/{count}").match(pathInfo, pathParams)){
				String count=pathParams.get("count");
				adminService.insertRandomProjectInfo(Integer.parseInt(count));
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/InsertRandomComment/{count}").match(pathInfo, pathParams)){
				String count=pathParams.get("count");
				adminService.insertRandomComments(Integer.parseInt(count));
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/CreateTables").match(pathInfo, pathParams)){
				SchemaChecker.check();
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/Test").match(pathInfo, pathParams)){
				String key = request.getParameter("key");
				String value = request.getParameter("value");
				logger.debug("cp1, key : {}, value : {}", key, value);
				CookieBox.set(response, key, value, 0);
			} else{
				String msg = String.format("failed to parse path parameter : %s", request.getPathInfo());
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject().put("success", "0").put("rescode", ResponseCode.UNKNOWN_ERROR).put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null){
				output.flush();
				output.close();
			} //if
		} //finally
	} //doPost

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;
		
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			String pathInfo = request.getPathInfo();
			Map<String, String> pathParams=new HashMap<String, String>();
			
			if(new UriTemplate("/UserInfo/{email}").match(pathInfo, pathParams)){
				String email=pathParams.get("email");
				userService.remove(email);
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/ProjectInfo/{id}").match(pathInfo, pathParams)){
				String id=pathParams.get("id");
				projectService.remove(id);
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/ProjectReply/{projectId}/{seq}").match(pathInfo, pathParams)){
				String projectId = pathParams.get("projectId");
				long seq = Long.parseLong(pathParams.get("seq"));
				String email  = request.getParameter("email");
				projectReplyService.remove(email, projectId, seq);
				output.println(new JSONObject().put("success", "1"));
				return;
			} else if(new UriTemplate("/DropAllTables").match(pathInfo, pathParams)){
				SchemaChecker.dropAllTables();
				output.println(new JSONObject().put("success", "1"));
			} else{
				String msg = String.format("failed to parse path parameter : %s", request.getPathInfo());
				ServletUtil.handleError(output, msg, ResponseCode.BAD_REQUEST);
				return;
			} //if
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass(), e.getMessage()), e);
			if (output != null)
				output.println(new JSONObject().put("success", "0").put("rescode", ResponseCode.UNKNOWN_ERROR).put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null){
				output.flush();
				output.close();
			} //if
		} //finally
	} //doDelete
} // class
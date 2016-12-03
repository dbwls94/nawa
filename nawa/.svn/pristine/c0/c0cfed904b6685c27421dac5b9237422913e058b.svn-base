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
import org.nawa.common.JwtToken;
import org.nawa.data.ResponseCode;
import org.nawa.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.uri.UriTemplate;

public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Search.class);
	
	private SearchService searchService = SearchService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output=null;

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			UriTemplate searchProjectUri = new UriTemplate("/Project/{keyword}");
			UriTemplate searchMyProjectUri = new UriTemplate("/MyProject/{keyword}");
			UriTemplate searchUserUri = new UriTemplate("/User/{keyword}");
			Map<String, String> pathParams=new HashMap<String, String>();

			if(searchProjectUri.match(request.getPathInfo(), pathParams)){
				searchProject(request, response, output, pathParams);
				return;
			} else if(searchMyProjectUri.match(request.getPathInfo(), pathParams)){
				searchMyProject(request, response, output, pathParams);
				return;
			} else if(searchUserUri.match(request.getPathInfo(), pathParams)){
				searchUser(request, response, output, pathParams);
				return;
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
	} //doGet
	
	private void searchProject(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String keyword = pathParams.get("keyword");
		
		try{
			Preconditions.checkNotNull(keyword, "keyword is null");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} //catch
		
		JSONArray result = searchService.searchProject(keyword);

		output.println(new JSONObject()
			.put("success", "1")
			.put("result", result).toString());
	} //searchProject
	
	private void searchMyProject(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String token = CookieBox.get(request, "token");
	
		try{
			Preconditions.checkNotNull(token, "token is null");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} //catch
		
		String email = (String) JwtToken.getPayloadValue(token, "email");
		String keyword = pathParams.get("keyword");
		
		try{
			Preconditions.checkNotNull(email, "email is null");
			Preconditions.checkNotNull(keyword, "keyword is null");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} //catch
		
		JSONArray result = searchService.searchMyProject(email, keyword);

		output.println(new JSONObject()
			.put("success", "1")
			.put("result", result).toString());
	} //searchProject
	
	private void searchUser(HttpServletRequest request, HttpServletResponse response, PrintWriter output, Map<String, String> pathParams){
		String keyword = pathParams.get("keyword");
		
		try{
			Preconditions.checkNotNull(keyword, "keyword is null");
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			output.println(new JSONObject()
				.put("success", "0")
				.put("rescode", ResponseCode.PRECONDITION_FAILED)
				.put("msg", e.getMessage()).toString());
			return;
		} //catch
		
		JSONArray result = searchService.searchUser(keyword);

		output.println(new JSONObject()
			.put("success", "1")
			.put("result", result).toString());
	} //searchUser
} //class
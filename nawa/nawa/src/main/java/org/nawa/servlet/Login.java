package org.nawa.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.nawa.common.CookieBox;
import org.nawa.data.ResponseCode;
import org.nawa.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/** 
 * �α���
 * @author leejy 
 * @date 2015. 02. 07
 **/
public class Login extends HttpServlet {
	private static final long serialVersionUID = 6685285283009070799L;
	private static final Logger logger = LoggerFactory.getLogger(Login.class);

	private static LoginService loginService=LoginService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output = response.getWriter();

			String email = request.getParameter("email");
			String sha512Password = request.getParameter("password");
			
			try {
				Preconditions.checkNotNull(email, "email is null");
				Preconditions.checkNotNull(sha512Password, "password is null");
			} catch (NullPointerException e) {
				logger.error(e.getMessage(), e);
				output.println(new JSONObject().put("success", "0")
						.put("rescode", ResponseCode.PRECONDITION_FAILED)
						.put("msg", e.getMessage()).toString());
				return;
			} // catch
			
			logger.info("try to login : {}", email);

			String loginToken=loginService.generateLoginToken(email, sha512Password);
			if(loginToken!=null){
				logger.info("{} logined", email);
				CookieBox.set(response, "token",  loginToken, -1);
				output.println(new JSONObject().put("success", "1"));
				return;
			} else{
				output.println(new JSONObject().put("success", "0")
						.put("rescode", ResponseCode.BAD_REQUEST)
						.put("msg", "email or password incorrect"));
				return;
			} //if

		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()));
			if (output != null)
				output.println(new JSONObject().put("success", "0")
						.put("rescode", ResponseCode.UNKNOWN_ERROR)
						.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
		} finally{
			if(output!=null) {
				output.flush();
				output.close();
			} //if
		} //finally
	} // doPost
} // class
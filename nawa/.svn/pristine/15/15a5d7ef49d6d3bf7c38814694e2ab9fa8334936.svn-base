package org.nawa.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.nawa.common.CookieBox;
import org.nawa.common.JwtToken;
import org.nawa.data.ResponseCode;
import org.nawa.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/** 
 * �α׾ƿ�
 * @author leejy 
 * @date 2015. 02. 07
 **/
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 6685285283009070799L;
	private static final Logger logger = LoggerFactory.getLogger(Logout.class);

	private static LoginService loginService=LoginService.getInstance();

	public Logout() {
		super();
	} // INIT

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = null;

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output = response.getWriter();

			String token= CookieBox.get(request, "token");

			try {
				Preconditions.checkNotNull(token, "token is null, unauthorized");
			} catch (NullPointerException e) {
				logger.error("invalid parameter, token : {}", token);
				output.println(new JSONObject().put("success", "0")
						.put("rescode", ResponseCode.PRECONDITION_FAILED)
						.put("msg", e.getMessage()).toString());
				return;
			} // catch

			JwtToken jwtToken=new JwtToken(token);
			String email=jwtToken.get("email").toString();
			logger.info("{} logout", email);
			output.println(new JSONObject().put("success", "1"));
			CookieBox.set(response, "token", "", 0);
			
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
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
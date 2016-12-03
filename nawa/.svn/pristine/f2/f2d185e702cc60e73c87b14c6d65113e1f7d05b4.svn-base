package org.nawa.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.nawa.common.CookieBox;
import org.nawa.data.ResponseCode;
import org.nawa.exception.InsufficientAuthorityException;
import org.nawa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Servlet implementation class ChangePassword
 */
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Project.class);
	private UserService userService=UserService.getInstance();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	//비밀번호 재설정
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter output=null;
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			//이메일로 보내진 비밀번호 찾기 url로 들어왔는지를 확인할 수 있는 방법???
			//로그인된 상태가 아니어야 하는거 다시 확인
			String token= CookieBox.get(request, "token");
			
			//비밀번호 찾기
			if(token==null) //로그아웃 상태면
			{
				//session에 저장한 url을 받아옴
				HttpSession session = request.getSession();
				String eurl = (String)session.getAttribute("EURL"); //flush.close()???
				
				//새 비밀번호 받기
				String npwd = request.getParameter("newpassword");
				//비밀번호 확인 받기
				String rnpwd = request.getParameter("rnewpassword");
				
				try
				{ 
					Preconditions.checkNotNull(npwd, "eurl is null");
					Preconditions.checkNotNull(rnpwd, "eurl is null");
				}
				catch(NullPointerException e)
				{
					logger.error(e.getMessage(), e);
					output.println(new JSONObject().put("success", "0")
							.put("rescode", ResponseCode.PRECONDITION_FAILED)
							.put("msg", e.getMessage()).toString());
					return;
				}
				
				try
				{
					if(npwd.equals(rnpwd)) //같다면
					{
						//기존 비밀번호를 지우고 새 비밀번호를 db에 넣음.
						//update
						try
						{
							userService.pwdupdate(eurl, rnpwd);
						}
						catch(InsufficientAuthorityException e)
						{
							String msg = String.format("insufficient authority, change_email:%s, npassword:%s", eurl, npwd);
							logger.warn(msg);
							output.println(new JSONObject().put("success", "0")
									.put("rescode", ResponseCode.UNAUTHORIZED)
									.put("msg", msg));
							return;
						}//catch
					}
				}
				catch(Exception e) //비밀번호 확인이 다르다면
				{
					logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
					if (output != null)
						output.println(new JSONObject().put("success", "0")
								.put("rescode", ResponseCode.UNKNOWN_ERROR)
								.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
				}
			}
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
	}//doPut

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

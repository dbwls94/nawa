package org.nawa.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.nawa.common.Conf;
import org.nawa.common.CookieBox;
import org.nawa.common.JwtToken;
import org.nawa.data.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Servlet implementation class FindPassword
 */
public class FindPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Project.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter output=null;
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf8");
			
			output=response.getWriter();
			
			//로그인된 상태가 아니어야 함
			//쿠키박스에서 로그인 토큰 가져온게 null이면 로그인 상태가 아닌가?
			String token= CookieBox.get(request, "token");
			
			//비밀번호 찾기
			if(token==null) //로그아웃 상태면
			{
				//1. 사용자가 입력한 이메일 받아오기
				String eurl = request.getParameter("emailURL");
				//세션에 이메일 저장
				HttpSession esession = request.getSession();
				esession.setAttribute("EURL", eurl); 
		        
				try
				{ Preconditions.checkNotNull(eurl, "eurl is null"); }
				catch(NullPointerException e)
				{
					logger.error(e.getMessage(), e);
					output.println(new JSONObject().put("success", "0")
							.put("rescode", ResponseCode.PRECONDITION_FAILED)
							.put("msg", e.getMessage()).toString());
					return;
				}
				
				//2. 받은 이메일로 비밀번호 변경 url을 보내줌. 
				// 메일 서버 주소를 IP 또는 도메인 명으로 지정한다.				
				Properties props = new Properties();
							
				//nawa.properties에서 가져오기
				String mailhost=Conf.get("mail.server");
				if(mailhost==null)
				{ mailhost="mail.stmp.host"; }
				else
				{ props.setProperty("mail.server", mailhost); } //if
				
				String mailport=Conf.get("mail.port");
				if(mailport==null)
				{ mailport="465"; }
				else
				{ props.setProperty("mail.port", mailport); } //if
				
				String mailid=Conf.get("mail.id");
				if(mailid==null)
				{ mailid="nawa@nawa.com"; }
				else
				{ props.setProperty("mail.id", mailid); } //if
				
				String mailpw=Conf.get("mail.password");
				if(mailpw==null)
				{ mailpw="nawapassword"; }
				else
				{ props.setProperty("mail.password", mailpw); } //if
				
				// 위 환경 정보로 "메일 세션"을 만들고 빈 메시지를 만든다
				Session session = Session.getDefaultInstance(props);			
				 
				try
				{
					MimeMessage msg = new MimeMessage(session);
				    // 발신자, 수신자, 참조자, 제목, 본문 내용 등을 설정한다
				    msg.setFrom(new InternetAddress("nawa@nawa.com", "nawa")); //발신자
				    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(eurl, "*고객님*")); //수신자
				    //msg.addRecipient(Message.RecipientType.CC, new InternetAddress("ggg@hhh.co.kr", "의자왕")); //참조자
				    msg.setSubject("비밀번호 변경 이메일입니다."); //제목
				    msg.setContent("*변경 이메일 url링크*", "text/html; charset=utf-8"); //본문내용
				 
				    // 메일을 발신한다
				    Transport.send(msg);
				}
				catch (Exception e)
				{
					logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
					if (output != null)
						output.println(new JSONObject().put("success", "0")
								.put("rescode", ResponseCode.UNKNOWN_ERROR)
								.put("msg", String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage())));
				}
			}
			//로그인된 상태라면 catch()문으로
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
		
		
	}
}

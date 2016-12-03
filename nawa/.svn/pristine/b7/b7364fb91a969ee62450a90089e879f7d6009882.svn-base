package org.nawa.service;

import org.nawa.common.JwtToken;
import org.nawa.rdb.DbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * @description �α��� ���� ����� �����ϴ� service class
 * @author leejy
 */
public class LoginService {
	private static final Logger logger=LoggerFactory.getLogger(LoginService.class);
	private static LoginService INSTANCE=null;
	
	private LoginService(){
	} //INIT
	
	public static LoginService getInstance(){
		synchronized (LoginService.class) {
			if(INSTANCE==null)
				INSTANCE=new LoginService();
			return INSTANCE;
		} //synch
	} //getInstance
	
	/**
	 * @description ����� ������ �����Ѵ�.
	 * @param email ���� ������ ������� email
	 * @param sha512Passwd ���� ������ sha512�� ��ȣȭ�� passwd
	 * @return true if authenticated, false if not authenticated
	 */
	private boolean authenticate(String email, String sha512Passwd){
		logger.info(email);
		
		try{
			Preconditions.checkNotNull(email);
			Preconditions.checkNotNull(sha512Passwd);
		} catch(NullPointerException e){
			logger.error("email, sha512Passwd null check : true, email : {}, sha512Passwd : {}", email, sha512Passwd);
			return false;
		} //catch
		
		String[][] result=DbHandler.preparedSelect("select count(*) from user_info where email = ? and passwd = ?", email, sha512Passwd);
	
		if(result==null || result.length==0 || result[0].length==0 || "0".equals(result[0][0])){
			logger.info("{} failed to authenticate", email);
			return false;
		} //if
	
		logger.info("{} successes to authenticate", email);
		return true;
	} //authnticate
	
	/**
	 * @description ����� ������ ��ģ �Ŀ� token�� �����Ѵ�.
	 * @param email ����� email
	 * @param sha512Passwd sha512�� ��ȣȭ�� ����� passwd
	 * @return token if authenticated, null if not authenticated
	 */
	public String generateLoginToken(String email, String sha512Passwd){
		logger.info(email);
		
		if(authenticate(email, sha512Passwd)==false)
			return null;
	
		return new JwtToken().put("email", email).put("loginTime", System.currentTimeMillis()).toString();
	} //generateLoginToken
	
	
	/**
	 * @description �ش� token�� ���� �α��� ���θ� Ȯ�� 
	 * @param token
	 * @return
	 */
	public boolean isLogined(String token){
		logger.info(token);
		
		if(token==null || token.length()==0)
			return false;
		
		try{
			JwtToken tokenObj=new JwtToken(token);
			return tokenObj.get("email")!=null;
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			return false;
		} //catch
	} //isLogined
} //class
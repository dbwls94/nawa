package org.nawa.common;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

public class JwtToken {
	private static final Logger logger=LoggerFactory.getLogger(JwtToken.class);
	private static final String secret="e252dea7-f997-4ac2-8d25-b01bf08cbba1";
	private static JWTVerifier verifier=new JWTVerifier(secret);
	private static JWTSigner signer=new JWTSigner(secret);
	
	private Map<String, Object> payload=null;
	
	public JwtToken(String token) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, SignatureException, IOException, JWTVerifyException{
		this.payload=verifier.verify(token);
	} //INIT
	
	public JwtToken(){
		payload=new HashMap<String, Object>();
	} //INIT
	
	public JwtToken put(String key, Object value){
		this.payload.put(key, value);
		return this;
	} //putPayload
	
	public Object get(String key){
		return this.payload.get(key);
	} //get
	
	public static Object getPayloadValue(String token, String key){
		if(token==null || token.trim().length()==0)
			return null;
		
		try {
			JwtToken tokenObj=new JwtToken(token);
			return tokenObj.get(key);
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException | IOException | JWTVerifyException e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			return null;
		} //catch
	} //getPayloadValue
	
	public static <T> T getPayloadValueAs(String token, String key, Class<T> clazz){
		Object value = getPayloadValue(token, key);
		if(value == null)
			return null;
		return (T) value;
	} //getPayloadValueAs
	
	@Override
	public String toString() {
		return signer.sign(this.payload);
	} //toString
} //class
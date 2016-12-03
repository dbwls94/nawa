package org.nawa.common;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.junit.Test;

import com.auth0.jwt.JWTVerifyException;

public class JwtTokenTest {

	@Test
	public void test() throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, SignatureException, IOException, JWTVerifyException {
		try{
			String token=new JwtToken().put("test1", "testValue").toString();
			assertTrue(new JwtToken(token).get("test1").equals("testValue"));
		} catch(Exception e){
			fail(e.getMessage());
		} //catch
	} //test
} //class
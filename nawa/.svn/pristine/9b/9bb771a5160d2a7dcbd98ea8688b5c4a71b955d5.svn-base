package org.nawa.servlet;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.Test;
import org.nawa.common.JwtToken;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class LogoutTest {

	@Test
	public void test_logout() {
		try{
			String url = "http://localhost:8080/Logout";
			ServletRunner servletRunner = new ServletRunner();
			servletRunner.registerServlet("Logout", Logout.class.getName());
			ServletUnitClient client = servletRunner.newClient();
			client.putCookie("token", new JwtToken().put("email", "test@test.com").toString());

			PostMethodWebRequest request = new PostMethodWebRequest(url);
			WebResponse response = client.getResponse(request);
			
			JSONObject responseJson = new JSONObject(response.getText());
			assertTrue(responseJson.get("success").equals("1"));
		} catch(Exception e){ 
			e.printStackTrace();
			fail();
		} //catch
	} //test_logout
} //class
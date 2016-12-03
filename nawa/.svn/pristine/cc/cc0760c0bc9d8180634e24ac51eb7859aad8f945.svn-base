package org.nawa.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.mockito.Mockito;
import org.nawa.common.JwtToken;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class MultipartRequester {
	Map<String, String> params = new HashMap<String, String>();
	Map<String, File> fileParams = new HashMap<String, File>();
	
	public MultipartRequester addParam(String key, String value){
		params.put(key, value);
		return this;
	} //addParam
	
	public MultipartRequester addFileParams(String key, File value){
		fileParams.put(key, value);
		return this;
	}
	
	public Map<String, String> getParams(){
		return this.params;
	} //getParams
	
	public Map<String, File> getFileParams(){
		return this.fileParams;
	} //getFileParams
	
	public String execute(HttpMethodBase method, String pathInfo, String tokenEmail, IRequestExecute execute) throws Exception{
		List<Part> parts = new ArrayList<Part>();
		for(Entry<String, String> paramEntry : params.entrySet())
			parts.add(new StringPart(paramEntry.getKey(), paramEntry.getValue()));
		for(Entry<String, File> paramFileEntry : fileParams.entrySet())
			parts.add(new FilePart(paramFileEntry.getKey(), paramFileEntry.getValue()));
		MultipartRequestEntity reqEntity = new MultipartRequestEntity(parts.toArray(new Part[parts.size()]), method.getParams());
		ByteArrayOutputStream retContent = new ByteArrayOutputStream();
		reqEntity.writeRequest(retContent);
		
		final byte[] partsBytes = retContent.toByteArray();
		String contentType = reqEntity.getContentType();
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter strWriter = new StringWriter();
		Mockito.when(response.getWriter()).thenReturn(new PrintWriter(strWriter));
		
		Mockito.when(request.getPathInfo()).thenReturn(pathInfo);
		Mockito.when(request.getContentLength()).thenReturn(partsBytes.length);
		Mockito.when(request.getContentType()).thenReturn(contentType);
		Mockito.when(request.getInputStream()).thenReturn(new ServletInputStream() {
			private InputStream bodyIn = new ByteInputStream(partsBytes, partsBytes.length);
			@Override
			public int read() throws IOException { return bodyIn.read(); }
		});

		if(tokenEmail != null){
			String token = new JwtToken().put("email", tokenEmail).toString();
			Cookie tokenCookie = new Cookie("token", URLEncoder.encode(token, "utf8"));
			Mockito.when(request.getCookies()).thenReturn(new Cookie[]{tokenCookie});
		} //if
		
		execute.execute(request, response);
		return strWriter.toString();
	} //execute
	
	public interface IRequestExecute{
		public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	}
} //class
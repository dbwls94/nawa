<%@page import="com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64"%>
<%@page import="java.util.StringTokenizer"%>

<%
String authHeader=request.getHeader("Authorization");
if(authHeader==null){
	response.setHeader("WWW-Authenticate", "BASIC realm=\":D:D:D:D\"");
  response.sendError(response.SC_UNAUTHORIZED);
  return;
} //if

  StringTokenizer st=new StringTokenizer(authHeader);
  if(st.hasMoreTokens()==false){
	  response.setHeader("WWW-Authenticate", "BASIC realm=\":D:D:D:D\"");
	  response.sendError(response.SC_UNAUTHORIZED);
	  return;
  } //if
  
  
 String basic=st.nextToken();
 if(basic.equalsIgnoreCase("Basic")==false){
	  response.setHeader("WWW-Authenticate", "BASIC realm=\":D:D:D:D\"");
	  response.sendError(response.SC_UNAUTHORIZED);
	  return;
 } //if
 
 try{
	   String credentials=new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
	   int p=credentials.indexOf(":");
	   if(p==-1){
		   response.setHeader("WWW-Authenticate", "BASIC realm=\":D:D:D:D\"");
		   response.sendError(response.SC_UNAUTHORIZED);
		   return;  
	   } //if
	   
	   String login=credentials.substring(0, p).trim();
	   String password=credentials.substring(p+1).trim();
	 
	   if(login.equalsIgnoreCase("nawa")==false || password.equalsIgnoreCase("nawadkagh")==false){
		   response.setHeader("WWW-Authenticate", "BASIC realm=\":D:D:D:D\"");
		   response.sendError(response.SC_UNAUTHORIZED);
		   return;   
	   } //if
 } catch(Exception e){
	   response.setHeader("WWW-Authenticate", "BASIC realm=\":D:D:D:D\"");
	   response.sendError(response.SC_UNAUTHORIZED);
		  return;   
 } //catch
  
%>
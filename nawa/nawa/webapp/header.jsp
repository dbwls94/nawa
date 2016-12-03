<%@page import="org.json.JSONObject"%>
<%@page import="org.nawa.service.UserService"%>
<%@page import="org.nawa.common.JwtToken"%>
<%@page import="org.nawa.common.CookieBox"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	String urlPath = "";

	String p2 = "/";
	String s2 = request.getRequestURL().toString();
	String[] sArray2 = s2.split(p2);
	
	String modulePage = sArray2[sArray2.length - 1];
	
	// main menu "on" condition
	String moduleFolder = "";
	String mainMenu = "";
	if(modulePage.equals("project_list.jsp") || modulePage.equals("project_detail.jsp") || modulePage.equals("project_edit.jsp") || modulePage.equals("project_member.jsp")) {
		mainMenu = "list";
		moduleFolder = "project";
	} else if(modulePage.equals("project_create.jsp")) {
		mainMenu = "create";
		moduleFolder = "project";
	} else if(modulePage.equals("profile_project.jsp") || modulePage.equals("profile_following.jsp") || modulePage.equals("profile_follower.jsp") || modulePage.equals("profile_edit.jsp")) {		
		moduleFolder = "member";
	}
	
	boolean isLogin = false;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("token")) {
				isLogin = true;
			}
		}
	}

	// boolean isLogin = false;
	// boolean isLogin = true; 
	
	
	
	String project_id = request.getParameter("project_id");
	String email = request.getParameter("email");
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Nawa</title>

	<script src="/js/lib/jquery-1.11.2.js"></script>
	<script src="/js/lib/sha512.js"></script>
	<script src="/js/server.js"></script>
	<script type="text/javascript">
		urlPath = "<%=urlPath%>";
	</script>
	
	<script src="<%=urlPath%>/js/common.js"></script>
	
	<% /* %><% */ %>
	<% if( !moduleFolder.equals("") ) { %>
	<script src="<%=urlPath%>/js/<%=moduleFolder%>.js"></script>
	<link href="<%=urlPath%>/css/<%=moduleFolder%>.css" rel="stylesheet">
	<% } %>
	<% /* %><% */ %>
	
	
	<link href="<%=urlPath%>/css/common.css" rel="stylesheet">
	<script type="text/javascript">
		<%
			JSONObject userInfo = null;
			try{
				String token = CookieBox.get(request, "token");
				if(token != null && token.trim().length() != 0){
					String myEmail = JwtToken.getPayloadValueAs(token, "email", String.class);
					userInfo = UserService.getInstance().loadUser(myEmail);
				} //if
			} catch(Exception e){
				e.printStackTrace();
			} //catch
		%>
		
		/*
			myInfo = { name: (string), email: (string), regdate: (string), gender: (int) };
		*/
		myInfo = <%= userInfo == null ? "null" : userInfo.toString() %>;
		isLogin = <%= userInfo == null ? "false" : "true" %>;
	</script>
</head>
<body><div id="nawa_wrap">

<div id="nawa_header"><div id="nawa_header_wrap">
	<div id="logo">
		<a href="/"><img src="/img/logo.png" /></a>
	</div>		
	<div id="main_menu" class="menu">
		<ul>
			<li id="main_menu_list_project"<%=(mainMenu=="list")?" class='on'":"" %>><a href="<%=urlPath%>/project_list.jsp">프로젝트 찾기</a></li>
			<li id="main_menu_make_project"<%=(mainMenu=="create")?" class='on'":"" %>><a href="<%=urlPath%>/project_create.jsp">새 프로젝트 만들기</a></li>
		</ul>
	</div>
	<% if(isLogin) { %>
	<div id="login_action">
		<ul>
			<li id="search_input"><input type="search" id="search_query" /></li>
			<li id="search_button"><img src="/img/top_menu_icon_search.png" /></li>
			<li id="notification_button"><img src="/img/top_menu_icon_notification.png" /><span id="notification_number">1</span></li>
			<li id="logout_button"><img src="/img/top_menu_icon_logout.png" /></li>
			<li id="login_user" class="user userpic40"><a href="#"><img src="/img/userpic/userpic40_1.png" /></a></li>
		</ul>
	</div>
	<% } else { %>
	<div id="login_action">
		<ul>
			<li id="search_input"><input type="search" id="search_query" /></li>
			<li id="search_button"><img src="/img/top_menu_icon_search.png" /></li>
			<li id="login_button"><button type="button" class="type2">로그인</button></li>
		</ul>
	</div>
	<% } %>
</div></div>

<div id="nawa_contents">

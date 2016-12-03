<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String p2 = "/";
	String s2 = request.getRequestURL().toString();
	String[] sArray2 = s2.split(p2);
	String moduleFolder = sArray2[sArray2.length - 2];

	String modulePage = sArray2[sArray2.length - 1];

	// main menu "on" condition
	String mainMenu = "";
	if (moduleFolder.equals("project")) {
		if (modulePage.equals("list.jsp")) {
			mainMenu = "list";
		} else if (modulePage.equals("create.jsp")) {
			mainMenu = "create";
		}
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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nawa</title>

<script src="/js/lib/jquery-1.11.2.js"></script>
<script src="/js/lib/sha512.js"></script>
<script src="/js/common.js"></script>
<link href="/css/common.css" rel="stylesheet">
<script src="/js/server.js"></script>
<script src="/js/member.js"></script>

<script src="/js/<%=moduleFolder%>.js"></script>
<link href="/css/<%=moduleFolder%>.css" rel="stylesheet">

</head>
<body>
	<div id="header_wrapp" class="header_wrap">
		<div id="header">
			<div id="logo">
				<a href="/"><img src="/imgsample/site_logo.png" /></a>
			</div>
			<div id="main_menu">
				<ul>
					<li id="main_menu_list_project"
						<%=(mainMenu == "list") ? " class='on'" : ""%>><a
						href="/project/list.jsp">프로젝트 찾기</a></li>
					<li id="main_menu_make_project"
						<%=(mainMenu == "create") ? " class='on'" : ""%>><a
						href="/project/create.jsp">새 프로젝트 만들기</a></li>
				</ul>
			</div>
			<%
				if (isLogin) {
			%>
			<div id="login_action">
				<ul>
					<li id="search_input"><input type="search" id="search_key" /></li>
					<li id="search"><img src="/imgsample/icon_loginbar_search.png" /></li>
					<li id="notification"><img
						src="/imgsample/icon_loginbar_notification.png" /></li>
					<li id="logout"><img src="/imgsample/icon_loginbar_logout.png" /></li>
				</ul>
			</div>
			<div id="login_user">
				<a href="/member/profile.jsp"><img
					src="/imgsample/loginbar_profile.png" /></a>
			</div>
			<%
				} else {
			%>
			<div id="login_action">
				<ul>
					<li id="search_input"><input type="search" id="search_key" /></li>
					<li id="search"><img src="/imgsample/icon_loginbar_search.png" /></li>
					<li id="login"><div id="login_btn_wrap">
							<button type="button">로그인</button>
						</div></li>
				</ul>
			</div>
			<%
				}
			%>
		</div>
	</div>
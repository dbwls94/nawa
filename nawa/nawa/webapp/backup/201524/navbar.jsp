<%@page import="org.nawa.entity.NavBarEntity"%>
<%@page import="org.nawa.service.NavBarService"%>
<%@page import="org.nawa.common.CookieBox"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body>
		<nav class="navbar-top">
			<a href=""><img class="nav-logo" src="" alt=""/></a>
			<input type="search"  placeholder="search" >
			<input type="button" class="search-button"value="찾기" onClick=""/>
			<a class="nav-profile"><img class="profile-img" src="img/photo.jpg" alt=""/>
				<span id="profile-name"></span>
			</a>
			<span>
				<span id="project-count"></span>개의 모임이 진행중입니다.
			</span>
			<a href="">
				logout
			</a>
			<a href=""><img src="" class="nav-arlam">
				알람 이미지 넣기
			</a>
			<a href="open_project.jsp" class="nav-project-add">모임만들기</a>
		</nav>
	</body>
</html>
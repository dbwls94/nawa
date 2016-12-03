<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp" %>
<h1>로그인</h1>
<div id="member_wrap" class="form_wrapper">
	<div class="header">
		<div class="close_btn">X</div>
	</div>
	<div class="content">
		<div id="login_wrap">
				
			<div id="login_buttons">
				<button type="button" id="signup_btn">signup</button>
				<button type="button" id="login_facebook">facebook login</button>
				<button type="button">둘러보기</button>
			</div>
				
			<form id="login_form" onsubmit="return login(this)">	
				<input type="text" id="loginID" placeholder="E-mail" required />
				<input type="password" id="loginPW" placeholder="비밀번호" required />
				<button>Login</button>
			</form>			
		
		</div>
	</div>
</div>
<div id="mask"></div>
<%@ include file="/footer.jsp" %>
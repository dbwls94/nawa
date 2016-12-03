<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp" %>
<h1>회원가입</h1>
<div id="member_wrap" class="form_wrapper">
	<div class="header">
		<div class="close_btn">X</div>
	</div>
	<div class="content">
		<div id="signup_wrap">
				
			<button type="button" id="login_facebook">facebook login</button>		
			<p>혹은</p>
			
			<form id="Signup_form" onsubmit="return signup(this)">
				<div class="picbutton">		
					<img src="/img/photo.jpg" class="profile-img" style="width: 120px; height: 120px;">
					<input type="file" id="UserPic">			
				</div>
				
				<input type="text" id="UserName" placeholder="이름" required />	
				<input type="email" id="UserEmail" placeholder="E-mail" required />	
				<input type="password" id="UserPwd" placeholder="비밀번호" required />	
				<input type="password" id="UserPwdCheck" placeholder="비밀번호 확인" required />
			
				<label for="UserGender">성별: </label>
				<select id="UserGender">
					<option value="1">남자</option>
					<option value="2">여자</option>
				</select>
			
				<button>가입하기</button>				
			</form>
			
		</div>
	</div>
</div>
<%@ include file="/footer.jsp" %>
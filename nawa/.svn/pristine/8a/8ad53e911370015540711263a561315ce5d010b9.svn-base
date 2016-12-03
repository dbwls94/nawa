<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="./header.jsp"%>
<script type="text/javascript">
<!--
$(function() {
	getProfile('<%=email%>');
	getUserPicBG($("#edit_profile_form .upload"), '<%=email%>', 120);
});

//-->
</script>


<div id="nawa_contents_title">
	<h2>프로필 편집</h2>
</div>

<div id="nawa_contents_wrap">

	<form id="edit_profile_form">
		<input type="hidden" id="signup_gender" value="1" />
	
				<div class="upload">
			        <input type="file" id="edit_profile_profile_img" accept="image/*" />
			    </div>				
					
				
				<label for="edit_profile_name">이름</label>
					<input type="text" id="edit_profile_name" placeholder="이름" required />	
				<label for="edit_profile_email">E-mail</label>
					<input type="email" id="edit_profile_email" placeholder="E-mail" required disabled="disabled" />
				<% if(true) { // 비밀번호 수정은 하지 않음. 수정 시 비밀번호로 확인 해야하나? %>		
				<label for="edit_profile_pw">비밀번호</label>
					<input type="password" id="edit_profile_pw" placeholder="비밀번호" required />	
				<label for="edit_profile_pw_check">비밀번호 확인</label>
					<input type="password" id="edit_profile_pw_check" placeholder="비밀번호 확인" required />
				<% } %>				
				<div class="select_gender">
					<div class="select_gender_label">성별</div>
					<div class="select_gender_content">
						<div id="signup_gender_1" class="gender_male on"></div>
						<div id="signup_gender_2" class="gender_female"></div>
					</div>
				</div>
	
	
	</form>
	
	<div class="action_area">
		<button type="button" id="profile_action_cancel" class="disable cancel_button">취소하기</button>
		<button type="button" id="profile_action_submit" class="enable">수정완료</button>
	</div>
	
</div>


<%@ include file="./footer.jsp"%>
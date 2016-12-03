<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<link href="/css/member.css" rel="stylesheet">

<div class="findpw_title">
	<h2>비밀번호 찾기</h2>
</div>


<div class="findpw_wrap">
	<div class="findpw_wrap_title">
		가입하실때 입력한 E-mail 주소를 입력해주세요.<br>
		임시 비밀번호를 전송해 드립니다.
	</div>
	<label for="findpw_email">E-mail</label>
	<input type="email" id="findpw_email" />
</div>


	<div id="edit_profile_action_area" class="action_area">
		<button type="button" id="findpw_cancel">이전으로</button>
		<button type="button" id="findpw_send" class="enable">보내기</button>
	</div>
<%@ include file="/footer.jsp"%>
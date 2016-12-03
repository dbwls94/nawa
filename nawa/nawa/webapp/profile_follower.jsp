<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="./header.jsp"%>
<script type="text/javascript">
<!--
$(function() {
	getProfileData('<%=email%>');
	getUserPic($(".user_pic img"), '<%=email%>', 120);
});
//-->
</script>


<div id="nawa_contents_title" class="profile_title">
<h2>크리에이터 프로필</h2>
</div>

<div id="nawa_contents_wrap">



	<div id="profile_userinfo">
		
		<div class="user_pic">
			<img src="" />
		</div>
		<div class="user_name">
			<span class="username"></span>
			<span class="edit_button"></span>
		</div>		
		<div class="user_email"><img src="/img/icon_email.png"><span></span></div>
		<div class="action_area"></div>
	
	</div>
	
	<div id="user_activity">
		
		<ul class="tab_title_list">
			<li><a href="<%=urlPath%>/profile_project.jsp?email=<%=email%>"><div class="tab_title">프로젝트</div><div class="number"></div></a></li>
			<li><a href="<%=urlPath%>/profile_following.jsp?email=<%=email%>"><div class="tab_title">팔로잉</div><div class="number"></div></a></li>
			<li class="on"><a href="<%=urlPath%>/profile_follower.jsp?email=<%=email%>"><div class="tab_title">팔로워</div><div class="number"></div></a></li>
		</ul>


		<div class="people_list follower">
			<ul></ul>
		</div>

	</div>


</div>


<%@ include file="./footer.jsp"%>
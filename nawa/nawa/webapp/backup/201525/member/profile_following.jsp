<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script src="/js/profile.js"></script>

<div class="profile_title">
	<h2>크리에이터 프로필</h2>
</div>

<div class="profile_wrap" id="profileContainer">


	<div id="profile_userinfo">
		
		<div class="user_pic">
			<img src="/imgsample/profile_yjp.png" />
		</div>
		<div class="user_name">
			박유진
			<span class="edit_button">E</span>
		</div>
		<div class="user_email">
			wychoi0709@naver.com
		</div>
		<div class="action_area">
			<button type="button" class="enable">팔로우</button>
		</div>
	
	</div>
	
	<div id="user_activity">
		
		<ul class="tab_title_list">
			<li><a href="profile.jsp"><div class="tab_title">프로젝트</div><div class="number">6</div></a></li>
			<li class="on"><a href="profile_following.jsp"><div class="tab_title">팔로잉</div><div class="number">28</div></a></li>
			<li><a href="profile_follower.jsp"><div class="tab_title">팔로워</div><div class="number">15</div></a></li>
		</ul>
		
		<div class="people_list">
			<ul>
			
				<li>
					<div class="user_pic">
						<img src="/imgsample/profile_yjp.png" />
					</div>
					<div class="user_name">
						박유진
					</div>
					<div class="action_area">
						<button type="button" class="enable unfollow">v 팔로우</button>
					</div>
				</li>
				<li>
					<div class="user_pic">
						<img src="/imgsample/profile_yjp.png" />
					</div>
					<div class="user_name">
						박유진
					</div>
					<div class="action_area">
						<button type="button" class="enable unfollow">v 팔로우</button>
					</div>
				</li>
				<li>
					<div class="user_pic">
						<img src="/imgsample/profile_yjp.png" />
					</div>
					<div class="user_name">
						박유진
					</div>
					<div class="action_area">
						<button type="button" class="enable unfollow">v 팔로우</button>
					</div>
				</li>
				<li>
					<div class="user_pic">
						<img src="/imgsample/profile_yjp.png" />
					</div>
					<div class="user_name">
						박유진
					</div>
					<div class="action_area">
						<button type="button" class="enable unfollow">v 팔로우</button>
					</div>
				</li>
				
			</ul>
		</div>
		
		
	</div>


</div>

<input type="hidden" id="hidEmail" value="<%= request.getParameter("email") %>" />
<input type="hidden" id="hidProfile_type" value="FOLOWWING" />

<%@ include file="/footer.jsp"%>
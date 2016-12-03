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
			<span class="edit_button"><a href="/member/edit_profile.jsp">Edit</a></span>
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
			<li class="on"><a href="profile.jsp"><div class="tab_title">프로젝트</div><div class="number">6</div></a></li>
			<li><a href="profile_following.jsp"><div class="tab_title">팔로잉</div><div class="number">28</div></a></li>
			<li><a href="profile_follower.jsp"><div class="tab_title">팔로워</div><div class="number">15</div></a></li>
		</ul>
		
		<div class="result_list">
			<div class="result_title">
				참여한 프로젝트
			</div>
			<ul>
			
				<li>
					<div class="result_list_title">웹서비스 제작할 디자이너, 개발자 구합니다...</div>
					<div class="img_area"><img src="/imgsample/project_image.png" /></div>
					<div class="result_info">					
						<div class="nums_of_member">
							<span class="joined member number">3</span>
							/
							<span class="admitted member number">10</span>
						</div>
						<div class="dday">D-7</div>
						<div class="progress">
							<div class="progress_value"></div>
						</div>
						<div class="project_info">
							<div class="project_date">2015년 1월 20일</div>
							/
							<div class="project_place">홍대 팀플레이스</div>
						</div>
						<div class="action_area">
							<button type="button" class="enable">참여하기</button>
						</div>						
					</div>
				</li>
				
				<li>
					<div class="result_list_title">웹서비스 제작할 디자이너, 개발자 구합니다...</div>
					<div class="img_area"><img src="/imgsample/project_image.png" /></div>
					<div class="result_info">					
						<div class="nums_of_member"></div>
						<div class="dday"></div>
						<div class="progress">
							<div class="progress_value"></div>
						</div>
						<div class="project_info">
							<div class="project_date">2015년 1월 20일</div>
							/
							<div class="project_place">홍대 팀플레이스</div>
						</div>
						<div class="action_area">
							<button type="button" class="disable">마감</button>
						</div>						
					</div>
				</li>
				
				
				
				
				
				<li>
					<div class="result_list_title">웹서비스 제작할 디자이너, 개발자 구합니다...</div>
					<div class="img_area"><img src="/imgsample/project_image.png" /></div>
					<div class="result_info">					
						<div class="nums_of_member"></div>
						<div class="dday"></div>
						<div class="progress">
							<div class="progress_value"></div>
						</div>
						<div class="project_info">
							<div class="project_date">2015년 1월 20일</div>
							/
							<div class="project_place">홍대 팀플레이스</div>
						</div>
						<div class="action_area">
							<button type="button" class="disable">마감</button>
						</div>						
					</div>
				</li>
				
				
			</ul>
		</div>
		
		<div class="result_list">
			<div class="result_title">
				추진한 프로젝트
			</div>
			<ul>
			
				<li>
					<div class="result_list_title">웹서비스 제작할 디자이너, 개발자 구합니다...</div>
					<div class="img_area"><img src="/imgsample/project_image.png" /></div>
					<div class="result_info">					
						<div class="nums_of_member">
							<span class="joined member number">3</span>
							/
							<span class="admitted member number">10</span>
						</div>
						<div class="dday">D-7</div>
						<div class="progress">
							<div class="progress_value"></div>
						</div>
						<div class="project_info">
							<div class="project_date">2015년 1월 20일</div>
							/
							<div class="project_place">홍대 팀플레이스</div>
						</div>
						<div class="action_area">
							<button type="button" class="setting">ⓞ</button>
						</div>						
					</div>
				</li>
				
				<li>
					<div class="result_list_title">웹서비스 제작할 디자이너, 개발자 구합니다...</div>
					<div class="img_area"><img src="/imgsample/project_image.png" /></div>
					<div class="result_info">					
						<div class="nums_of_member"></div>
						<div class="dday"></div>
						<div class="progress">
							<div class="progress_value"></div>
						</div>
						<div class="project_info">
							<div class="project_date">2015년 1월 20일</div>
							/
							<div class="project_place">홍대 팀플레이스</div>
						</div>
						<div class="action_area">
							<button type="button" class="setting">ⓞ</button>
						</div>						
					</div>
				</li>
				
				
			</ul>
		</div>
		
	</div>


</div>

<input type="hidden" id="hidEmail" value="<%= request.getParameter("email") %>" />
<input type="hidden" id="hidProfile_type" value="PROJECT" />

<%@ include file="/footer.jsp"%>
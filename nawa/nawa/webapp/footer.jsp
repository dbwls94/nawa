<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


</div>



<div id="login_layer" class="layer">
	<div class="layer_header">
		<div class="layer_message"></div>
		<div class="close_btn">X</div>
	</div>
	<div class="layer_content">
		<div class="layer_content_wrap">
			<div class="layer_menu">
				<div class="login on">로그인</div>
				<div class="signup">처음이세요?</div>
			</div>
			
			<form id="login_form" onsubmit="return login(this)">	
				<input type="email" id="login_id" placeholder="E-mail" required />
				<input type="password" id="login_pw" placeholder="비밀번호" required />
			</form>			
				
			<div class="layer_buttons">
				<button type="button" id="login_facebook" class="icon_left facebook facebook_short">로그인</button>
				<button type="button" id="login_btn" class="">로그인</button>
				<button type="button" id="find_pw" class="normal">뭐더라..?</button>
			</div>
				
		
		</div>
	</div>
</div>

<div id="signup_layer" class="layer">
	<div class="layer_header">
		<div class="layer_message"></div>
		<div class="close_btn">X</div>
	</div>
	<div class="layer_content">
		<div class="layer_content_wrap">
			<div class="layer_menu">
				<div class="login">로그인</div>
				<div class="signup on">처음이세요?</div>
			</div>
			
			<div class="facebook_login">
				<button type="button" id="signup_facebook" class="icon_left facebook facebook_long">페이스북으로 로그인</button>
			</div>
			
			<div class="clear_blank">혹은</div>
			
			<form id="signup_form" onsubmit="return signup(this)">
				<input type="hidden" id="signup_gender" value="1" />
				<div class="upload">
			        <input type="file" id="signup_profile_img" accept="image/*" />
			    </div>	
				<input type="text" id="signup_name" placeholder="이름" required />	
				<input type="email" id="signup_email" placeholder="E-mail" required />	
				<input type="password" id="signup_pw" placeholder="비밀번호" required />	
				<input type="password" id="signup_pw_check" placeholder="비밀번호 확인" required />
				<div class="select_gender">
					<div class="select_gender_label">성별</div>
					<div class="select_gender_content">
						<div id="signup_gender_1" class="gender_male on"></div>
						<div id="signup_gender_2" class="gender_female"></div>
					</div>
				</div>
			</form>			
				
			<div class="layer_buttons">
				<button type="button" id="signup_btn" class="main_btn">가입하기</button>
			</div>
				
		
		</div>
	</div>
</div>


<div id="notification_wrap">
	<div class="header">
		<div id="layer_message"><img src="/img/icon_notification_title.png"></div>
		<div class="close_btn">X</div>
	</div>
	<div class="notification_content">
		<div class="notification_menu">
			<div class="my_notification on">내소식</div>
			<div class="following_notification">팔로잉</div>
		</div>		
		<div class="notification_list nawa_scrollbars"></div>
		
	</div>
</div>


<div id="mask"></div>



<div id="noti_type_1" class="noti_data">
	<ul>
		<li>
			<div class="image_area">
				<img src="/img/userpic/userpic60_2.png" />
			</div>
			<div class="content_area">
				<div class="notification_function">
					<button type="button" class="type2">follow</button>
				</div>
				<div class="notification_content">
					<span class="noti_bold">박유진</span>님이 회원님을 팔로우하기 시작했습니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/projectpic/projectpic60_1.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">웹서비스...</span> 에 새로운 댓글이 <span class="noti_bold">3개</span> 있습니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/projectpic/projectpic60_1.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">웹서비스...</span> 가 수정되었습니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/projectpic/projectpic60_1.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">웹서비스...</span> 에서 회원님과의 다음 기회를 기약합니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/projectpic/projectpic60_1.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">웹서비스...</span> 에 새로운 댓글이 <span class="noti_bold">7개</span> 있습니다.
				</div>
			</div>
		</li>
	</ul>
</div>
<div id="noti_type_2" class="noti_data">
	<ul>
		<li>
			<div class="image_area">
				<img src="/img/userpic/userpic60_2.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">박유진</span>님이 <span class="noti_bold">시계만들어 주실 분 안계신...</span>에 참여합니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/userpic/userpic60_2.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">박유진</span>님이 <span class="noti_bold">웹서비스 제작할 디자이너, 개발자 구합...</span>프로젝트를 만들었습니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/userpic/userpic60_5.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">이재용</span>님이 <span class="noti_bold">김유진</span>님을 팔로우하기 시작합니다.
				</div>
			</div>
		</li>
		<li>
			<div class="image_area">
				<img src="/img/userpic/userpic60_2.png" />
			</div>
			<div class="content_area">
				<div class="notification_content">
					<span class="noti_bold">박유진</span>님이 <span class="noti_bold">우산을 이용한 새로운 제품제작...</span>에 댓글을 남겼습니다.
				</div>
			</div>
		</li>
	</ul>
</div>






<script id="comment_list_template" type="text/x-nawa-template">

		<li class="comment_root depth{{depthNum}}" comment_seq="{{seq}}" project_id="{{project_id}}" comment_depth="{{depth}}" useremail="{{user_email}}">
			<img src="/User/Pic/{{user_email}}?width=50&height=50" />
			<div class="comments_writer">{{name}}</div>
			<div class="comments_time">{{string_date}}</div>
			<div class="comments_action"><img class="edit" src="/img/icon_reply_edit.png" /><img class="del" src="/img/icon_reply_delete.png" /></div>
			<div class="comments_content">{{content}}</div>
			<div class="reply_area"><ul></ul></div>
		</li>

</script>



<script id="projectcard_list_template" type="text/x-nawa-template">

				<li class="{{is_ended_string}}">
					<div class="result_list_title">{{title}}</div>
					<div class="img_area"><img src="/Project/Pic/{{id}}?width=380&height=300" /></div>
					<div class="result_info">					
						<div class="nums_of_member">
							<span class="joined member number">{{participant_actual_count}}</span>
							/
							<span class="admitted member number">{{max_user_count}}</span>
						</div>
						<div class="dday">D{{recruit_due_date_relative}}</div>
						<div class="progress">
							<div class="progress_value" style="width:{{progress_value}}%"></div>
						</div>
						<div class="project_info">
							<div class="project_date">{{meeting_date}}</div>
							/
							<div class="project_place">{{place}}</div>
						</div>
						<div class="action_area">{{btn}}</div>						
					</div>
				</li>

</script>

<script id="usercard_list_template" type="text/x-nawa-template">

				<li>
					<div class="user_pic">
						<img src="/User/Pic/{{email}}?width=60&height=60" user_email="{{email}}" />
					</div>
					<div class="user_name" user_email="{{email}}">
						{{name}}
					</div>
					<div class="action_area">{{btn}}{{btn2}}</div>
				</li>

</script>

<script id="userminimal_list_template" type="text/x-nawa-template">

				<li class="user userpic50">
					<img src="/User/Pic/{{email}}?width=50&height=50" />
					<div class="username">{{name}}</div>
				</li>

</script>


</body>
</html>

$(function() {
	

	$("#profile_action_submit").click(function () {
		profileEdit();
	});
	
});

function getProfileData(email) {
		
	server.getUserProfileData({ 'email': email }, { async: true, callback: function (response) {
		console.log(response.profile);
		var followers = response.profile.followers;
		var followings = response.profile.followings;
		var leading_projects = response.profile['leading-projects'];
		var participating_projects = response.profile['participating-projects'];
		var user_info = response.profile['user-info'];
		
		var count_project = leading_projects.length + participating_projects.length;
		var count_following = followings.length;
		var count_follower = followers.length;

		$("#user_activity .tab_title_list li:nth-child(1) .number").text(count_project);
		$("#user_activity .tab_title_list li:nth-child(2) .number").text(count_following);
		$("#user_activity .tab_title_list li:nth-child(3) .number").text(count_follower);
		
		
		/*
followers
followings
	email: "test-702280724@test.com"
	gender: 2
	name: "-1141288117"
	regdate: "2015-07-22 00:03:45.0"

leading-projects[0]
	comments_count: 5
	description: "desc 1412754062"
	description_title: "desc -342073801"
	id: "50bf6347-90da-4251-a3c8-89d789dc1eaf"
	is_active: 1
	leader_email: "test961617971@test.com"
	leader_name: "1003417847"
	long_project: 2
	max_user_count: 10
	meeting_date: "2015-07-20"
	meeting_time: "meeting_time 254097661"
	participant_actual_count: 0
	place: "place 695573660"
	recruit_due_date: "2015-07-27 10:58:46.0"
	regdate: "2015-07-20 10:58:46.0"
	title: "title 1885642745"

participating-projects

user-info
	email: "test961617971@test.com"
	gender: 2
	name: "1003417847"
	regdate: "2015-07-20 10:58:46.0"

		 */		
		$("#profile_userinfo .user_name .username").html(user_info.name);
		$("#profile_userinfo .edit_button").attr("user_email", user_info.email);		
		$("#profile_userinfo .user_email span").text(user_info.email);
		$("#profile_userinfo .action_area button").attr("user_email", user_info.email);
		
		if( myInfo != null && user_info.email == myInfo.email ) {
			// 로그인 사용자
			$("#profile_userinfo .edit_button").html("<img src='/img/icon_profile_edit.png'>");
		} else {
			if(myInfo != null) {
				server.getUserProfile({ 'email': user_info.email }, { async: true, callback: function (response) {
					var isFollow = false;
					if( response.success == "1" ) {
						if( response.user.am_i_follow_him == "1" ) {
							isFollow = true;
							// 팔로우 된 상태
							$("#profile_userinfo .action_area").html('<button type="button" class="enable unfollow" data-email="'+user_info.email+'">팔로우</button>');
						}
					}
					if( isFollow == false) {
						// 팔로우 안된 상태
						$("#profile_userinfo .action_area").html('<button type="button" class="enable follow" data-email="'+user_info.email+'"><img src="/img/icon_follow.png" /></button>');
						
					}
					console.log(response);
				} });
			}
			
		}


		
		
		
		// 프로필 - 프로젝트 목록
		var projectcard_list_template_html = $("#projectcard_list_template").html();
		
		$(participating_projects).each(function() {
			this.is_ended_string = (this.is_active==0)?"ended":"";
			this.progress_value = this.participant_actual_count/this.max_user_count*100;
			
			
			if( myInfo != null && this.leader_email == myInfo.email ) { 
				// my project
				this.btn = '<button type="button" class="setting type3" project_id="'+this.id+'"><img src="/img/icon_setting_gray.png" /></button>';
			} else if (this.is_active == 0) {				
				this.btn = '<button type="button" class="disable"">마감</button>';
			} else if (myInfo != null) {			
				var res = server.getIsProjectParticipated({ 'projectId': this.id, 'email': myInfo.email }, { async: false });
				if( res.success = "1") {
					if( res.isParticipated ) { // 참여중
						this.btn = '<button type="button" class="enable" data-projectid="'+this.id+'">참여중</button>';
					} else {
						this.btn = '<button type="button" class="enable join" data-projectid="'+this.id+'">참여하기</button>';
					}
				}		
			}
			
		});
		Template.render(projectcard_list_template_html, participating_projects, "#participating_project ul");
		

		$(leading_projects).each(function() {
			this.is_ended_string = (this.is_active==0)?"ended":"";
			this.progress_value = this.participant_actual_count/this.max_user_count*100;

			if( myInfo != null && this.leader_email == myInfo.email ) { 
				// my project
				this.btn = '<button type="button" class="setting type3" project_id="'+this.id+'"><img src="/img/icon_setting_gray.png" /></button>';
			} else if (this.is_active == 0) {				
				this.btn = '<button type="button" class="disable"">마감</button>';
			} else if (myInfo != null) {
				var res = server.getIsProjectParticipated({ 'projectId': this.id, 'email': myInfo.email }, { async: false });
				if( res.success = "1") {
					if( res.isParticipated ) { // 참여중
						this.btn = '<button type="button" class="enable" data-projectid="'+this.id+'">참여중</button>';
					} else {
						this.btn = '<button type="button" class="enable join" data-projectid="'+this.id+'">참여하기</button>';
					}
				}
			}
		});
		Template.render(projectcard_list_template_html, leading_projects, "#leading_project ul");
		
		

		// 프로젝트 관리
		$("button.setting").click(function() {
			if(isSet($(this).attr("project_id")))
				window.location.href = urlPath + "/project_edit.jsp?project_id=" + $(this).attr("project_id");
		});
		
		
		
		
		
		// 프로필 - 팔로잉 목록

		var usercard_list_template_html = $("#usercard_list_template").html();
		
		
		$(followings).each(function() {
			if( myInfo != null && this.email == myInfo.email ) return;
			var res = server.getUserProfile({ 'email': this.email }, { async: false});
			if( res.success = "1") {
				if( res.user.am_i_follow_him == "1") {
					this.btn = '<button type="button" class="enable unfollow" data-email="'+this.email+'">팔로우</button>';
				} else {
					this.btn = '<button type="button" class="enable follow" data-email="'+this.email+'"><img src="/img/icon_follow.png" /></button>';
				}
			}
		});
		Template.render(usercard_list_template_html, followings, "#user_activity .people_list.following ul");
		
		

		// 프로필 - 팔로워 목록

		$(followers).each(function() {
			if( myInfo != null && this.email == myInfo.email ) return;
			var res = server.getUserProfile({ 'email': this.email }, { async: false});
			if( res.success = "1") {
				if( res.user.am_i_follow_him == "1") {
					this.btn = '<button type="button" class="enable unfollow" data-email="'+this.email+'">팔로우</button>';
				} else {
					this.btn = '<button type="button" class="enable follow" data-email="'+this.email+'"><img src="/img/icon_follow.png" /></button>';
				}
			}
		});
		Template.render(usercard_list_template_html, followers, "#user_activity .people_list.follower ul");
		
		//$("button[data-email='"+myInfo.email+"']").remove();
		
		
		
		
		
		
	} });
	
}

function getProfile(email) {
	server.getUserProfile({ 'email': email }, { async: true, callback: function (response) {
		console.log(response.user);
		var user = response.user;
		

		$("#edit_profile_name").val(user.name);
		$("#edit_profile_email").val(user.email);
		
		$("#signup_gender").val(user.gender);
		$("#signup_gender_" + user.gender).click();
		
		
		
	} });
}


function profileEdit() {	


	var userPic = $("#edit_profile_profile_img")[0].files;
	var userName = $("#edit_profile_name").val();
	var userEmail = $("#edit_profile_email").val();
	var userGender = $("#signup_gender").val();

	var userPwd = $("#edit_profile_pw").val();
	var userPwdCheck = $("#edit_profile_pw_check").val();
	

	if (userPwd != userPwdCheck) {
		alert("비밀번호 확인 틀림");
		$("#edit_profile_pw").focus();
		return false;
	}

	var encryptedPwd = hex_sha512(userPwd);
	
	var formData = new FormData();
	
	//formData.append("email", user_email); // !!!! 나중에 방법 수정하자.
	
	formData.append("name", userName);
	formData.append("password", encryptedPwd);
	formData.append("gender", userGender);
	if (userPic.length > 0)
		formData.append("userPic", userPic[0]);

	server.setUserProfile({'email':userEmail, 'formData':formData}, {
		async: true,
		callback: function (response) {
			if (response.success == "1") {
				location.href = "profile_project.jsp?email=" + userEmail;
			} else {
				alert(response.msg);
			}
		}
	});
}

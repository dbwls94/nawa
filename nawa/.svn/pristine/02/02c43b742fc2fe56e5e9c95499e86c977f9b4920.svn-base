
$(function() {
	

	// 창닫기 버튼
	$(".close_btn").click(function() {
		event.stopPropagation();
		if( $(this).parents(".layer").is(":visible") ) $(this).parents(".layer").hide();
		else toggleNotification();
		$("#mask").hide();
	});

	// 로그인 창 열기
	$("#login_button button").click(function() {
		event.stopPropagation();
		$(".layer").hide();
		$("#login_layer").show();
		$("#login_id").focus();
		$("#mask").show();
	});
	
	// 로그인 / 회원가입 창 열기 - 메뉴
	$(".layer .layer_menu div").click(function() {
		event.stopPropagation();
		if( $(this).hasClass("on") ) return;
		$(this).parents(".layer").hide();
		if( $(this).hasClass("login") ) {
			$("#login_layer").show();
			$("#login_id").focus();
		} else if( $(this).hasClass("signup") ) {
			
			if( $(window).height() < $("#signup_layer").height()+150) {
				$("#signup_layer").addClass("position_fixing");
			}
			
			$(window).resize(function() {
				if( $(window).height() < $("#signup_layer").height()+150) {
					$("#signup_layer").addClass("position_fixing");
				} else {
					$("#signup_layer").removeClass("position_fixing");
				}
			});
			
			$("#signup_layer").show();	
			$("#signup_name").focus();	
		}
	});

	// 로그인 처리
	$("#login_btn").click(function() {
		event.stopPropagation();
		loginAction();
	});
	$("#login_id, #login_pw").keydown(function(key) {
		if (key.keyCode == 13) {
			loginAction();
		}		
	});
	// 로그아웃 처리
	$("#logout_button").click(function() {
		event.stopPropagation();
		logoutAction();
	});
	// 비밀번호 찾기
	$("#find_pw").click(function() {
		event.stopPropagation();
		window.location.href = urlPath + "/findpw.jsp";
	});
	

	// 로그인 에러메세지 감추기.
	$(".layer .layer_header").click(function() {
		event.stopPropagation();
		if( $(this).hasClass("on") ) hideLoginErrorMsg();
	});
	


	// 회원가입 처리
	$("#signup_btn").click(function() {
		event.stopPropagation();
		signupAction();
	});
	$("#signup_name, #signup_email, #signup_pw, #signup_pw_check").keydown(function(key) {
		if (key.keyCode == 13) {
			signupAction();
		}		
	});
	// 가입 이메일 유효성 및 중복체크
	$("#signup_email").focusout(function() {
		emailExistCheckAction();
	});
	

	// 팔로우 하기
	$("button.follow").click(function() {
		actionFollow($(this));
	});
	// 언팔로우 하기
	$("button.unfollow").click(function() {
		actionUnfollow($(this))
	});
	
	
	
	// 검색 결과
	$("#search_button").click(function() {
		event.stopPropagation();
		if($("#search_query").val().trim().length < 1 ) return;
		window.location.href = urlPath + "/search.jsp?v=" + $("#search_query").val();
	});
	$("#search_query").keydown(function(key) {
		if (key.keyCode == 13) {
			$("#search_button").click();
		}		
	});
	
	
	// 성별선택
	$(".select_gender .select_gender_content div").click(function() {
		event.stopPropagation();
		if( $(this).hasClass("on") ) return;
		$(this).parent().find("div").removeClass("on");
		$(this).addClass("on");
		$("#signup_gender").val($(this).attr("id").replace("signup_gender_",""));
	});
	
	
	
	// 알림창 보기
	$("#notification_button").click(function() {
		event.stopPropagation();
		notification_setting('1');
		toggleNotification();
	});

	// 알림창 내소식 보기
	$(".notification_menu .my_notification").click(function() {
		event.stopPropagation();
		$(".notification_menu div").removeClass("on");
		$(this).addClass("on");
		notification_setting('1');
	});
	// 알림창 팔로잉 보기
	$(".notification_menu .following_notification").click(function() {
		event.stopPropagation();
		$(".notification_menu div").removeClass("on");
		$(this).addClass("on");
		notification_setting('2');
	});
	
	
	// 사용자 프로필 보기
	$(".user_pic, .user img, .user_pic img, .user .username, .people_list .user_name").click(function() {
		event.stopPropagation();
		viewProfile($(this));
	});

	// 프로필 수정
	$("#profile_userinfo .edit_button").click(function() {
		event.stopPropagation();
		if(isSet($(this).attr("user_email")))
			window.location.href = urlPath + "/profile_edit.jsp?email=" + $(this).attr("user_email");
	});
	


	// 프로젝트 관리
	$("button.setting").click(function() {
		event.stopPropagation();
		if(isSet($(this).attr("project_id")))
			window.location.href = urlPath + "/project_edit.jsp?project_id=" + $(this).attr("project_id");
	});
	
	// 취소하기, 이전으로 버튼 처리
	$("button.cancel_button").click(function() {
		event.stopPropagation();
		window.history.back();
	});
	
	
	
	// 화면 여백 클릭 이벤트 처리용
	$(document.body).click(function(e) {
		console.log("click : ");
		console.log($(e.target));
		
		if(e.target.tagName == "BUTTON") {
			// 프로젝트 관리
			if( $(e.target).hasClass("setting") ) {
				if(isSet($(this).attr("project_id")))
					window.location.href = urlPath + "/project_edit.jsp?project_id=" + $(this).attr("project_id");
				return;
			}
			// 팔로우 하기
			if( $(e.target).hasClass("follow") ) {
				actionFollow($(e.target));
				return;
			}
			// 언팔로우 하기
			if( $(e.target).hasClass("unfollow") ) {
				actionUnfollow($(e.target));
				return;
			}

			// 프로젝트 참여 하기
			if( $(e.target).hasClass("join") ) {
				actionJoinProject($(e.target));
				return;
			}

			// 프로젝트 참여 승인
			if( $(e.target).hasClass("accept") ) {
				actionJoinProjectAccept($(e.target), true);
				return;
			}
			// 프로젝트 참여 거절
			if( $(e.target).hasClass("deny") ) {
				actionJoinProjectAccept($(e.target), false);
				return;
			}
		}
		
		// 프로젝트 참여 멤버 전체 보기/감추기 see_more_member		
		if( $(e.target).hasClass("see_more_member") ) {
			viewTotalMember($(e.target));
			return;
		}


		viewProfile($(e.target));
		
		
		// 댓글창 닫기
		//if( $(e.target).parents(".button_comment_open").length < 1 && $(".comments:visible").length > 0 ) {
		if($(".comments:visible").length > 0) {
			if($(e.target).parents(".comments").length < 1) {
				$(".button_comment_open").find("img").attr("src","/img/icon_reply_close.png");
				$(".comments").fadeOut("fast");
			}
		}
			

		// 로그인/회원가입 창 닫기
		if($(".layer:visible").length > 0) {
			if($(e.target).parents(".layer").length < 1) {
				$(".layer").fadeOut("fast");
				$("#mask").fadeOut("fast");
			}		
		}
			

		// 알림 창 닫기
		if($("#notification_wrap:visible").length > 0) {
			if($(e.target).parents("#notification_wrap").length < 1) {
				$("#notification_wrap").animate({width:'toggle'},100);
			}
		}
	});
	
	

	if(myInfo != null) {
		// 사용자 사진
		getUserPic($("#login_user img"), myInfo.email, 40);
		$("#login_user a").attr("href","/profile_project.jsp?email=" + myInfo.email)
	}
});


function isSet(v) {
	return (typeof v != "undefined" && v.length > 0);
}


/*
 * 화면 여백 클릭 이벤트 처리용 함수
 */
function clickRelese() {
	// 댓글창 닫기
	$(".button_comment_open").find("img").attr("src","/img/icon_reply_close.png");
	$(".comments").animate({opacity:'toggle',display:'block'});
}


/*
 * 사용자 정보 보기
 */
function viewProfile(obj) {
	if(isSet($(obj).attr("user_email")))
		window.location.href = urlPath + "/profile_project.jsp?email=" + $(obj).attr("user_email");
}

/*
 * 팔로우 하기
 */
// 
function actionFollow(targetElement) {
	event.stopPropagation();
	var targetEmail = $(targetElement).data("email");
	if( typeof targetEmail != "string" || targetEmail.length < 1 ) return;
	server.setFollow({ 'targetEmail': targetEmail }, { async: true, callback: function (response) {
		//alert("팔로우");
		$(targetElement).removeClass("follow");
		$(targetElement).addClass("unfollow");
		$(targetElement).html("팔로우");
		//'<button type="button" class="enable unfollow" data-email="'+user_info.email+'">팔로우</button>'
	} });
	
}
/*
 * 언팔로우 하기
 */
function actionUnfollow(targetElement) {
	event.stopPropagation();
	var targetEmail = $(targetElement).data("email");
	if( typeof targetEmail != "string" || targetEmail.length < 1 ) return;
	server.setUnFollow({ 'targetEmail': targetEmail }, { async: true, callback: function (response) {
		//alert("언팔로우");
		$(targetElement).removeClass("unfollow");
		$(targetElement).addClass("follow");
		$(targetElement).html('<img src="/img/icon_follow.png" />');
		//'<button type="button" class="enable follow" data-email="'+user_info.email+'"><img src="/img/icon_follow.png" /></button>'
	} });
	
}

/*
 * 프로젝트 참여 하기
 */
function actionJoinProject(targetElement) {
	event.stopPropagation();
	var projectId = $(targetElement).data("projectid");
	if( typeof projectId != "string" || projectId.length < 1 ) return;
	
	server.setProjectParticipate({ 'projectId': projectId }, { async: true, callback: function (response) { 
		// 참여 신청 후
		$(targetElement).html('참여중'); // 승인대기중 상태 없음. 
		$(targetElement).removeClass("join");
	} });
		
}

/*
 * 프로젝트 참여 승인/거절
 */
function actionJoinProjectAccept(targetElement, isAccept) {
	event.stopPropagation();
	var projectId = $(targetElement).data("projectid");
	if( typeof projectId != "string" || projectId.length < 1 ) return;
	var targetEmail = $(targetElement).data("email");
	if( typeof targetEmail != "string" || targetEmail.length < 1 ) return;
	
	server.setProjectAcceptParticipate({ 'projectId': projectId, 'participationUserEmail': targetEmail, accept: isAccept }, { async: true, callback: function (response) {
		window.location.reload();
		// console.log(response);
	} });
	
}



/*
 * 로그인
 */
function loginAction() {
	var email = $("#login_id").val();
	var pwd = $("#login_pw").val();
	if (email == "") { showLoginErrorMsg("#login_layer", "E-mail을 입력하십시오."); $("#login_id").focus(); return false; }	
	if (pwd == "") { showLoginErrorMsg("#login_layer", "비밀번호를 입력하십시오."); $("#login_pw").focus(); return false; }
	if( !emailStringCheckAction(email) ) { showLoginErrorMsg("#login_layer", "E-mail을 올바로 입력하십시오."); $("#login_id").focus(); return false; }

	server.setLogin({'email' : email,'password' : hex_sha512(pwd)}, {
		async : true,
		callback : function(response) {
			if( response.success == '1' ) {
				// window.location.href = urlPath + "/project_list.jsp";
				window.location.reload();
			} else {
				showLoginErrorMsg("#login_layer", response.msg);
			}
		}
	});
}
/*
 * 로그아웃
 */
function logoutAction() {
	server.setLogout({
		async : true,
		callback : function(response) {
			window.location.href = urlPath + "/project_list.jsp";
		}
	})
}

/*
 * 레이어 에러메세지 보기 / 감추기
 */
function showLoginErrorMsg(target, msg) {
	$(target + " .layer_header").addClass("on").find(".layer_message").text(msg);
	setTimeout(hideLoginErrorMsg, 2000);
}
function hideLoginErrorMsg() {
	$(".layer_header").removeClass("on").find(".layer_message").text("");
}




/*
 * 회원가입
 */
function signupAction() {

	var userPic = $("#signup_profile_img")[0].files;
	var userName = $("#signup_name").val();
	var userEmail = $("#signup_email").val();
	var userPwd = $("#signup_pw").val();
	var userPwdCheck = $("#signup_pw_check").val();
	var userGender = $("#signup_gender").val();


	if (userName == "") { showLoginErrorMsg("#signup_layer", "이름을 입력하십시오."); $("#signup_name").focus(); return false; }
	if (userEmail == "") { showLoginErrorMsg("#signup_layer", "E-mail을 입력하십시오."); $("#signup_email").focus(); return false; }	
	if (userPwd == "") { showLoginErrorMsg("#signup_layer", "비밀번호를 입력하십시오."); $("#signup_pw").focus(); return false; }
	if (userPwdCheck == "") { showLoginErrorMsg("#signup_layer", "비밀번호 확인을 입력하십시오."); $("#signup_pw_check").focus(); return false; }
	
	/*
	var RegexName = /^[가-힣]{2,4}$/; // 이름 유효성 검사 2~4자 사이 영문 이름[a-zA-Z]{5,10}$/i;
	if (!RegexName.test(userName)) {
		alert("한글 이름만");
		$("#signup_name").focus();
		return false;
	}
	*/
	// 이메일 체크
	if( !emailExistCheckAction() ) return false;
	
	if (userPwd != userPwdCheck) {
		showLoginErrorMsg("#signup_layer", "비밀번호 확인 틀림");
		$("#signup_pw").focus();
		return false;
	}

	var encryptedPwd = hex_sha512(userPwd);

	var formData = new FormData();
	formData.append("email", userEmail);
	formData.append("password", encryptedPwd);
	formData.append("name", userName);
	formData.append("gender", userGender);
	if (userPic.length > 0)
		formData.append("userPic", userPic[0]);

	server.setSignUp(formData, {
		async : true,
		callback : function(response) {
			window.location.href = urlPath + "/project_list.jsp";
			
//			콜백함수 실행로직 확인해보자. server.js [L:53]
//			if( response.success == '1' ) {
//				window.location.href = urlPath + "/project_list.jsp";
//			} else {
//				alert(response.msg);
//			}
		}
	});// setSignUp();
}; // signupAction();

/*
 * 이메일 형식체크
 */
function emailStringCheckAction(email) {
	var RegexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	if (!RegexEmail.test(email)) return false;
	return true;
}; // emailStringCheckAction();

/*
 * 가입 이메일 중복체크
 */
function emailExistCheckAction() {
	var userEmail = $("#signup_email").val();
	if( userEmail.trim().length < 1 ) return;

	if( !emailStringCheckAction(userEmail) ) {
		showLoginErrorMsg("#signup_layer", "이메일 양식틀림");
		$("#signup_email").val("");
		$("#signup_email").focus();
		return false;
	}
	
	server.getIsEmailExists({ 'email': userEmail }, {
		async : true,
		callback : function(response) {			
			if( response.isEmailExists === true ) {
				showLoginErrorMsg("#signup_layer", "이메일 사용중");
				$("#signup_email").focus();
				return false;
			} else {
			}
		}
	});// getIsEmailExists();
	
	return true;
}; // emailExistCheckAction();




/*
 * 알림 토글
 */
function toggleNotification() {
	$("#notification_wrap").animate({width:'toggle'},100);
}
/*
 * 알림 내용 넣기
 */
function notification_setting(type) {
	if( type !== '1' && type !== '2' ) type = '1';	
	$("#notification_wrap .notification_list").empty();
	$("#notification_wrap .notification_list").append($("#noti_type_"+type).html());
}


/*
 * 사용자 사진
 */
function getUserPic(obj, email, size) {

	$(obj).attr("src", "/User/Pic/"+encodeURI(email)+"?width="+size+"&height="+size+"" );
}

/*
 * 사용자 사진 배경
 */
function getUserPicBG(obj, email, size) {

	$(obj).css("background-image", "url('/User/Pic/"+encodeURI(email)+"?width="+size+"&height="+size+"')" );
}




function getSearchResultUser(v) {
	server.getSearchUser({ 'keyword': v }, { async: true, callback: function (response) { 
		console.log(response.result);
		var searchUsers = response.result;
		var count_searchUsers = searchUsers.length;
		
		$(".number.user").text(parseInt($(".number.user").text()) + count_searchUsers);		

		var usercard_list_template_html = $("#usercard_list_template").html();		
		$(searchUsers).each(function() {
			if( this.email == myInfo.email ) return;
			var res = server.getUserProfile({ 'email': this.email }, { async: false});
			if( res.success = "1") {
				if( res.user.am_i_follow_him == "1") {
					this.btn = '<button type="button" class="enable unfollow" data-email="'+this.email+'">팔로우</button>';
				} else {
					this.btn = '<button type="button" class="enable type3 follow" data-email="'+this.email+'"><img src="/img/icon_follow.png" /></button>';
				}
			}
		});
		Template.render(usercard_list_template_html, searchUsers, " .people_list ul");
	} });
}

function getSearchResultAllProject(v) {
	server.getSearchProject({ 'keyword': v }, { async: true, callback: function (response) { 
		console.log(response.result);
		var searchProjects = response.result;
		var count_searchProjects = searchProjects.length;

		$(".number.project").text(parseInt($(".number.project").text()) + count_searchProjects);		

		// 프로젝트 목록
		var projectcard_list_template_html = $("#projectcard_list_template").html();		
		$(searchProjects).each(function() {
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
		Template.render(projectcard_list_template_html, searchProjects, ".result_list.all_project ul");
		
	} });
}

function getSearchResultMyProject(v) {
	server.getSearchProject({ 'keyword': v }, { async: true, callback: function (response) { 
		console.log(response.result);
		var searchProjects = response.result;
		var count_searchProjects = searchProjects.length;

		$(".number.project").text(parseInt($(".number.project").text()) + count_searchProjects);		

		// 프로젝트 목록
		var projectcard_list_template_html = $("#projectcard_list_template").html();		
		$(searchProjects).each(function() {
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
		Template.render(projectcard_list_template_html, searchProjects, ".result_list.my_project ul");
		
	} });
}


/*
 * 프로젝트 참여 멤버 전체 보기/감추기 see_more_member
 */
function viewTotalMember(obj) {
	event.stopPropagation();
	/*
	$(".see_more_member").click(function() {
		event.stopPropagation();
		$total_member = $(this).parents(".project_info").find(".member_list_total");
		if( $total_member.is(':visible') ) {
			$total_member.animate({opacity:'toggle',display:'block'});
		} else {
			$total_member.animate({opacity:'toggle',display:'none'});
		}
	});
	*/

	$total_member = $(obj).parents(".project_info").find(".member_list_total");
	if( $total_member.is(':visible') ) {
		$total_member.animate({opacity:'toggle',display:'block'});
	} else {
		$total_member.animate({opacity:'toggle',display:'none'});
	}
}




/*
 * 템플린용 코드
 */
Template = new function() {
	this.tplTagRegExp = /\{\{[a-zA-Z0-9_]*\}\}/gi;
};

/**
 * 화면에 list template 을 그림
 * 
 * @param {String} templateCode HTML 코드 작성용 템플릿 코드
 * @param {Object} mappingData 템플릿 코드에 매핑시킬 데이터 객체
 * @param {String} showId 데이터가 매핑된 템플릿 코드를 뿌려줄 화면 객체의 ID
 */
Template.render = function(templateCode, mappingData, showID) {
	var codes = "";
	for(var i = 0 ; i < mappingData.length ; i++ ) {
		var rowData = mappingData[i];

		var tmpStr = templateCode.replace(this.tplTagRegExp, function($1) {
			var tag = $1.substring(2, $1.length-2);
			var data = rowData[tag];
			
			
			
			if( data != null ) {
				return data;
			} else {
				return "";
			}
		});
		
		codes += tmpStr;
	}

	$(showID).append(codes);
};





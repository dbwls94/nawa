
function closeAll() {
	$("#projectList > li div.see_more").text("자세히 보기");
	$(".comments").hide();
	$(".project_memo").hide();
	$(".member_list_total").hide();
}


$(function() {
	

	$("#project_action_submit").click(function () {
		event.stopPropagation();
		projectFormSubmit();
	});
	
});



function getProjectList() {
	
	server.getProjectList({ 'page': 1 }, { async: true, callback: function (response) {
		console.log(response.projects);
		
		$(response.projects).each(function() {
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
		
		var project_list_template_html = $("#project_list_template").html();
		Template.render(project_list_template_html, response.projects, "#projectList");
		
		var index = 0;
		$(response.projects).each(function() {
			getProjectparticipatingUsers("#project_" + response.projects[(index)].id, response.projects[(index++)].id);
		});
		
		
		
		afterProjectListShow();
		
	} });
	
}

function getProject(projectId) {
	
	server.getProjectInfo({ 'projectId': projectId }, { async: true, callback: function (response) {
		console.log(response.project);
		var project = response.project;
		
		/*
		description: "desc 1412754062"
		description_title: "desc -342073801"
		id: "50bf6347-90da-4251-a3c8-89d789dc1eaf"
		leader_email: "test961617971@test.com"
		long_project: 2
		max_user_count: 10
		meeting_date: "2015-07-20"
		meeting_time: "meeting_time 254097661"
		place: "place 695573660"
		recruit_due_date: "2015-07-27 10:58:46.0"
		regdate: "2015-07-20 10:58:46.0"
		title: "title 1885642745"	

		 */

		$("h3").text(project.title);

		// due date
		
		

		$(".joined_member .number").text(project.max_user_count);
		$(".admitted_member .number").text(project.max_user_count);

		$(".progress .progress_value").css("width", (project.max_user_count/project.max_user_count*100)+"%" );
		
		// 장기프로젝트 값 확인.
		if( project.long_project == 2 )
			$(".project_type").text("장기프로젝트");
		
		// 참여인원

		$("dd.first_meet").text(project.meeting_date);
		$("dd.meet_time").text(project.meeting_time);
		$("dd.meet_place").text(project.place);
		

		$(".project_memo > p").text(project.description_title);
		$(".project_memo > pre").text(project.description);

		$(".button_comment_open").attr("projectId", project.id );
		$(".comments_contents ul").attr("id", "comment_" + project.id );

		$(".user.creator_info > img, .user > div").attr("user_email", project.leader_email);
		$(".user.creator_info > img").attr("src", "/User/Pic/" + project.leader_email + "?width=60&height=60");
		$(".user.creator_info .username").text(project.leader_name);
		
		$(".project_image > img").attr("src", "/Project/Pic/" + project.id + "?width=380&height=300");
		
		getProjectparticipatingUsers("#project_detail", project.id);
		
		afterProjectListShow();
		
		
	} });
	
}


function getProjectComment(projectId) {
	event.stopPropagation();
	
	server.getComment({ 'projectId': projectId }, { async: true, callback: function (response) {
		console.log(response.comments);
		
		$(response.comments).each(function() {
			this.string_date = calDate(this.regdate);
			this.depthNum = this.depth.split("/").length - 1;
		});

		$(".comments_contents ul").empty();
		
		var project_comment_template_html = $("#comment_list_template").html();
		Template.render(project_comment_template_html, response.comments, "#comment_" + projectId);
		

		
		//$comment_area.fadeIn(500, function() {
			var commentObj = $("div.comments:visible");
			if( $(commentObj).parent().find(".project_memo").offset().top >  $(commentObj).offset().top )
				$("html, body").animate({scrollTop:$(commentObj).offset().top});
		//});
		
		
		afterProjectCommentShow();
		
	} });
	
}

function calDate(targetDate) {
	var nowDate = new Date();
	var targetDate = new Date(targetDate);

	if( ((nowDate-targetDate)/1000/60)<10 ) return "방금"; // 10분 이전이면 "방금".
	else if( ((nowDate-targetDate)/1000/60)<60 ) return parseInt((new Date()-nowDate)/1000/60) + "분 전"; // 1시간 이전이면 (예)"10분 전".
	else if( nowDate.getDate() == targetDate.getDate() ) return parseInt((nowDate-targetDate)/1000/60/60) + "시간 전"; // 날짜가 같으면 (예) "1시간 전".
	else if( (nowDate.getDate()-targetDate.getDate())==1 ) return "어제"; // 날짜가 하루차이면 "어제".
	else return targetDate.getFullYear() + "." + targetDate.getMonth() + "." + targetDate.getDate();
}


function afterProjectListShow() {

	
	// 프로젝트 내용 보기/감추기
	$("#projectList > li div.see_more").click(function(){
		event.stopPropagation();		
		$memo = $(this).parent().find(".project_memo");
		if( $memo.is(':visible') ) {
			$(this).text("자세히 보기");
			closeAll();
			$("html, body").animate({scrollTop:$memo.parent().offset().top});
		} else {
			closeAll();
			$(this).text("접어두기");
			$memo.show();
			$("html, body").animate({scrollTop:$memo.offset().top});
		}
	});
	
	// 프로젝트 참여 멤버 전체 보기/감추기 see_more_member
	$(".see_more_member").click(function() {
		viewTotalMember($(this));
	});
	
	// 코멘트 보기/감추기
	$(".button_comment_open").click(function() {
		event.stopPropagation();
		$memo = $(this).parent();
		if( !$memo.is(':visible') ) return;
		
		$comment_area = $(this).parent().parent().parent().find(".comments");
		if( $comment_area.is(':visible') ) {
			//$comment_area.animate({opacity:'toggle',display:'block'});
			//$comment_area.hide("fast");
			$comment_area.fadeOut("fast");
			$(this).find("img").attr("src","/img/icon_reply_close.png");
			$("html, body").animate({scrollTop:$memo.offset().top});
		} else {
			getProjectComment($(this).attr("projectId"));
			//$comment_area.animate({opacity:'toggle',display:'none'});
			//$comment_area.show("fast");
			$comment_area.fadeIn("fast");
			
			$(this).find("img").attr("src","/img/icon_reply_open.png");
		}	
	});
	

	// 사용자 프로필 보기
	$(".user img, .user_pic img, .user .username, .people_list .user_name").click(function() {
		event.stopPropagation();
		if(isSet($(this).attr("user_email")))
			window.location.href = urlPath + "/profile_project.jsp?email=" + $(this).attr("user_email");
	});
	
}

// 댓글, 대댓글 입력창 전체 삭제
function removeCommentReplyInput() {
	$(".comments li .reply_area ul .reply_input").remove();
	$(".comments li.edit").each(function() {
		var c_text = $(this).find(".comments_content input").val();
		$(this).find(".comments_content").empty();
		$(this).find(".comments_content").text(c_text);
		$(this).removeClass("edit");
	});
	event.stopPropagation();
}

function afterProjectCommentShow() {
	
	// 대댓글 수정/삭제
	$(".comments li .comments_action img").click(function() {
		event.stopPropagation();

		var $p_li = $(this).parent().parent();

		var userEmail = $p_li.attr('useremail');
		if( userEmail != myInfo.email ) return;
		
		var seq = $p_li.attr('comment_seq');
		var projectId = $p_li.attr('project_id');
		
		if( $(this).hasClass("edit") ) { // 수정			
			removeCommentReplyInput();			
			
			$($p_li).addClass("edit");			
			
			var c_text = $($p_li).find(".comments_content").text();
			$($p_li).find(".comments_content").empty();
			$($p_li).find(".comments_content").append("<input class='edit_comment' comment_seq='"+seq+"' project_id='"+projectId+"' type='text' value='"+c_text+"'>");
			
			$("input.edit_comment").focus();
			$("input.edit_comment").focusout(function() { removeCommentReplyInput(); });			
			$("input.edit_comment").click(function() { event.stopPropagation(); });
			$("input.edit_comment").keydown(function(key) {
				if (key.keyCode == 13) {
					var c_seq = $(this).attr('comment_seq');
					var c_projectId = $(this).attr('project_id');
					var c_text = $(this).val();					
					server.setUpdateComment({ 'seq': seq, 'projectId': projectId, content: c_text }, { async: true, callback: function (response) {
						getProjectComment(projectId);
					} });
				}
				
				event.stopPropagation();
			});
			
			
		} else if( $(this).hasClass("del") ) { // 삭제			
			server.setDeleteComment({ 'seq': seq, 'projectId': projectId }, { async: true, callback: function (response) {
				getProjectComment(projectId);
			} });
			
		} 
		
		
		
		event.stopPropagation();
	});
	
	// 대댓글 쓰기창 보기/감추기
	$(".comments li").click(function() {
		event.stopPropagation();
		if($(this).hasClass("depth2")) {
			//$(".comments li[comment_depth='"+("/"+$(this).attr("comment_depth").split("/")[1])+"']").click();
			$(".comments li[comment_depth='"+("/"+$(this).attr("comment_depth").split("/")[1])+"']").trigger("click");
			return;
		}
		$reply_area = $(this).find(".reply_area");
		$reply_ul = $(this).find(".reply_area ul");		
		
		if( $reply_ul.find(".reply_input").is(':visible') ) {
			//$reply_ul.find(".reply_input").remove();
			removeCommentReplyInput();
		} else {
		
		
			var comment_seq = $(this).attr('comment_seq');
			var project_id = $(this).attr('project_id');
			
			$(".reply_input").remove();
			var reply_input = '<li class="reply_input"><img src="/User/Pic/'+myInfo.email+'?width=40&height=40" /><div class="comments_writer">'+myInfo.name+'</div><input comment_seq="' + comment_seq + '" project_id="' + project_id + '" type="text" name="comment_write" class="write_comment" placeholder="댓글은 여기에 입력하세요." /></li>';
			$reply_ul.prepend(reply_input);
			$reply_ul.find("input").focus();
			$reply_ul.find("input").click(function() { 
				event.stopPropagation(); 
			});
			$reply_ul.find("input").keydown(function(key) { 
				if (key.keyCode == 13) {
					addComment(this);
				}
			});
		}	
	});
	
	// 코멘트 액션
	$(".comments .comments_contents ul li").hover(function() {		
		if( $(this).attr('useremail') != myInfo.email ) return;
		
		$(".comments .comments_contents ul li").removeClass("on");
		$(this).addClass("on");
	}, function() {		
		if( $(this).attr('useremail') != myInfo.email ) return;
		$(".comments .comments_contents ul li").removeClass("on");
	});
	
	// 댓글 등록
	// write_comment
	$(".comments .write_comment").keydown(function(key) {
		if (key.keyCode == 13) {
			addComment(this);
		}
	});
	// 댓글 등록 input에 포커스 받으면 다른 입력창 모두 닫음.
	$(".comments .write_comment").focus(function(key) {
		removeCommentReplyInput();
	});
}

function addComment(obj){
	var $input = $(obj);
	if($input.hasClass("depth2")) return;
	
	var seq = $input.attr('comment_seq');
	var projectId = $input.attr('project_id');
	var content =$input.val();
	
	if($.trim(content).length < 1){
		return;
	}
	
	var response = server.setComment({ 'projectId': projectId, 'content': content, 'parent_seq': seq }, { async: false });
	if (response.success == '1') {
		getProjectComment(projectId);
		$input.val("");
	} else {
		console.log(response.msg);
	}
}



function getProjectForEdit(projectId) {
	
	server.getProjectInfo({ 'projectId': projectId }, { async: true, callback: function (response) {
		console.log(response.project);
		var project = response.project;
		
		/*
		description: "desc 1412754062"
		description_title: "desc -342073801"
		id: "50bf6347-90da-4251-a3c8-89d789dc1eaf"
		leader_email: "test961617971@test.com"
		long_project: 2
		max_user_count: 10
		meeting_date: "2015-07-20"
		meeting_time: "meeting_time 254097661"
		place: "place 695573660"
		recruit_due_date: "2015-07-27 10:58:46.0"
		regdate: "2015-07-20 10:58:46.0"
		title: "title 1885642745"	

		 */

		$("#project_name").val(project.title);

		//$(".joined_member .number").text(project.max_user_count);
		//$(".admitted_member .number").text(project.max_user_count);

		//$(".progress .progress_value").css("width", (project.max_user_count/project.max_user_count*100)+"%" );
		
		// 장기프로젝트 값 확인.
		if( project.long_project == 2 )
			$("#long_project").attr("checked","checked");
		
		// 참여인원

		$("#project_meet_date").val(project.meeting_date);
		$("#project_place").val(project.place);
		$("#project_time").val(project.meeting_time);

		$("#project_member_nums").val(project.max_user_count);
		var d = new Date(project.recruit_due_date);
		var dString = d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes();
		d = project.recruit_due_date.split(":");
		dString = d[0] + ":" + d[1];
		$("#project_limit_date").val(dString);
		

		$("#project_contents_title").val(project.description_title);
		$("#project_contents_memo").text(project.description);
		
				
	} });
	
}



function projectFormSubmit() {	

	var project_id = $("#project_id").val(); // 프로젝트 타이틀
	var isEdit = ( !!($("#project_id").val()) && project_id.length > 0 ); 
	var pType = (isEdit) ? "edit":"create";
	
	// 제목
	var project_name = $("#project_name").val(); // 프로젝트 타이틀
	var project_img = $("#project_img")[0].files; // 이미지
	var long_project = "1";
	if ($("#long_project").is(":checked")) {
		long_project = "2";
	} // 장기
	var project_meet_date = $("#project_meet_date").val(); // 첫만남
	var project_place = $("#project_place").val(); // 장소
	var project_time = $("#project_time").val(); // 시간
	var project_member_num = $("#project_member_nums").val(); // 모집인원
	var project_limit_date = $("#project_limit_date").val(); // 모집 마감일
	
	var project_contents_title = $("#project_contents_title").val(); // 설명  제목
	var project_contents_memo = $("#project_contents_memo").val(); // 설명 내용

	var formData = new FormData();
	if( isEdit ) formData.append("id", project_id);
	formData.append("title", project_name);
	if (project_img.length > 0)
		formData.append("project_pics", project_img[0]);
	formData.append("long_project", long_project);
	formData.append("meeting_date", project_meet_date);
	formData.append("meeting_time", project_time);
	formData.append("place", project_place);
	formData.append("max_user_count", project_member_num);
	formData.append("recruit_due_date", project_limit_date);
	formData.append("description_title", project_contents_title);
	formData.append("description", project_contents_memo);
	formData.append("entry_terms", "1");

	server.setProject(pType, formData, {
		async: true,
		callback: function (response) {
			if (response.success == "1") {
				if( isEdit )
					location.href = "project_edit.jsp?project_id=" + project_id;
				else 
					location.href = "project_list.jsp";
			} else {
				alert(response.msg);
			}
		}
	});
}


function getProjectMemberList(projectId) {

	server.setProjectWaitingAcceptUsers({ 'projectId': projectId }, { async: true, callback: function (response) {
		if( response.success === "0") return;
		console.log(response.waitings);
		
		
		var usercard_list_template_html = $("#usercard_list_template").html();
		$(response.waitings).each(function() {
			if( myInfo != null && this.email == myInfo.email ) return;
			var res = server.getUserProfile({ 'email': this.email }, { async: false});
			if( res.success = "1") {
				if( res.user.am_i_follow_him == "1") {
					this.btn = '<button type="button" class="enable unfollow" data-email="'+this.email+'">팔로우</button>';
				} else {
					this.btn = '<button type="button" class="enable follow" data-email="'+this.email+'"><img src="/img/icon_follow.png" /></button>';
				}
			}
			this.btn2 = '<button type="button" class="disable accept" data-projectid="'+projectId+'" data-email="'+this.email+'">승인</button><button type="button" class="disable deny" data-projectid="'+projectId+'" data-email="'+this.email+'">거절</button>';
		});
		Template.render(usercard_list_template_html, response.waitings, ".people_list.wait ul");
		
	} });
	
	
	
	server.getProjectParticipatingUsers({ 'projectId': projectId }, { async: true, callback: function (response) {
		if( response.success === "0") return;
		console.log(response.participatings);
		
		
		var usercard_list_template_html = $("#usercard_list_template").html();
		$(response.participatings).each(function() {
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
		Template.render(usercard_list_template_html, response.participatings, ".people_list.member ul");
		
	} });
	
}




function getProjectparticipatingUsers(target, projectId) {
	
	server.getProjectParticipatingUsers({ 'projectId': projectId }, { async: true, callback: function (response) {
		console.log(response.participatings);

		var index = 1;
		$(response.participatings).each(function() {
			var addLiString = "<li class='user userpic50'><img src='/User/Pic/" + this.email + "?width=50&height=50' /></li>";			
			$(target + " .joined_member_list").append(addLiString);
			index++;
			if(index > 4) return false;
		});
		if( index > 4) {
			$(target + " .joined_member_list").append("<li class='see_more_member'>···</li>");

			$(target + " .member_list_total ul").empty();		
			var userminimal_list_template = $("#userminimal_list_template").html();
			Template.render(userminimal_list_template, response.participatings, target + " .member_list_total ul");
		}
		
		if($("#project_detail .joined_member .number").length > 0) {
			$("#project_detail .joined_member .number").text(response.participatings.length);
			$("#project_detail .progress .progress_value").css("width",(parseInt(response.participatings.length)/parseInt($("#project_detail .admitted_member .number").text())*100) + "%");
		}

		
		

		
		
	} });
	
}


var prjList = (function () {
	var configMap = {
		itemTemplate: String()
		  + '<li>'
			+ '<div class="creator_info"><a href="/member/profile.jsp">'
			  + '<img src="{{creator_info_img_url}}" />'
			  + '<div class="username">{{creator_info_name}}</div>'
			+ '</a></div>'
			+ '<div class="project_info">'
			  + '<div class="info_title">'
				+ '<h3><a href="/project/detail.jsp">{{project_info_title}}</a></h3>'
				+ '<div class="dday">{{project_info_dday}}</div>'
			  + '</div>'
			  + '<div class="info_image">'
				+ '<img src="{{project_info_image}}" />'
			  + '</div>'
			  + '<div class="info_data">'
				+ '<div class="joined member">참여인원<span class="number">{{project_info_joined_member_number}}</span></div>'
				+ '<div class="admitted member">모집인원<span class="number">{{project_info_admitted_member_number}}</span></div>'
				+ '<div class="progress">'
				  + '<div class="progress_value"></div>'
				+ '</div>'
				+ '<ul class="member_list"></ul>'
				+ '<div class="member_list_total"></div>'
				+ '<div class="project_type">{{project_info_project_type}}</div>'
				+ '<dl>'
				  + '<dt class="first_meet">첫 만남</dt>'
				  + '<dd class="first_meet">{{project_info_project_first_meet}}</dd>'
				  + '<dt class="meet_time">시간</dt>'
				  + '<dd class="meet_time">{{project_info_project_meet_time}}</dd>'
				  + '<dt class="meet_place">장소</dt>'
				  + '<dd class="meet_place">{{project_info_meet_place}}</dd>'
				+ '</dl>'
				+ '<button type="button">참여하기</button>'
			  + '</div>'
			  + '<div class="clear_blank"></div>'
			  + '<div class="project_memo">'
				+ '<p class="font-size-20">{{project_info_project_memo_title}}</p>'
				+ '<pre>{{project_info_project_memo_content}}</pre>'
				+ '<div class="see_comment">'
				  + '<img src="/imgsample/comment.png" />'
				+ '</div>'
			  + '</div>'
			+ '</div>'
			+ '<div class="see_more">자세히 보기</div>'
			+ '<div class="comments">'
			  + '<div class="comments_title">댓글</div>'
			  + '<div class="comments_contents nawa_scrollbars"><ul></ul></div>'
			  + '<input type="text" name="comment_write" class="write_comment" placeholder="댓글은 여기에 입력하세요." />'
			+ '</div>'
		  + '</li>',
		member_list_total: String()
			+ '<div><div class="title_area">'
			  + '<div class="see_more_member title_icon">···</div>'
			  + '<div class="title_text">참여 인원</div>'
			+ '</div>'
			+ '<ul></ul></div>'
	},
	statusMap = {
		currentPage: 0,
		isLastPage: false
	},
	$container,
	getProjectListBind,
	toggle_member_detail, bind_comment,
	initModule;

	getProjectListBind = function () {
		if (statusMap.isLastPage)
			return false;

		statusMap.currentPage++;
		server.getProjectList(
			{
				page: statusMap.currentPage
			}, {
				async: true,
				callback: function (response) {
					if (response.success == '1') {
						if (response.projects.length > 0) {
							for (var i = 0; i < response.projects.length; i++) {
								var project = response.projects[i];
								var item = $(configMap.itemTemplate);

								item.attr('data-projectid', project.id);

								item.find('.creator_info .username').text(project.leader_name);
								item.find('.creator_info a').attr('href', '/member/profile.jsp?email=' + encodeURI(project.leader_email)); // profile detail url
								item.find('.creator_info img').attr({ 'src': '/imgsample/profile_yjp.png' }); // Profile Img

								item.find('.info_title > h3 > a').text(project.title);
								item.find('.info_title > h3 > a').attr('href', '/project/detail.jsp'); // project detail url
								item.find('.info_title > .dday').text("D" + project.recruit_due_date_relative);
								item.find('.info_image > img').attr({ 'src': '/imgsample/project_image.png' }); // Project Img

								item.find('.info_data > .joined > .number').text(project.participant_actual_count);
								item.find('.info_data > .admitted > .number').text(project.max_user_count);
								item.find('.progress_value').css({ 'width': ((100 / project.max_user_count * project.participant_actual_count) + '%') });

								if (project.participant_actual_count > 0) {
									var result = server.getProjectParticipatingUsers({ projectId: project.id }, { async: false });
									if (result.success == "1") {
										var count = result.participatings.length;
										var li_participant = '';
										for (var j = 0; j < result.participatings.length; j++) {
											li_participant += '<li>'
												  + '<a href="' + '/member/profile.jsp?email=' + encodeURI(result.participatings[j].email) + '"><img src="' + '/imgsample/profile_no.png' + '"/></a>'
												+ '</li>';
											if (j >= 3) {
												break;
											}
										}

										if (count > 4) {
											li_participant += '<li class="see_more_member">···</li>';
										}
										item.find('ul.member_list').append(li_participant);
									}
								}

								item.find('.project_type').text((project.long_project == 1) ? '장기 프로젝트' : '단기 프로젝트');
								item.find('dd.first_meet').text(project.meeting_time); // 일
								item.find('dd.meet_time').text(project.meeting_time); // 시간
								item.find('dd.meet_place').text(project.place);

								item.find('.project_memo > p').text(project.title);
								item.find('.project_memo > pre').text(project.description);

								// Event 처리
								item.find('.see_more').click(function () {
									$(".comments").hide();
									$memo = $(this).parent().find(".project_info .project_memo");
									if ($memo.is(':visible')) {
										$(this).text("자세히 보기");
										$memo.hide();
									} else {
										$(".see_more").text("자세히 보기");
										$(".project_info .project_memo").hide();

										$(this).text("접어두기");
										$memo.show();
										//$(window).scrollTop($memo.parent().offset().top);
									}
								});
								item.find('.see_more_member').click(function () {
									if ($(this).parents(".info_data").find(".member_list_total").children().length <= 0) {
										var $member_list_total = $(configMap.member_list_total);
										var child_participant = '';

										var result = server.getProjectParticipatingUsers({ projectId: project.id }, { async: false });
										if (result.success == "1") {
											for (var i in result.participatings) {
												child_participant += '<li>'
													+ '<a href="' + '/project/detail.jsp?email' + encodeURI(result.participatings[i].email) + '"><img src="' + '/imgsample/profile_no.png' + '" /></a>'
													+ '<div class="user_name">' + result.participatings[i].name + '</div>'
												+ '</li>';
											}
										}
										$member_list_total.find('ul').append(child_participant);

										$(this).parents(".info_data").find(".member_list_total").append($member_list_total);
										$(this).parents(".info_data").find('.member_list_total .title_icon').click(function () {
											toggle_member_detail(this);
										});
									}

									toggle_member_detail(this);
								});
								item.find('.see_comment img').click(function () {
									var $memo = $(this).parents("li").find(".project_info .project_memo");
									if (!$memo.is(':visible')) return;

									var $comment_area = $(this).parents("li").find(".comments");
									if ($comment_area.is(':visible')) {
										$comment_area.animate({ opacity: 'toggle', display: 'none' });
									} else {
										var project_id = $(this).parents("li").attr('data-projectid');
										$('li[data-projectid="' + project_id + '"]').find('.comments_contents ul').children().remove();
										server.getComment(
											{
												projectId: project_id
											}, {
												async: true,
												callback: function (result) {
													if (result.success == "1") {
														bind_comment(result);
													}
												}
											});
										$comment_area.animate({ opacity: 'toggle', display: 'block' });
									}
								});

								item.find('.write_comment').focus(function () {
									if (!isLogin()) {
										showLoginPopup();
									}
								}).keydown(function (event) {
									if (event.which == 13) {
										event.preventDefault();

										var msg = $(this).val();
										var projectId = $(this).parent().parent().attr('data-projectid');

										var response = server.setComment({ projectId: projectId, content: msg }, { async: false });
										if (response.success == '1') {
											var $li = $("li[data-projectid='" + projectId + "']");
											$li.find('.comments_contents ul').children().remove();
											server.getComment(
												{
													projectId: projectId
												}, {
													async: true,
													callback: function (result) {
														if (result.success == "1") {
															bind_comment(result);
														}
													}
												});

										} else {
											console.log(response.msg);
										}
										$(this).val("");
									}
								});

								$container.append(item);
							}
						} else {
							statusMap.isLastPage = true;
						}
					} else {
						console.log(response.msg);
					}
				}
			});
	};

	toggle_member_detail = function (obj) {
		var $total_member = $(obj).parents(".info_data").find(".member_list_total");
		if ($total_member.is(':visible')) {
			$total_member.animate({ opacity: 'toggle', display: 'block' });
		} else {
			$total_member.animate({ opacity: 'toggle', display: 'none' });
		}
	};

	bind_comment = function (result) {
		var project_id = '';
		for (var _i in result.comments) {
			var comment = result.comments[_i];
			var $comment = null;
			var depth = comment.depth.split('/').length - 1; //무조껀 2 이기 때문에 -1함
			var depthSeq = comment.depth.split('/')[depth];
			var parentDepthSeq = comment.depth.split('/')[depth - 1];
			project_id = comment.project_id;

			var _html = '<li data-depthSeq="' + depthSeq + '">'
						+ '<img src="' + '/imgsample/profile_no.png' + '" />'
						+ '<div class="comments_writer">' + comment.name + '</div>' //comment.user_email
						+ '<div class="comments_time">' + comment.regdate + '</div>'
						+ '<div class="comments_content">' + comment.content + '</div>'
						//+ '<div class="comments_action"><img src="/img/icon_reply_edit.png"><img src="/img/icon_reply_delete.png"></div>'
						+ '<div class="reply_area"><ul></ul></div>'
					+ '</li>';
			$comment = $(_html);

			// comment hover event
			$comment.hover(function () {
				$(this).addClass('on');
			}, function () {
				$(this).removeClass('on');
			});

			if (depth == 1) {
				$('li[data-projectid="' + project_id + '"]').find('.comments_contents > ul').append($comment);
			} else {
				$('li[data-projectid="' + project_id + '"]').find('.comments_contents > ul').find('li[data-depthSeq="' + parentDepthSeq + '"] > .reply_area > ul').append($comment);
			}
		}
	}

	initModule = function ($containerObj) {
		$container = $containerObj;

		getProjectListBind();
	};

	return {
		initModule: initModule
	}
}($));

$(function () {
	prjList.initModule($("#projectList"));
});
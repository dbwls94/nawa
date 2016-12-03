// hidEmail
var profile = (function () {
	var
		configMap = {
			profile_user_template: String()
				+ '<div id="profile_userinfo">'
					+ '<div class="user_pic">'
						+ '<img src="/imgsample/profile_yjp.png" />'
					+ '</div>'
					+ '<div class="user_name">'
						+ '<span class="user_name_text">{user_name}</span>'
						+ '<span class="edit_button"><a href="{edit_jsp_url}">Edit</a></span>'
					+ '</div>'
					+ '<div class="user_email">{user_email}</div>'
					+ '<div class="action_area">'
						+ '<button type="button" class="enable">' + '팔로우' + '</button>'
					+ '</div>'
				+ '</div>',
			profile_activity_template: String()
				+ '<div id="user_activity">'
					+ '<ul class="tab_title_list">'
						+ '<li id="liTabl01"><a href="profile.jsp"><div class="tab_title">' + '프로젝트' + '</div><div class="number">6</div></a></li>'
						+ '<li id="liTabl02"><a href="profile_following.jsp"><div class="tab_title">' + '팔로잉' + '</div><div class="number">28</div></a></li>'
						+ '<li id="liTabl03"><a href="profile_follower.jsp"><div class="tab_title">' + '팔로워' + '</div><div class="number">15</div></a></li>'
					+ '</ul>'
				+ '</div>',
			profile_result_list_template: String()
				+ '<div class="result_list">'
					+ '<div class="result_title">{result_title}</div>'
					+ '<ul>'
					+ '</ul>'
				+ '</div>',
			profile_result_item_template: String()
				+ '<li>'
					+ '<div class="result_list_title">웹서비스 제작할 디자이너, 개발자 구합니다...</div>'
					+ '<div class="img_area"><img src="/imgsample/project_image.png" /></div>'
					+ '<div class="result_info">'
						+ '<div class="nums_of_member">'
							+ '<span class="joined member number">3</span>'
							+ '/'
							+ '<span class="admitted member number">10</span>'
						+ '</div>'
						+ '<div class="dday">D-7</div>'
						+ '<div class="progress">'
							+ '<div class="progress_value"></div>'
						+ '</div>'
						+ '<div class="project_info">'
							+ '<div class="project_date">2015년 1월 20일</div>'
							+ '/'
							+ '<div class="project_place">홍대 팀플레이스</div>'
						+ '</div>'
						+ '<div class="action_area">'
							+ '<button type="button" class="enable">참여하기</button>'
						+ '</div>'
					+ '</div>'
				+ '</li>',
			profile_people_list_template: String()
				+ '<div class="people_list">'
					+ '<ul>'
					+ '</ul>'
				+ '</div>',
			profile_people_item_template: String()
				+ '<li>'
					+ '<div class="user_pic">'
						+ '<img src="/imgsample/profile_yjp.png" />'
					+ '</div>'
					+ '<div class="user_name">'
						+ '박유진'
					+ '</div>'
					+ '<div class="action_area">'
						+ '<button type="button" class="enable add_follow">ㅇ</button>'
					+ '</div>'
				+ '</li>'
		},
		statusMap = {
			email: '',
			type: 'PROJECT' // FOLOWWING, FOLLOWER
		},
		$container,
		getProfileBind,
		initModule;

	getProfileBind = function () {
		$container.html("");

		var $user_profile = $(configMap.profile_user_template);

		$user_profile.find('.user_pic > img').attr('src', '/imgsample/profile_yjp.png');
		$user_profile.find('.user_name_text').text('박유진');
		$user_profile.find('.edit_button > a').attr('href', '/member/edit_profile.jsp');
		$user_profile.find('.user_email').text('wychoi0709@naver.com');
		//$user_profile.find('.action_area > button')

		$container.append($user_profile);

		var $profile_activity = $(configMap.profile_activity_template);

		$profile_activity.find('#liTabl01 .number').text('1');
		$profile_activity.find('#liTabl01 a').attr('href', 'profile.jsp?email=' + encodeURI(statusMap.email));
		$profile_activity.find('#liTabl02 .number').text('2');
		$profile_activity.find('#liTabl02 a').attr('href', 'profile_following.jsp?email=' + encodeURI(statusMap.email));
		$profile_activity.find('#liTabl03 .number').text('3');
		$profile_activity.find('#liTabl03 a').attr('href', 'profile_follower.jsp?email=' + encodeURI(statusMap.email));

		switch (statusMap.type) {
			case 'PROJECT':
				$profile_activity.find('#liTabl01').addClass('on');
				break;
			case 'FOLOWWING':
				$profile_activity.find('#liTabl02').addClass('on');
				break;
			case 'FOLLOWER':
				$profile_activity.find('#liTabl03').addClass('on');
				break;
		}

		$container.append($profile_activity);

		if (statusMap.type == 'PROJECT') {
			// getProjectParticipatingProjects
			server.getProjectParticipatingProjects({
				email: statusMap.email
			}, {
				async: true,
				callback: function (response) {
					if (response.success == '1') {
						var $profile_activity = $('#user_activity');
						var $profile_join_result_list = $(configMap.profile_result_list_template);
						$profile_join_result_list.find('.result_title').text('참여한 프로젝트');

						if (response.participatings.length > 0) {
							for (var i in response.participatings) {
								var project = response.participatings[i];
								var $item_join = $(configMap.profile_result_item_template);
								$item_join.find('.result_list_title').text(project.title);
								$item_join.find('.img_area > img').attr('src', '/imgsample/project_image.png');
								$item_join.find('.joined .member .number').text(project.participant_actual_count);
								$item_join.find('.admitted .member .number').text(project.max_user_count);
								$item_join.find('.dday').text("D" + project.recruit_due_date_relative);
								$item_join.find('.progress_value').css({ 'width': ((100 / project.max_user_count * project.participant_actual_count) + '%') });
								$item_join.find('.project_date').text(project.meeting_date);
								$item_join.find('.project_place').text(project.place);
								//$item_join.find('.action_area') // => button 영역

								$profile_join_result_list.find('ul').append($item_join);
							}
						}
						$profile_activity.append($profile_join_result_list);
					}
				}
			});

			// getProjectLeadingProjects
			server.getProjectLeadingProjects({
				email: statusMap.email
			}, {
				async: true,
				callback: function (response) {
					if (response.success == '1') {
						var $profile_activity = $('#user_activity');
						var $profile_join_result_list = $(configMap.profile_result_list_template);
						$profile_join_result_list.find('.result_title').text('추진한 프로젝트');

						if (response["leading-projects"].length > 0) {
							for (var i in response["leading-projects"]) {
								var project = response["leading-projects"][i];

								var $item_process = $(configMap.profile_result_item_template);
								$item_process.find('.result_list_title').text(project.title);
								$item_process.find('.img_area > img').attr('src', '/imgsample/project_image.png');
								$item_process.find('.joined .member .number').text(project.participant_actual_count);
								$item_process.find('.admitted .member .number').text(project.max_user_count);
								$item_process.find('.dday').text("D " + project.recruit_due_date_relative);
								$item_process.find('.progress_value').css({ 'width': ((100 / project.max_user_count * project.participant_actual_count) + '%') });
								$item_process.find('.project_date').text(project.meeting_date);
								$item_process.find('.project_place').text(project.place);
								//$item_process.find('.action_area') // => button 영역

								$profile_join_result_list.find('ul').append($item_process);
							}
						}
						$profile_activity.append($profile_join_result_list);
					}
				}
			});
		} else if (statusMap.type == 'FOLOWWING') {
			server.getRelationFollowing(
				{
					email: statusMap.email
				}, {
					async: true,
					callback: function (response) {
						if (response.success == '1') {
							var $profile_activity = $('#user_activity');
							var $profile_people_list = $(configMap.profile_people_list_template);

							if (response.followings.length > 0) {
								for (var i in response.followings) {
									/*
									"gender":2,
									"name":"166763340",
									"regdate":"2015-07-18 15:16:00.0",
									"email":"test-845353289@test.com"
									*/
									var $profile_people_item = $(configMap.profile_people_item_template);
									$profile_people_item.find('.user_pic img').attr('src', '/imgsample/profile_yjp.png');
									$profile_people_item.find('.user_name').text(response.followings[i].name);
									//$profile_people_item.find('.action_area').text(response.followings[i].name);

									$profile_people_list.find('ul').append($profile_people_item);
								}

								$profile_activity.append($profile_people_list);
							} else {
								// 없을 때
							}
						}
					}
				});
		} else if (statusMap.type == 'FOLLOWER') {
			server.getRelationFollowed(
				{
					email: statusMap.email
				}, {
					async: true,
					callback: function (response) {
						if (response.success == '1') {
							var $profile_activity = $('#user_activity');
							var $profile_people_list = $(configMap.profile_people_list_template);

							if (response.followeds.length > 0) {
								for (var i in response.followeds) {
									/*
									"gender":2,
									"name":"166763340",
									"regdate":"2015-07-18 15:16:00.0",
									"email":"test-845353289@test.com"
									*/
									var $profile_people_item = $(configMap.profile_people_item_template);
									$profile_people_item.find('.user_pic img').attr('src', '/imgsample/profile_yjp.png');
									$profile_people_item.find('.user_name').text(response.followeds[i].name);
									//$profile_people_item.find('.action_area').text(response.followeds[i].name);

									$profile_people_list.find('ul').append($profile_people_item);
								}

								$profile_activity.append($profile_people_list);
							} else {
								// 없을 때
							}
						}
					}
				});
		}
	};

	initModule = function (param) {
		$container = param.$container;
		statusMap.email = param.email;
		statusMap.type = param.type;

		getProfileBind();
	};

	return {
		initModule: initModule
	}
}($));

$(function () {
	profile.initModule({
		$container: $("#profileContainer"),
		email: $("#hidEmail").val(),
		type: $("#hidProfile_type").val() // PROJECT, FOLOWWING, FOLLOWER
	});
});
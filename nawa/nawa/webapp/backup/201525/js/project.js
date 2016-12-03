function select_menu(menu) {
	$("#main_menu li").removeClass("on");
	$("#main_menu_" + menu + "_project").addClass("on");
}

$(function () {

	$(".see_more_member").click(
			function () {
				$total_member = $(this).parents(".info_data").find(
						".member_list_total");
				if ($total_member.is(':visible')) {
					$total_member.animate({
						opacity: 'toggle',
						display: 'block'
					});
				} else {
					$total_member.animate({
						opacity: 'toggle',
						display: 'none'
					});
				}
			});

	$(".see_more").click(function () {
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
			$(window).scrollTop($memo.parent().offset().top);
		}

	});

	$(".see_comment img").click(function () {
		$memo = $(this).parents("li").find(".project_info .project_memo");
		if (!$memo.is(':visible'))
			return;

		$comment_area = $(this).parents("li").find(".comments");
		if ($comment_area.is(':visible')) {
			$comment_area.animate({
				opacity: 'toggle',
				display: 'block'
			});
		} else {
			$comment_area.animate({
				opacity: 'toggle',
				display: 'none'
			});
		}

	});

	$("#project_create_make").click(function () {

		var project_contents_title = $("#project_contents_title").val(); // 프로젝트
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
		var project_contents_memo = $("#project_contents_memo").val(); // 프로젝트
		// 설명

		var formData = new FormData();
		formData.append("title", project_name);
		if (project_img.length > 0)
			formData.append("project_pics", project_img[0]);
		formData.append("long_project", long_project);
		formData.append("meeting_time", project_time);
		formData.append("place", project_place);
		formData.append("max_user_count", project_member_num);
		formData.append("recruit_due_date", project_limit_date);
		formData.append("description", project_contents_memo);
		formData.append("entry_terms", "1");

		server.setProject(formData, {
			async: true,
			callback: function (response) {
				if (response.success == "1") {
					alert("success");
					location.href = "/project/list.jsp";
				} else {
					alert(response.msg);
				}
			}
		});

	});
}); /* document load */
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="./header.jsp"%>


<div id="nawa_contents_title">
<h2>새 프로젝트 만들기</h2>
</div>

<div id="nawa_contents_wrap">


	<form id="project_create_form">
	
		<div id="project_header_area">
			<input type="text" id="project_name" />
		</div>
		
		<div id="project_info_area">
			<div class="upload">
				<input type="file" id="project_img" accept="image/*" />
			</div>
			<div id="info_area">
				<div class="checkbox_area">
					<input type="checkbox" id="long_project" />
						<label for="long_project">장기프로젝트</label>
				</div>
				<label for="project_date">첫 만남</label>
					<input type="date" id="project_meet_date" />
				<label for="project_place">장소</label>
					<input type="text" id="project_place" />
					
				<label for="project_time">시간</label>
					<input type="time" id="project_time" />
				<label for="project_member_nums">모집인원</label>
					<input type="number" id="project_member_nums" />
				<label for="project_limit_date">모집 마감일</label>
					<input type="text" id="project_limit_date" />
			</div>
		</div>
		<div id="project_contents_area">
			<input type="text" id="project_contents_title" />
			<textarea id="project_contents_memo"></textarea>
		</div>
	
	
	</form>




	<div class="action_area">
		<button type="button" class="disable cancel_button" id="project_action_cancel">취소하기</button>
		<button type="button" id="project_action_submit">만들기</button>
	</div>

	
</div>


<%@ include file="./footer.jsp"%>
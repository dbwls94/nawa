<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="./header.jsp"%>
<script type="text/javascript">
<!--
getProjectForEdit('<%=project_id%>');
//-->
</script>

<div id="nawa_contents_title">
<h2>프로젝트 관리</h2>
</div>

<div id="nawa_contents_wrap">
	<div  class="project_edit">

		<ul class="tab_title_list">
			<li class="on"><a href="<%=urlPath%>/project_edit.jsp?project_id=<%=project_id%>"><div class="tab_title">프로젝트 수정</a></li>
			<li><a href="<%=urlPath%>/project_member.jsp?project_id=<%=project_id%>"><div class="tab_title">멤버관리</div></a></li>
		</ul>
		
		
		<form id="project_create_form">
			<input type="hidden" id="project_id" value="<%=project_id%>" >
		
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
						<input type="text" id="project_time" />
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
	</div>
		
	
		<div class="action_area">
			<button type="button" class="disable cancel_button" id="project_action_cancel">취소하기</button>
			<button type="button" id="project_action_submit">수정완료</button>
		</div>

</div>


<%@ include file="./footer.jsp"%>
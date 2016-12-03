<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="./header.jsp"%>
<script type="text/javascript">
<!--
getProjectMemberList('<%=project_id%>');
//-->
</script>


<div id="nawa_contents_title">
<h2>프로젝트 관리</h2>
</div>

<div id="nawa_contents_wrap">
	<div  class="project_edit">
	
		<ul class="tab_title_list">
			<li><a href="<%=urlPath%>/project_edit.jsp?project_id=<%=project_id%>"><div class="tab_title">프로젝트 수정</a></li>
			<li class="on"><a href="<%=urlPath%>/project_member.jsp?project_id=<%=project_id%>"><div class="tab_title">멤버관리</div></a></li>
		</ul>
		
	
		<div class="people_list wait">
			<div class="people_title">
				승인 대기중
			</div>
			<ul></ul>
		</div>
		
		
		<div class="people_list member">
			<div class="people_title">
				참여 멤버
			</div>
			<ul></ul>
		</div>
	
	</div>
</div>


<%@ include file="./footer.jsp"%>
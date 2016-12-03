<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="./header.jsp"%>
<%
	String searchValue = request.getParameter("v");
%>
<script type="text/javascript">
<!--
$(function() {
	getSearchResultUser('<%=searchValue%>');
	getSearchResultAllProject('<%=searchValue%>');
	getSearchResultMyProject('<%=searchValue%>');
});
//-->
</script>

<div id="nawa_contents_title">
<h2></h2>
</div>

<div id="nawa_contents_wrap" class="search_result">


		<div class="search_result_title">
			<span class="search_value">'<span class="value"><%=searchValue%></span>'</span>
			(으)로 검색한 결과 
			사람 <span class="number user">0</span>명,
			프로젝트 <span class="number project">0</span>개의
			결과가 있습니다.
		</div>



		<div class="people_list">
			<div class="result_title">
				크리에이터
			</div>
			<ul></ul>
		</div>
		
		
		<div class="result_list all_project">
			<div class="result_title">
				프로젝트
			</div>
			<ul></ul>
		</div>

		<div class="result_list my_project">
			<div class="result_title">
				추진한 프로젝트
			</div>
			<ul></ul>
		</div>


</div>


<%@ include file="./footer.jsp"%>
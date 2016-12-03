<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>

<%@ include file="/admin/inc/auth.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jquery -->
<script src="/admin/js/lib/jquery-2.1.4.min.js"></script>

<!-- bootstrap -->
<link href="/admin/css/lib/bootstrap.min.css" rel="stylesheet">
<script src="/admin/js/lib/bootstrap.min.js"></script>

<!-- bootbox -->
<script src="/admin/js/lib/bootbox.min.js"></script>

<!-- js cols -->
<script src="/admin/js/lib/js_cols.min.js"></script>

<!-- code mirror -->
<script src="/admin/js/lib/code-mirror/codemirror.js"></script>
<link href="/admin/css/lib/code-mirror/codemirror.css" rel="stylesheet">

<script src="/admin/js/lib/code-mirror/sql.js"></script>
<script src="/admin/js/lib/code-mirror/javascript.js"></script>

<script src="/admin/js/lib/code-mirror/show-hint.js"></script>
<link href="/admin/css/lib/code-mirror/show-hint.css" rel="stylesheet">
<script src="/admin/js/lib/code-mirror/sql-hint.js"></script>

<link href="/admin/css/lib/code-mirror/base16-dark.css" rel="stylesheet">

<!-- sha512 -->
<script src="/admin/js/lib/sha512.js"></script>

<script src="/admin/js/lib/ajax-call.js"></script>
<script src="/admin/js/lib/string.format.js"></script>

<!-- bootstrap datepicker -->
<!-- http://www.malot.fr/bootstrap-datetimepicker/demo.php -->
<!--  
<link href="/admin/css/lib/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="/admin/js/lib/bootstrap-datetimepicker.min.js"></script>
 -->

<!-- common css -->
<link href="/admin/css/common.css" rel="stylesheet">

</head>

<body class="bg-blue-black">
	<jsp:include page="inc/header.jsp" flush="false"/>
	
	<div class="container">
		<div class="row" style="margin-bottom: 20px;">
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.view.showMockupDiv('signup');">signup mockup</button>
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.view.showMockupDiv('login');">login mockup</button>
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.view.showMockupDiv('create-project');">create project mockup</button>
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.view.showMockupDiv('edit-project');">edit project mockup</button>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.view.showMockupDiv('comment');">comment mockup</button>
			</div>
			<div class="col-xs-9"></div>
		</div>
		<hr />
		
		<div id="div-signup-mockup" class="mockup-div" style="display: none">
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>email</label>
				</div>
				<div class="col-xs-9">
					<input id="input-email" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-12">
					<button type="button" class="btn btn-sm btn-info pull-right" onclick="controller.checkDuplication();">check duplication</button>
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>password</label>
				</div>
				<div class="col-xs-9">
					<input id="input-password" type="password" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px">
				<div class="col-xs-3">
					<label>name</label>
				</div>
				<div class="col-xs-9">
					<input id="input-name" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px">
				<div class="col-xs-3">
					<label>gender</label>
				</div>
				<div class="col-xs-9">
					<select id="select-gender" class="form-control">
						<option value="1">남</option>
						<option value="2">여</option>
					</select>
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px">
				<div class="col-xs-3">
					<label>pic (optional)</label>
				</div>
				<div class="col-xs-9">
					<input id="input-user-pic" type="file" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>facebook-access-token (optional)</label>
				</div>
				<div class="col-xs-9">
					<input id="input-facebook-access-token" type="text" class="form-control" />
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12">
					<button type="button" class="btn btn-primary pull-right" onclick="controller.signup();">signup</button>
				</div>
			</div>
		</div>
		
		<div id="div-login-mockup" class="mockup-div" style="display: none">
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>cookie</label>
				</div>
				<div class="col-xs-9">
					<input id="input-cookie" type="text" class="form-control" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-5">
					<input id="input-email" type="text" class="form-control" placeholder="email" />
				</div>
				<div class="col-xs-5">
					<input id="input-password" type="password" class="form-control"  placeholder="password" />
				</div>
				<div class="col-xs-2">
					<button type="button" class="btn btn-primary form-control" onclick="controller.login();">login</button>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-10"></div>
				<div class="col-xs-2">
					<button type="button" class="btn btn-primary form-control" onclick="controller.logout();">logout</button>
				</div>
			</div>
			<hr />
		</div>
		
		<div id="div-create-project-mockup" class="mockup-div" style="display: none">
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>title</label>
				</div>
				<div class="col-xs-9">
					<input id="input-title" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>desc-title</label>
				</div>
				<div class="col-xs-9">
					<input id="input-desc-title" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>desc</label>
				</div>
				<div class="col-xs-9">
					<textarea id="textarea-desc" class="form-control" rows="4"></textarea>
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>place</label>
				</div>
				<div class="col-xs-9">
					<input id="input-place" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>meeting date</label>
				</div>
				<div class="col-xs-9">
					<input id="input-meeting-date" type="text" class="form-datetime form-control" placeholder="yyyy-MM-dd" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>meeting time</label>
				</div>
				<div class="col-xs-9">
					<input id="input-meeting-time" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>max user count</label>
				</div>
				<div class="col-xs-9">
					<input id="input-max-user-count" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>recruit due date</label>
				</div>
				<div class="col-xs-9">
					<input id="input-recruit-due-date" type="text" class="form-datetime form-control" placeholder="yyyy-MM-dd" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>long project</label>
				</div>
				<div class="col-xs-9">
					<select id="select-long-project" class="form-control">
						<option value="1">장기 프로젝트</option>
						<option value="2">단기 프로젝트</option>
					</select>
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>project pic</label>
				</div>
				<div class="col-xs-9">
					<input id="file-project-pic" type="file" class="form-control" />
				</div>
			</div>
			<hr />
			
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-12">
					<button type="button" class="btn btn-primary pull-right" onclick="controller.postProject();">save</button>
				</div>
			</div>
		</div>
		
		<div id="div-edit-project-mockup" class="mockup-div" style="display: none">
			<input type="hidden" id="hidden-project-id" value="" />
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>title</label>
				</div>
				<div class="col-xs-9">
					<input id="input-title" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>desc-title</label>
				</div>
				<div class="col-xs-9">
					<input id="input-desc-title" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>desc</label>
				</div>
				<div class="col-xs-9">
					<textarea id="textarea-desc" class="form-control" rows="4"></textarea>
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>place</label>
				</div>
				<div class="col-xs-9">
					<input id="input-place" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>meeting date</label>
				</div>
				<div class="col-xs-9">
					<input id="input-meeting-date" type="text" class="form-datetime form-control" placeholder="yyyy-MM-dd" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>meeting time</label>
				</div>
				<div class="col-xs-9">
					<input id="input-meeting-time" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>max user count</label>
				</div>
				<div class="col-xs-9">
					<input id="input-max-user-count" type="text" class="form-control" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>recruit due date</label>
				</div>
				<div class="col-xs-9">
					<input id="input-recruit-due-date" type="text" class="form-datetime form-control" placeholder="yyyy-MM-dd" />
				</div>
			</div>
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-3">
					<label>long project</label>
				</div>
				<div class="col-xs-9">
					<select id="select-long-project" class="form-control">
						<option value="1">장기 프로젝트</option>
						<option value="2">단기 프로젝트</option>
					</select>
				</div>
			</div>
			<hr />
			<div class="row" style="margin-bottom: 3px;">
				<div class="col-xs-12">
					<button type="button" class="btn btn-primary pull-right" onclick="controller.putProject();">save</button>
				</div>
			</div>
		</div>
		
		<div id="div-comment-mockup" class="mockup-div" style="display: none">
			<input type="hidden" id="hidden-project-id" />
			<div id="div-existing-comment">
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12">
					<button type="button" class="btn btn-primary pull-right" onclick="controller.view.showPostCommentDialog();">add new comment</button>
				</div>
			</div>
		</div>
	</div>
	
	<script src="/admin/js/mockup.js"></script>
</body>
</html>
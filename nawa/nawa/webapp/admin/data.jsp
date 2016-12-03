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

<script src="/admin/js/lib/ajax-call.js"></script>

<!-- common css -->
<link href="/admin/css/common.css" rel="stylesheet">

</head>

<body class="bg-blue-black">
	<jsp:include page="inc/header.jsp" flush="false"/>
	
	<div class="container">
		<div class="row">
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.createTables();">create tables</button>
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.dropTables();">drop tables</button>
			</div>
			<div class="col-xs-6"></div>
		</div>
		<hr />
		
		<div class="row">
			<div>
				<label>insert random</label>
			</div>
			<div class="col-xs-3">
				<input type="text" id="text-input-random" class="form-control" />
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.insertRandomUsers();">users</button>
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.insertRandomProjects();">projects</button>
			</div>
			<div class="col-xs-3">
				<button type="button" class="btn btn-default form-control" onclick="controller.insertRandomComments();">comments</button>
			</div>
		</div>
		<hr />
		
		<div class="row">
			<div class="col-xs-4">
				<button type="button" class="btn btn-default form-control" onclick="controller.loadUserInfo();">load user info</button>
			</div>
			<div class="col-xs-4">
				<button type="button" class="btn btn-default form-control" onclick="controller.loadProjectInfo();">load project info</button>
			</div>
			<div class="col-xs-4">
				<button type="button" class="btn btn-default form-control" onclick="controller.loadComments();">load comments</button>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12">
				<textarea class="form-control" id="textarea-load-result"></textarea>
			</div>
		</div>
		<hr />
		
	</div>
	
	<script src="/admin/js/data.js"></script>
</body>
</html>
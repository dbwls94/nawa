Model = function(){
}; //INIT
Model.prototype = {
}; //Model

View = function(){
}; //INIT
View.prototype = {
	showMockupDiv: function(mockupName){
		$('.mockup-div').hide(300);
		switch(mockupName){
		case 'signup':
			$('#div-signup-mockup').show(300);
			break;
		case 'login':
			var root = $('#div-login-mockup');
			root.find('#input-cookie').val(document.cookie);
			root.show(300);
			break;
		case 'create-project':
			$('#div-create-project-mockup').show(300);
			break;
		case 'edit-project':
			ajaxCall('/Admin/ProjectInfo', 'GET', {}, function(resp){
				if(resp.success != '1'){
					bootbox.alert(resp.msg);
					return;
				} //if
				
				if(resp.projectInfos.length == 0){
					bootbox.alert("no project exists to edit");
					return;
				} //if
				
				var buttonsDOM = '';
				for(var i=0; i<resp.projectInfos.length; i++){
					buttonsDOM += '<div class="row">';
					buttonsDOM += '<div class="col-xs-12">';
					buttonsDOM += '<button type="button" class="btn btn-info btn-sm" style="margin-bottom: 2px;" ' + 
										'onclick="controller.view.viewEditProjectMockup(\'{id}\');">{title} ({id})</button>'.format(resp.projectInfos[i]);
					buttonsDOM += '</div>';
					buttonsDOM += '</div>';
				} //for i
				bootbox.dialog({
					title: 'select project to edit',
					message: buttonsDOM,
				});
			});
			break;
		case 'comment':
			ajaxCall('/Admin/ProjectInfo', 'GET', {}, function(resp){
				console.log(resp); //DEBUG
				if(resp.success != '1'){
					bootbox.alert(resp.msg);
					return;
				} //if
				
				if(resp.projectInfos.length == 0){
					bootbox.alert("no project exists to leave comment");
					return;
				} //if
				
				var buttonsDOM = '';
				for(var i=0; i<resp.projectInfos.length; i++){
					buttonsDOM += '<div class="row">';
					buttonsDOM += '<div class="col-xs-12">';
					buttonsDOM += '<button type="button" class="btn btn-info btn-sm" style="margin-bottom: 2px;" ' + 
										'onclick="controller.view.viewCommentMockup(\'{id}\');">{title} ({id})</button>'.format(resp.projectInfos[i]);
					buttonsDOM += '</div>';
					buttonsDOM += '</div>';
				} //for i
				bootbox.dialog({
					title: 'select project to leave comment',
					message: buttonsDOM,
				});
			});
			break;
		default:
			bootbox.alert("unknown mockup : " + mockupName);
			return;
		} //switch
	}, //showSignupMockup
	viewEditProjectMockup: function(projectId){
		bootbox.hideAll();
		ajaxCall('/Project/' + projectId, 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(resp.msg);
				return;
			} //if
					
			var project = resp.project;
			var root = $('#div-edit-project-mockup');
			root.find('#hidden-project-id').val(project.id);
			root.find('#input-title').val(project.title);
			root.find('#input-desc-title').val(project.description);
			root.find('#textarea-desc').val(project.description);
			root.find('#input-place').val(project.place);
			root.find('#input-meeting-date').val(project.meeting_date);
			root.find('#input-meeting-time').val(project.meeting_time);
			root.find('#input-max-user-count').val(project.max_user_count);
			root.find('#input-recruit-due-date').val(project.recruit_due_date);
			root.find('#select-long-project').val(project.long_project);
			root.show(300);
		});
	}, //viewEditProjectMockup
	viewCommentMockup: function(projectId){
		bootbox.hideAll();
		ajaxCall('/Comment/' + projectId, 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(resp.msg);
				return;
			} //if
			
			var root = $('#div-comment-mockup');
			root.find('#hidden-project-id').val(projectId);
			if(resp.comments != null || resp.comments.length > 0){
				var existingCommentsDOM = '<table class="table table-striped table-bordered">';
				existingCommentsDOM += '<thead>'
				existingCommentsDOM += '<th width="10%">seq</th>';
				existingCommentsDOM += '<th width="10%">depth</th>';
				existingCommentsDOM += '<th width="20%">user name/email</th>';
				existingCommentsDOM += '<th width="20%">content</th>';
				existingCommentsDOM += '<th width="20%">regdate</th>';
				existingCommentsDOM += '<th width="20%">operation</th>';
				existingCommentsDOM += '</thead>'
				existingCommentsDOM += '</tbody>'
				for(var i=0; i<resp.comments.length; i++){
					existingCommentsDOM += '<tr>';
					existingCommentsDOM += '<td>{seq}</td>'.format(resp.comments[i]);
					existingCommentsDOM += '<td>{depth}</td>'.format(resp.comments[i]);
					existingCommentsDOM += '<td>{name}({email})</td>'.format(resp.comments[i]);
					existingCommentsDOM += '<td>{content}</td>'.format(resp.comments[i]);
					existingCommentsDOM += '<td>{regdate}</td>'.format(resp.comments[i]);
					existingCommentsDOM += '<td>';
					existingCommentsDOM += '<button type="button" class="btn btn-info btn-sm" onclick="controller.view.showPutCommentDialog({seq});">edit</button>'.format(resp.comments[i]);
					existingCommentsDOM += '<button type="button" class="btn btn-info btn-sm" onclick="controller.removeComment({seq});">remove</button>'.format(resp.comments[i]);
					existingCommentsDOM += '<button type="button" class="btn btn-info btn-sm" onclick="controller.view.showPostCommentDialog({seq});">re-reply</button>'.format(resp.comments[i]);
					existingCommentsDOM += '</td>';
					existingCommentsDOM += '</tr>';
				} //for i
				existingCommentsDOM += '</tbody>'
				existingCommentsDOM += '</table>'
				root.find('#div-existing-comment').empty().append(existingCommentsDOM);
			} //if
			root.show(300);
		});
	}, //viewCommentMockup 
	showPostCommentDialog: function(parentSeq){
		bootbox.prompt('new comment', function(result){
			if(result === null)
				return;
			
			var root = $('#div-comment-mockup');
			var params = {};
			params.projectId = root.find('#hidden-project-id').val();
			params.content = result;
			if(parentSeq != null)
				params.parent_seq = parentSeq;
			ajaxCall('/Comment', 'POST', params, function(resp){
				bootbox.alert(JSON.stringify(resp));
			});
		});
	}, //showPostCommentDialog
	showPutCommentDialog: function(seq){
		bootbox.prompt('edit comment', function(result){
			if(result === null)
				return;
			
			var root = $('#div-comment-mockup');
			var params = {};
			params.seq = seq;
			params.projectId = root.find('#hidden-project-id').val();
			params.content = result;
			ajaxCall('/Comment', 'PUT', params, function(resp){
				bootbox.alert(JSON.stringify(resp));
			});
		});
	} //showPutCommentDialog
}; //view

Controller = function(){
	this.model = new Model();
	this.view = new View();
}; //INIT
Controller.prototype = {
	checkDuplication: function(){
		var email = $('#div-signup-mockup').find('#input-email').val();
		ajaxCall('/User/isEmailExists/' + email, 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(resp.errmsg);
				return;
			} //if
			bootbox.alert('isEmailExists : ' + resp.isEmailExists);
		});
	}, //checkDuplication
	signup: function(){
		var root = $('#div-signup-mockup');
		var formData = new FormData();
		formData.append('email', root.find('#input-email').val());
		formData.append('password', hex_sha512(root.find('#input-password').val()));
		formData.append('name', root.find('#input-name').val());
		formData.append('gender', root.find('#select-gender').val());
		formData.append('facebook_access_token', root.find('#input-facebook-access-token').val());
		var userPic = root.find('#input-user-pic')[0].files;
		if(userPic.length > 0)
			formData.append('userPic', userPic[0]);
		
		$.ajax({
				type:"POST",
				url:"/User",
				contentType:false,
				cache:false,
				data:formData,
				processData:false,
				success:function(data){
					if( (typeof data) == 'object')
						data = JSON.stringify(data);
					bootbox.alert(data);
				},
				error:function(e){
					bootbox.alert('error, ' + e.statusText);
				}
			});
	}, //signup
	login: function(){
		var root = $('#div-login-mockup');
		var params = {};
		params.email = root.find('#input-email').val();
		var password = root.find('#input-password').val();
		params.password = hex_sha512(password);
		
		ajaxCall('/Login', 'get', params, function(resp){
			bootbox.alert(JSON.stringify(resp));
			root.find('#input-cookie').val(document.cookie);
		});
	}, //login
	logout: function(){
		ajaxCall('/Logout', 'post', {}, function(resp){
			bootbox.alert(JSON.stringify(resp));
			$('#div-login-mockup').find('#input-cookie').val(document.cookie);
		});
	}, //logout
	postProject: function(){
		var root = $('#div-create-project-mockup');
		var formData = new FormData();
		formData.append('title', root.find('#input-title').val());
		formData.append('description_title', root.find('#input-desc-title').val());
		formData.append('description', root.find('#textarea-desc').val());
		formData.append('place', root.find('#input-place').val());
		formData.append('meeting_date', root.find('#input-meeting-date').val());
		formData.append('meeting_time', root.find('#input-meeting-time').val());
		formData.append('max_user_count', root.find('#input-max-user-count').val());
		formData.append('recruit_due_date', root.find('#input-recruit-due-date').val());
		formData.append('long_project', root.find('#select-long-project').val());
		var projectPic = root.find('#file-project-pic')[0].files;
		if(projectPic.length != 0)
			formData.append('project_pics', projectPic[0]);
		
		$.ajax({
				type:"POST",
				url:"/Project",
				contentType:false,
				cache:false,
				data:formData,
				processData:false,
				success:function(data){
					if( (typeof data) == 'object')
						data = JSON.stringify(data);
					bootbox.alert(data);
				},
				error:function(e){
					bootbox.alert('error, ' + e.statusText);
				}
			});
	}, //postProject
	putProject: function(){
		var root = $('#div-edit-project-mockup');
		var formData = new FormData();
		formData.append('id', root.find('#hidden-project-id').val());
		formData.append('title', root.find('#input-title').val());
		formData.append('description_title', root.find('#input-desc-title').val());
		formData.append('description', root.find('#textarea-desc').val());
		formData.append('place', root.find('#input-place').val());
		formData.append('meeting_date', root.find('#input-meeting-date').val());
		formData.append('meeting_time', root.find('#input-meeting-time').val());
		formData.append('max_user_count', root.find('#input-max-user-count').val());
		formData.append('recruit_due_date', root.find('#input-recruit-due-date').val());
		formData.append('entry_terms', root.find('#select-entry-terms').val());
		formData.append('long_project', root.find('#select-long-project').val());
		var projectPic = root.find('#file-project-pic')[0].files;
		if(projectPic.length != 0)
			formData.append('project_pics', projectPic[0]);
		
		$.ajax({
				type:"PUT",
				url:"/Project",
				contentType:false,
				cache:false,
				data:formData,
				processData:false,
				success:function(data){
					if( (typeof data) == 'object' )
						data = JSON.stringify(data);
					bootbox.alert(data);
				},
				error:function(e){
					bootbox.alert('error, ' + e.statusText);
				}
			});
	}, //putProject
	removeComment: function(seq){
		var root = $('#div-comment-mockup');
		var params = {};
		params.seq = seq;
		params.projectId = root.find('#hidden-project-id').val();
		ajaxCall('/Comment', 'DELETE', params, function(resp){
			bootbox.alert(JSON.stringify(resp));
		});
	} //removeComment
}; //Controller

$(function(){
	controller = new Controller();
});
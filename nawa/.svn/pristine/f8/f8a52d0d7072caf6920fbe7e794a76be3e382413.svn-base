Model = function(){
	
}; //INIT
Model.prototype = {
		
}; //Model


View = function(){
	this.codeMirror($('#textarea-load-result')[0]);
}; //INIT
View.prototype = {
	codeMirror: function(dom){
		this.resultEditor = CodeMirror.fromTextArea(dom, {
			lineNumbers: true,
			mode: 'javascript'
		});
		this.resultEditor.setSize(null, 700);
		this.resultEditor.setOption('theme', 'base16-dark');
	} //codeMirror
}; //view

Controller = function(){
	this.model = new Model();
	this.view = new View();
}; //INIT
Controller.prototype = {
	createTables: function(){
		ajaxCall('/Admin/CreateTables', 'post', {}, function(resp){
			bootbox.alert(JSON.stringify(resp));
		});
	}, //createTables
	dropTables: function(){
		ajaxCall('/Admin/DropAllTables', 'delete', {}, function(resp){
			bootbox.alert(JSON.stringify(resp));
		});
	}, //dropTables
	insertRandomUsers: function(){
		var count = $("#text-input-random").val();
		ajaxCall('/Admin/InsertRandomUserInfo/' + count, 'post', {}, function(resp){
			bootbox.alert(JSON.stringify(resp));
		});
	}, //insertRandomUsers
	insertRandomProjects: function(){
		var count = $("#text-input-random").val();
		ajaxCall('/Admin/InsertRandomProjectInfo/' + count, 'post', {}, function(resp){
			bootbox.alert(JSON.stringify(resp));
		});
	}, //insertRandomProjects
	insertRandomComments: function(){
		var count = $("#text-input-random").val();
		ajaxCall('/Admin/InsertRandomComment/' + count, 'post', {}, function(resp){
			bootbox.alert(JSON.stringify(resp));
		});
	}, //insertRandomComments
	loadUserInfo: function(){
		ajaxCall('/Admin/UserInfo', 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(JSON.stringify(resp));
				return;
			} //if
			var userInfos = JSON.stringify(resp.userInfos, null, '\t');
			controller.view.resultEditor.setValue(userInfos);
		});
	}, //loadUserInfo
	loadProjectInfo: function(){
		ajaxCall('/Admin/ProjectInfo', 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(JSON.stringify(resp));
				return;
			} //if
			var projectInfos = JSON.stringify(resp.projectInfos, null, '\t');
			controller.view.resultEditor.setValue(projectInfos);
		});
	}, //loadProjectInfo
	loadComments: function(){
		ajaxCall('/Admin/ProjectReply', 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(JSON.stringify(resp));
				return;
			} //if
			var projectReplies = JSON.stringify(resp.projectReplies, null, '\t');
			controller.view.resultEditor.setValue(projectReplies);
		});
	} //loadComments
}; //Controller

controller = new Controller();
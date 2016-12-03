Model = function(){
	this.servlets = null;
}; //INIT
Model.prototype = {
}; //Model

View = function(){
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
	loadApiDoc: function(){
		ajaxCall('/Admin/ApiDoc', 'get', {}, function(resp){
			if(resp.success != '1'){
				bootbox.alert(JSON.stringify(resp.errmsg));
				return;
			} //if
			
			var doc = resp.doc;
			controller.model.servlets = doc;
			var dom = '';
			
			for(var i=0; i<doc.length; i++){
				var servlet = doc[i];
				dom += '<h3>{}</h3>'.format(servlet.name);
				dom += '<table class="table table-striped table-bordered">';
				dom += '<thead><tr>';
				dom += '<th width="7%">method</th>';
				dom += '<th width="12%">url</th>';
				dom += '<th width="15%">desc</th>';
				dom += '<th width="22%">parameter</th>';
				dom += '<th width="6%">returnType</th>';
				dom += '<th width="32%">returns</th>';
				dom += '<th width="6%">try</th>';
				dom += '</tr></thead>';
				dom += '<tbody>';
				
				var methods = servlet.methods;
				for(var ii=0; ii<methods.length; ii++){
					var method = methods[ii];
					dom += '<tr>';
					dom += '<td>{}</td>'.format(method.method);
					dom += '<td>{}</td>'.format(method.url);
					dom += '<td>{}</td>'.format(method.desc);
					var parameters = method.parameter;
					dom += '<td>';
					for(var iii=0; iii<parameters.length; iii++)
						dom += '<div>{}</div>'.format(parameters[iii]);
					dom += '</td>';
					
					dom += '<td>{}</td>'.format(method.returnType);
					var returns = method.returns;
					dom += '<td>';
					for(var iii=0; iii<returns.length; iii++)
						dom += '<div>{}</div>'.format(returns[iii]);
					dom += '</td>';
					dom += '<td style="text-align: center;">';
					dom += '<button type="button" class="btn btn-info btn-sm" onclick="controller.tryApiDialog({}, {});">try</button>'.format(i, ii);
					dom += '</td>';
					dom += '</tr>'; //row
				} //for ii
				dom += '</tbody>';
				dom += '</tr></table>';
			} //for i
			
			$('#div-api-doc').empty().append(dom);
		});
	}, //loadApiDoc
	tryApiDialog: function(servletIndex, methodIndex){
		var method = this.model.servlets[servletIndex].methods[methodIndex];
		
		if(method.parameter != null && method.parameter.length != 0 && method.parameter[0].indexOf('form data') > 0 ){
			bootbox.alert('you cannot try form data api');
			return;
		} //if
		
		var parameterJson = {};
		for(var i=0; i<method.parameter.length; i++){
			var splited = method.parameter[i].split(':');
			var key = splited[0].trim();
			var value = splited[1].trim();
			parameterJson[key] = value;
		} //for i
		parameterJson = JSON.stringify(parameterJson, null, 2);
		
		dialogDom = '';
		dialogDom += '<div class="row">';
		dialogDom += '<div class="col-xs-2">';
		dialogDom += '<label>method</label>';
		dialogDom += '</div>'; //col2
		dialogDom += '<div class="col-xs-10">';
		dialogDom += '<input id="inputTryMethod" type="text" class="form-control" value="{}" />'.format(method.method);
		dialogDom += '</div>'; //col10
		dialogDom += '</div>'; //row
		dialogDom += '<div class="row">';
		dialogDom += '<div class="col-xs-2">';
		dialogDom += '<label>url</label>';
		dialogDom += '</div>'; //col2
		dialogDom += '<div class="col-xs-10">';
		dialogDom += '<input id="inputTryUrl" type="text" class="form-control" value="{}" />'.format(method.url);
		dialogDom += '</div>'; //col10
		dialogDom += '</div>'; //row
		dialogDom += '<div class="row">';
		dialogDom += '<div class="col-xs-2">';
		dialogDom += '<label>parameter</label>';
		dialogDom += '</div>'; //col2
		dialogDom += '<div class="col-xs-10">';
		dialogDom += '<textarea id="textareaTryParams" rows=5 class="form-control">{}</textarea>'.format(parameterJson);
		dialogDom += '</div>'; //col10
		dialogDom += '</div>'; //row
		dialogDom += '<input id="hiddenReturnType" type="hidden" name="returnType" value="{}" />'.format(method.returnType);
		
		bootbox.dialog({
			title: 'try',
			message: dialogDom,
			buttons: {
				success: {
					label: 'try',
					className: 'btn-success',
					callback: function(){
						var method = $('#inputTryMethod').val();
						var url = $('#inputTryUrl').val();
						var params = $('#textareaTryParams').val();
						if(params == null || params == '')
							params = '{}';
						var params = JSON.parse(params);
						var returnType = $('#hiddenReturnType').val();
						controller.tryApi(method, url, params, returnType);
					} //callback
				} //success
			} //buttons
		});
		
		var enterListener = function(e){
			if(e.currentTarget.tagName == 'TEXTAREA' && e.ctrlKey && e.keyCode == 13)
				$('div.modal-footer').find('.btn-success').click();
			if(e.keyCode == 13)
				$('div.modal-footer').find('.btn-success').click();
		} //enterListener
		$('#inputTryMethod').keyup(enterListener);
		$('#inputTryUrl').keyup(enterListener);
	}, //tryApiDialog
	tryApi: function(method, url, params, returnType){
		switch(returnType){
		case 'json': 
			ajaxCall(url, method, params, function(resp){
				var dom = '';
				dom += '<div class="row"><div class="col-xs-12">';
				dom += '<textarea rows=10 class="form-control">{}</textarea>'.format(JSON.stringify(resp, null, 2));
				dom += '</div></div>';
				
				bootbox.dialog({
					title: 'result',
					message: dom,
					buttons: {
						success: {
							label: 'ok',
							className: 'btn-success'
						} //success
					} //buttons
				});
			});
			break;
		case 'image':
			var paramsUrl = '';
			for(var paramKey in params){
				var paramValue = params[paramKey];
				if(paramsUrl.length == 0)
					paramsUrl += '?';
				else
					paramsUrl += '&';
				paramsUrl += '{}={}'.format(paramKey, paramValue);
			} //if
			var dom = '<div><image src="{}" style="width: 100%; height: 100%;" /></div>'.format( (url+paramsUrl) );
			bootbox.dialog({
				title: 'result', 
				message: dom,
				buttons: {
					success: {
						label: 'ok',
						className: 'btn-success'
					} //success
				} //buttons
			});
			break;
		default: 
			bootbox.alert("unknown returnType");
			break;
		} //switch
	} //tryApi
}; //Controller

$(function(){
	controller = new Controller();
	controller.loadApiDoc();
});
function ajaxCall(url, type, data, onSuccess){
	if(type.toLowerCase() === 'put' || type.toLowerCase() === 'delete'){
		var queryString = '';
		for(var paramKey in data){
			var paramValue = data[paramKey];
			if(queryString.length != 0)
				queryString += '&';
			queryString += paramKey + '=' + paramValue;
		} //for key
		url += '?' + queryString;
		data = null;
	} //if
	
	$.ajax({
		url: url,
		type: type,
		dataType: 'json',
		data: data,
		success: onSuccess,
		error: function(e){
			console.error(e);
		}
	});	
} //ajaxCall
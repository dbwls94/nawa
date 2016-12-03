//로그인 ajax 시작
console.log("basic enter!");

$("#login-id").click(function() {
	var email = $("#email-id").val();
	var pwd = $("#pwd-id").val();
	var encryptedPwd=hex_sha512(pwd);
	
	var senddata = {
		"email" : email,
		"password" :encryptedPwd
	};
	console.log("var enter!");
     console.log(encryptedPwd);
     
	$.ajax({
		url : "/Login",
		dataType : "json",
		type : "get",
		data : senddata,
		success : function(data) {

			var success = data.success;
			var msg;

			if (success == "1") {
				location.href = "project_list.jsp";
			} else {
				if (success == "0") { 
					console.log("fail enter!");

					msg = data.msg;
					alert(msg);
				}
			}
			// End success
		},
		// Start error (Ajax통신이 안됐을 때)
		error : function(e) {
			alert("error");
			console.log(e);
			// End error
		}
	});
});
// 로그인 ajax 끝

function Register() {
	location = "sign_up.jsp"
}
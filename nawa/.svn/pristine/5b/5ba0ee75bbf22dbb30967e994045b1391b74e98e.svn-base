/**
 * 
 */

$(document).ready(function () {

	// 가입 시 email 중복체크
	$("#UserEmail").focusout(function () {
		alert("유효한 email 체크 후  email 중복체크");
	});
});

function login() {
	var email = $("#login_id").val();
	var pwd = $("#login_pw").val();

	if (email == "", pwd == "") {
		alert("login error")
		if ($("#login_window_message").hasClass("on"))
			$("#login_window_message").removeClass("on").text("");
		else
			$("#login_window_message").addClass("on").text("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		return;
	}
	;

	var encryptedPwd = hex_sha512(pwd);

	server.setLogin({
		'email': email,
		'password': encryptedPwd
	}, {
		async: true,
		callback: function (response) {
			location.href = "list.jsp";
		}
	});
}// login()

function logout() {
	server.setLogout({
		async: true,
		callback: function (response) {
			location.href = "list.jsp";
		}
	})
}

function signup() {

	console.log("signup()");

	var userPic = $("#signup_profile_img")[0].files;
	console.log(userPic);
	var userName = $("#signup_name").val();
	var userEmail = $("#signup_email").val();
	var userPwd = $("#signup_pw").val();
	var userPwdCheck = $("#signup_pw_check").val();
	var userGender = $("#signup_genger").val();

	var RegexName = /^[가-힣]{2,4}$/; // 이름 유효성 검사 2~4자 사이 영문 이름[a-zA-Z]{5,10}$/i;
	var RegexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	if (!RegexName.test(userName)) {
		alert("한글 이름만");
		$("#signup_name").focus();
		return false;
	}

	if (!RegexEmail.test(userEmail)) {
		alert("이메일 양식틀림");
		$("#signup_email").focus();
		return false;
	}
	if (userPwd != userPwdCheck) {
		alert("비밀번호 확인 틀림")
		$("#signup_pw").focus();
		return false;
	}

	var encryptedPwd = hex_sha512(userPwd);

	var formData = new FormData();
	formData.append("name", userName);
	formData.append("email", userEmail);
	formData.append("password", encryptedPwd);
	formData.append("gender", userGender);
	if (userPic.length > 0)
		formData.append("userPic", userPic[0]);

	server.setSignUp(formData, {
		async: true,
		callback: function (response) {
			alert("setSignUp()"), location.href = "list.jsp",
					window.location.href = "?islogin=true";
		}
	});// setSignUp();
}; // sigun();

function isLogin() {
	var token = getCookie('token');
	if (token) {
		return true;
	}
	return false;
}
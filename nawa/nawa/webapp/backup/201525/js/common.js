/**
 * 
 */

$(function () {

	$(".close_btn").click(function () {
		$(this).parents(".layer").hide();
		$("#mask").hide();
	});
	$(".layer .layer_menu div").click(function () {
		if ($(this).hasClass("on"))
			return;
		$(this).parents(".layer").hide();
		if ($(this).hasClass("login"))
			$("#login_wrap").show(), $("#login_id").focus();
		else if ($(this).hasClass("signup"))
			$("#signup_wrap").show();
		$("#signup_name").focus();

		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function (e) {
					$("#image-upload").attr('src', e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
		}
		$("#signup_profile_img").change(function () {
			console.log("path:" + this.value);
			readURL(this);
		});
	});

	$("#signup_profile_img").change(function () {
		// signup profile image - filename print
		var arr = $(this).val().split("\\");
		var fName = arr[(arr.length - 1)];
		try {
			console.log(fName);
		} catch (e) {
		}
	});

	$("#signup_select_gender .select_gender_content div").click(
			function () {
				if ($(this).hasClass("on"))
					return;
				$("#signup_select_gender .select_gender_content div")
						.removeClass("on");
				$(this).addClass("on");
				$("#signup_genger").val(
						$(this).attr("id").replace("signup_gender_", ""));
			});

	$("#login_btn_wrap").click(function () {
		showLoginPopup();
	});

	$("#signup_btn").click(function () {
		signup();
	});

	$("#login_btn").click(function () {
		login();
	});
	$("#login_facebook").click(function () {
		alert("facebook login");
	});
	$("#logout").click(function () {
		logout();
	});

	$("#notification").click(function () {
		$("#notification_wrap").animate({
			width: 'toggle'
		}, 100);
	});
});

// login popup show
function showLoginPopup() {
	$(".layer").hide();
	$("#login_wrap").show();
	$("#mask").show();
	$("#login_id").focus();
}

// refer http://utils.egloos.com/v/3217777
// 
function getCookie(cookieName) {
	var search = cookieName + "=";
	var cookie = document.cookie;

	if (cookie.length > 0) {
		startIndex = cookie.indexOf(cookieName);

		if (startIndex != -1) {
			startIndex += cookieName.length;
			endIndex = cookie.indexOf(";", startIndex);

			if (endIndex == -1) endIndex = cookie.length;

			return unescape(cookie.substring(startIndex + 1, endIndex));
		}
		else {
			return false;
		}
	}
	else {
		return false;
	}
}

function setCookie(cookieName, cookieValue, expireDate) {
	var today = new Date();
	today.setDate(today.getDate() + parseInt(expireDate));
	document.cookie = cookieName + "=" + escape(cookieValue) + "; path=/; expires=" + today.toGMTString() + ";";
}

function deleteCookie(cookieName) {
	var expireDate = new Date();

	expireDate.setDate(expireDate.getDate() - 1);
	document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}


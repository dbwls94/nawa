        $(document).ready(function(){
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성
                    reader.onload = function (e) {
                    //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러
                        $('#picture').attr('src', e.target.result);
                        //이미지 Tag의 SRC속성에 읽어들인 File내용을 지정
                        //(아래 코드에서 읽어들인 dataURL형식)
                    }                   
                    reader.readAsDataURL(input.files[0]);
                    //File내용을 읽어 dataURL형식의 문자열로 저장
                }
            }//readURL()--
             
   
            //file 양식으로 이미지를 선택(값이 변경) 되었을때 처리하는 코드
            $("#inputUserPic").change(function(){
                //alert(this.value); //선택한 이미지 경로 표시
                readURL(this);
            });
         });


var model;
var view;
var controller;

function Model() {
	this.inputUserEmail = $("#inputUserEmail");
	this.btnUserEmailDuplCheck = $("#btnUserEmailDuplCheck");
	this.inputUserPwd = $("#inputUserPwd");
	this.inputUserName = $("#inputUserName");
	this.selectUserGender = $("#selectUserGender");
	this.inputUserPic = $("#inputUserPic");
	this.inputUserFAT = $("#inputUserFAT");
	this.btnUserOK = $("#btnUserOK");
	this.btnUserCancel = $("#btnUserCancel");
	
} // Model
model = new Model();

// ---------------------------------------------------------------------------------------------------------

function View() {
	this.init = function() {
		model.btnUserEmailDuplCheck.click(function() {
			var email = model.inputUserEmail.val();
			controller.ajaxCall("/User/isEmailExists", "GET", {
				email : email
			}, function(data) {

			});
		});

		model.btnUserOK.click(function() {
			var email = model.inputUserEmail.val();
			var pwd = model.inputUserPwd.val();
			var name = model.inputUserName.val();
			var gender = model.selectUserGender.val();
			var files = model.inputUserPic[0].files;
			var fat = model.inputUserFAT.val();
			controller.postUser(email, pwd, name, gender, files, fat);
		});
		model.btnUserCancel.click(function() {
			model.inputUserEmail.val("");
			model.inputUserPwd.val("");
			model.inputUserName.val("");
			model.inputUserPic.val("");
			model.inputUserFAT.val("");
		});
	} // init
} // View
view = new View();

function Controller() {
	this.postUser = function(email, password, name, gender, files,
			facebook_access_token) {
		var encryptedPwd = hex_sha512(password);

		var formData = new FormData();
		formData.append("email", email);
		formData.append("password", encryptedPwd);
		formData.append("name", name);
		formData.append("gender", gender);
		formData.append("facebook_access_token", facebook_access_token);
		if (files.length > 0)
			formData.append("userPic", files[0]);
		$.ajax({
			type : "POST",
			url : "/User",
			contentType : false,
			cache : false,
			data : formData,
			processData : false,
			success : function(data) {
				alert("okokokokok");
				location = "project_list.jsp"
			},
			error : function(e) {
				alert("error");
			}
		});
	} // postUser

	this.ajaxCall = function(url, type, data, onSuccess) {
		$.ajax({
			url : url,
			type : type,
			dataType : 'json',
			data : data,
			success : function() {
				alert("ok");
			},

			error : function(e) {
				
			}
		});
	} // ajaxCall
} // Controller
controller = new Controller();

view.init();

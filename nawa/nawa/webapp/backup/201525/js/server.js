/**
 * server.js 서버 통신을 위한 모듈
 * jquery ajax 함수 모듈화로 http://api.jquery.com/jquery.ajax/ 참고하였음
 * http://findfun.tistory.com/382 -> 번역
 */

var server = (function () {
	'use strict';

	var
		default_ajax_settings = {
			url: '',			// Request URL
			type: 'POST',		// "POST", "GET", "PUT", "DELETE"
			async: true,		// true, false
			dataType: 'json',	// "xml", "html", "script", "json"
			timeout: 3000,
			cache: false,		// Default true
			data: null,
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			error: null,		// 통신 에러 발생시 처리	// function (request, status, error)
			success: null,		// 통신 성공시 처리		// function (response, status, request) {
			beforeSend: null,	// 통신을 시작할때 처리
			complete: null		// 통신이 완료된 후 처리
		},
		doAjax, set_ajax_settings,
		// Login Servlet
		setLogin,
		// Logout Servlet
		setLogout,
		// Project Servlet
		setProject, getProject, getProjectList, getProjectInfo, getIsProjectParticipated, setProjectWaitingAcceptUsers,
		getProjectParticipatingUsers, setProjectParticipate, setProjectAcceptParticipate, setProjectDeleteParticipate,
		getProjectParticipatingProjects, getProjectLeadingProjects,
		// Comment Servlet
		getComment, setComment, setUpdateComment, setDeleteComment,
		// User Servlet
		setSignUp, setFollow, setUnFollow, getIsEmailExists, getRelationFollowing, getRelationFollowed, getUserProfile, setUserProfile,
		// Search
		getSearchProject, getSearchMyProject, getSearchUser
	;

	// Private method
	// set_ajax_settings
	set_ajax_settings = function (url, type, data, asyncOption) {
		var ajaxSettings = JSON.parse(JSON.stringify(default_ajax_settings)) //Object.create(default_ajax_settings);
		ajaxSettings.url = url;
		ajaxSettings.type = type;
		ajaxSettings.data = data;

		if (asyncOption) {
			if (asyncOption.async === false) {
				ajaxSettings.async = false;
			} else {
				ajaxSettings.async = true;
				if (asyncOption.callback) {
					ajaxSettings.success = function (response, status, request) {
						asyncOption.callback(response);
					};
					ajaxSettings.error = function (request, status, error) {
						asyncOption.callback({ success: '0', msg: 'ajax call error' });
					};
				}
			}
		}

		return ajaxSettings;
	};

	// doAjax
	doAjax = function (settings) {
		if (settings.async) {
			$.ajax(settings);
			return true;
		} else {
			var response = $.ajax(settings);

			return response.responseJSON;
		}
	};
	// /Private method

	// Public method
	//
	// 목적:
	//    로그인
	// example:
	//    server.setLogin({ 'email': 'test-1231772711@test.com', 'password': 'test-1231772711@test.com-passwd' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun: 
	//    성공시 {success:"1"}
	//    실패시 {success:"0", msg:[why failed]}
	setLogin = function (data, asyncOption) {
		var url = '/Login';
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    로그아웃
	// example:
	//    server.setLogout({ async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success:"1"}
	//    실패시 {success:"0", msg:[why failed]}
	setLogout = function (asyncOption) {
		var url = '/Logout';
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 등록
	// example:
	//    server.setProject({ ..Data.. }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success:"1"}
	//    실패시 {success:"0", msg:[why failed]}
	setProject = function (data, asyncOption) {
		var url = '/Project';
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		ajaxSettings.dataType = 'multipart/form-data; charset=UTF-8';
		ajaxSettings.processData = false;
		ajaxSettings.contentType = false;

		return doAjax(ajaxSettings);
	}

	//
	// 목적:
	//    프로젝트 수정시 데이터 불러오기
	// example:
	//    server.getProject({ projectId: 'project id' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success:"1"}
	//    실패시 {success:"0", msg:[why failed]}
	getProject = function (data, asyncOption) {
		var url = '/Project/' + encodeURI(data.projectId);
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 목록 불러오기
	// example:
	//    server.getProjectList({ page: 1 }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success:"1", projects:[project info in json]}
	//    실패시 {success:"0", msg:[why failed]}
	getProjectList = function (data, asyncOption) {
		var url = '/Project/List/' + encodeURI(data.page); // /Project/List/{page}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 "see more" (상세보기)
	// example:
	//    server.getProjectInfo({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success:"1", project:[project info in json]}
	//    실패시 {success:"0", msg:[why failed]}
	getProjectInfo = function (data, asyncOption) {
		var url = '/Project/' + encodeURI(data.projectId); // /Project/{projectId}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 참가 여부 확인
	// example:
	//    server.getIsProjectParticipated({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success:"1", isParticipated:true/false}
	//    실패시 {success:"0", msg:[why failed]}
	getIsProjectParticipated = function (data, asyncOption) {
		var url = '/Project/isParticipated/' + encodeURI(data.projectId); // /Project/isParticipated/{projectId}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    참여자 관리 창에서
	//    "승인 대기자" 조회
	// example:
	//    server.setProjectWaitingAcceptUsers({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success "1", waitings: [waitings info in json]}
	//    실패시 {success:"0", msg:[why failed]}
	setProjectWaitingAcceptUsers = function (data, asyncOption) {
		var url = '/Project/WaitingAcceptUsers/' + encodeURI(data.projectId); // /Project/WaitingAcceptUsers/{projectId}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    해당 프로젝트에 참여중인 사용자 리스트 출력
	// example:
	//    server.getProjectParticipatingUsers({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success "1", participatings: [participatings info in json]}
	//    실패시 {success:"0", msg:[why failed]}
	getProjectParticipatingUsers = function (data, asyncOption) {
		var url = '/Project/ParticipatingUsers/' + encodeURI(data.projectId); // /Project/ParticipatingUsers/{projectId}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 참가 신청
	// example:
	//    server.setProjectParticipate({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success "1"}
	//    실패시 {success:"0", msg:[why failed]}
	setProjectParticipate = function (data, asyncOption) {
		var url = '/Project/Participate/' + encodeURI(data.projectId); // /Project/Participate/{projectId}
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    "승인 대기자" 승인/거절 처리
	// example:
	//    server.setProjectAcceptParticipate({ projectId: '04055672-0248-41d4-9089-ab6078d37b20', participationUserEmail: 'test7412007@test.com', accept: true }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success "1" ...}
	//    실패시 {success:"0", msg:[why failed]}
	setProjectAcceptParticipate = function (data, asyncOption) {
		var url = '/Project/AcceptParticipate/' + encodeURI(data.projectId); // /Project/AcceptParticipate/{projectId}
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, { participationUserEmail: data.participationUserEmail, accept: data.accept }, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 참가 취소
	// example:
	//    server.setProjectDeleteParticipate({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 {success "1" ...}
	//    실패시 {success:"0", msg:[why failed]}
	setProjectDeleteParticipate = function (data, asyncOption) {
		var url = '/Project/Participate/' + encodeURI(data.projectId); // /Project/Participate/{projectId}
		var type = 'DELETE';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 "see more"에서 댓글 목록 조회
	// example:
	//    server.getComment({ projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1", comments : [comments in json] }
	//    실패시 { success : "0", msg : [whi failed] }
	getComment = function (data, asyncOption) {
		var url = '/Comment/' + encodeURI(data.projectId); // /Comment/{projectId}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    해당 사용자가 참여중인 프로젝트 리스트 출력
	//      include-not-accepted(optional): 승인되지 않은 프로젝트도 포함할지 여부 (true/false)(default false)
	// example:
	//    server.getProjectParticipatingProjects({ email: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", participatings : [사용자가 참여중인 프로젝트들의 project info 출력] }
	//    실패시 { success: "0", msg: (why failed) }
	getProjectParticipatingProjects = function (data, asyncOption) {
		var url = '/Project/ParticipatingProjects/' + encodeURI(data.email); // /Project/ParticipatingProjects/{email}
		var type = 'GET';

		var param = null;
		if (data.include_not_accepted) {
			param = { "include-not-accepted": data.include_not_accepted };
		}

		var ajaxSettings = set_ajax_settings(url, type, param, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    해당 사용자가 추진중인 프로젝트 리스트 출력
	// example:
	//    server.getProjectLeadingProjects({ email: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", leading-projects: [사용자가 추진중인 프로젝트들의 project info 출력] }
	//    실패시 { success: "0", msg: (why failed) }
	getProjectLeadingProjects = function (data, asyncOption) {
		var url = '/Project/LeadingProjects/' + encodeURI(data.email); // /Project/LeadingProjects/{email}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    댓글 작성
	// example:
	//    server.setComment({ projectId: '04055672-0248-41d4-9089-ab6078d37b20', content: 'content', parentSeq: 0 }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1" }
	//    실패시 { success : "0", msg : [why failed] }
	setComment = function (data, asyncOption) {
		var url = '/Comment'; // /Comment
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    댓글 수정
	// example:
	//    server.setUpdateComment({ seq: 0, projectId: '04055672-0248-41d4-9089-ab6078d37b20', content: 'content' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1" }
	//    실패시 { success : "0", msg : [why failed] }
	setUpdateComment = function (data, asyncOption) {
		var url = '/Comment'; // /Comment
		var type = 'PUT';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    댓글 삭제
	// example:
	//    server.setDeleteComment({ seq: 0, projectId: '04055672-0248-41d4-9089-ab6078d37b20' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1" }
	//    실패시 { success : "0", msg : [why failed] }
	setDeleteComment = function (data, asyncOption) {
		var url = '/Comment'; // /Comment
		var type = 'DELETE';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    회원 가입
	// example:
	//    server.setSignUp({ email: 'test@test.com', password: 'encryted password', name: 'name', gender: 0 }
	//                    , { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1", loginToken : [loginToken] }
	//    실패시 { success : "0", msg : [why failed] }
	setSignUp = function (data, asyncOption) {
		var url = '/User'; // /User
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);
		ajaxSettings.dataType = 'multipart/form-data; charset=UTF-8';
		ajaxSettings.processData = false;
		ajaxSettings.contentType = false;

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    팔로우
	// example:
	//    server.setFollow({ targetEmail: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1" }
	//    실패시 { success : "0", msg : [why failed] }
	setFollow = function (data, asyncOption) {
		var url = '/User/Relation'; //  /User/Relation/
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    언팔로우
	// example:
	//    server.setUnFollow({ targetEmail: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1" }
	//    실패시 { success : "0", msg : [why failed] }
	setUnFollow = function (data, asyncOption) {
		var url = '/User/Relation' + '?targetEmail=' + encodeURI(data.targetEmail); // /User/Relation
		var type = 'DELETE';

		var ajaxSettings = set_ajax_settings(url, type, data, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    Email 중복 체크
	// example:
	//    server.getIsEmailExists({ email: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success : "1" }
	//    실패시 { success : "0", msg : [why failed] }
	getIsEmailExists = function (data, asyncOption) {
		//var url = '/User/isEmailExists'; // /User/isEmailExists
		var url = '/User/isEmailExists/' + encodeURI(data.email); // /User/isEmailExists
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    팔로잉 목록
	// example:
	//    server.getRelationFollowing({ email: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    중복시 { success : "1", followings : [following users] }
	//    실패시 { success : "0", msg : [why failed] }
	getRelationFollowing = function (data, asyncOption) {
		var url = '/User/Relation/Following/' + data.email; // /User/Relation/Following
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    팔로워 목록
	// example:
	//    server.getRelationFollowed({ email: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    중복시 { success : "1", followings : [following users] }
	//    실패시 { success : "0", msg : [why failed] }
	getRelationFollowed = function (data, asyncOption) {
		var url = '/User/Relation/Followed/' + data.email; // /User/Relation/Followed
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    해당 사용자의 프로필 정보
	// example:
	//    server.getUserProfile({ email: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", profile: {leading-proejcts: (leading-projects), participating-projects: (participating-projects), user-info: (user-info), followings: (followings), followers: (followers) }
	//    실패시 { success: "0", msg: (why failed) }
	getUserProfile = function (data, asyncOption) {
		var url = '/User/Profile/' + encodeURI(data.email); // /User/Profile/{email}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    회원정보 수정
	// example:
	//    server.setUserProfile({ email: 'test@test.com', formData: {(..form data..)}}, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", loginToken: (login token) }
	//    실패시 { success: "0", msg: (why failed) }
	setUserProfile = function (data, asyncOption) {
		var url = '/User/' + encodeURI(data.email); // /User/{email}
		var type = 'POST';

		var ajaxSettings = set_ajax_settings(url, type, data.formData, asyncOption);

		ajaxSettings.dataType = 'multipart/form-data; charset=UTF-8';
		ajaxSettings.processData = false;
		ajaxSettings.contentType = false;

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    프로젝트 검색
	// example:
	//    server.getSearchProject({ keyword: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", result: (search result in project) }
	//    실패시 { success: "0", msg: (why failed) }
	getSearchProject = function (data, asyncOption) {
		var url = '/Search/Project/' + encodeURI(data.keyword); // /Search/Project/{keyword}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    내가 참여한 프로젝트 중에서 검색
	// example:
	//    server.getSearchMyProject({ keyword: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", result: (search result in my project) }
	//    실패시 { success: "0", msg: (why failed) }
	getSearchMyProject = function (data, asyncOption) {
		var url = '/Search/MyProject/' + encodeURI(data.keyword); // /Search/MyProject/{keyword}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	//
	// 목적:
	//    사용자 이름 검색
	// example:
	//    server.getSearchUser({ keyword: 'test@test.com' }, { async: true, callback: function (response) { /* do... */ } });
	// retrun:
	//    성공시 { success: "1", result: (search result in user) }
	//    실패시 { success: "0", msg: (why failed) }
	getSearchUser = function (data, asyncOption) {
		var url = '/Search/User/' + encodeURI(data.keyword); // /Search/User/{keyword}
		var type = 'GET';

		var ajaxSettings = set_ajax_settings(url, type, null, asyncOption);

		return doAjax(ajaxSettings);
	};

	// /Public method

	// Return
	return {
		setLogin: setLogin,
		setLogout: setLogout,
		setProject: setProject,
		getProjectList: getProjectList,
		getProjectInfo: getProjectInfo,
		getIsProjectParticipated: getIsProjectParticipated,
		setProjectWaitingAcceptUsers: setProjectWaitingAcceptUsers,
		getProjectParticipatingUsers: getProjectParticipatingUsers,
		setProjectParticipate: setProjectParticipate,
		setProjectAcceptParticipate: setProjectAcceptParticipate,
		setProjectDeleteParticipate: setProjectDeleteParticipate,
		getComment: getComment,
		setComment: setComment,
		setUpdateComment: setUpdateComment,
		setDeleteComment: setDeleteComment,
		getProjectParticipatingProjects: getProjectParticipatingProjects,
		getProjectLeadingProjects: getProjectLeadingProjects,
		setSignUp: setSignUp,
		setFollow: setFollow,
		setUnFollow: setUnFollow,
		getIsEmailExists: getIsEmailExists,
		getRelationFollowing: getRelationFollowing,
		getRelationFollowed: getRelationFollowed,
		getUserProfile: getUserProfile,
		setUserProfile: setUserProfile,
		getSearchProject: getSearchProject,
		getSearchMyProject: getSearchMyProject,
		getSearchUser: getSearchUser
	};
}($));
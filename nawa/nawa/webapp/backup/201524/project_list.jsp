<%@page import="org.nawa.common.JwtToken"%>
<%@page import="org.nawa.entity.NavBarEntity"%>
<%@page import="org.nawa.service.NavBarService"%>
<%@page import="org.nawa.common.CookieBox"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>프로젝트 리스트 페이지</title>

		<!-- CSS로딩 

		<link rel="stylesheet" type="text/css" href="css/project_list.css">
		<link rel="stylesheet" type="text/css" href="css/navbar.css">

		-->
		
	</head>
	<body>
		<div id="test"></div>
		<section>
			<header>
				<jsp:include page="navbar.jsp" flush="false"/>
				<!-- nav 바를 jsp include로 넣음 -->
			</header>
			<div id = "container">
			<!--
				<div class="project-list">
					<div class="list-name">
						<div class="list-name-title"> 
							test 여긴 한글 되는지 테스트
						</div>
					</div>  
					<div class="list-menu">
						<img src="img/test.jpg " class="list-img">
					</div>
					<div class="list-member">
						<div class="list-member-number">
							member
						</div>
						<div class="list-member-img-list">
							<a href=""><img class="list-member-img"src="img/photo.jpg"></a>
							<a href=""><img class="list-member-img"src="img/photo.jpg"></a>
						</div>
					</div>            
					<div class="list-calendar">
						2015-02-21
					</div>
					<div class="list-place">
						place
					</div>
					<div class="list-end">
						end
					</div>
					<div class="list-money">
						money              
					</div>
					<div class="list-button-menu">
						<a href=""class="list-button-change" ><span>change</span></a>
						<a href="" class="list-button-invite"  >invite</a>
						<a href="" class="list-button-delete">delete</a>
					</div> 
				</div> 
				-->
			</div><!-- container -->
		</section>
		
		
		
		<!---------------------------------------------------------------------------------------------------------------------------- -->
		
		<!--  js library 파일  -->
		<script type="text/javascript" src="js/lib/modernizr.custom.js"></script>
		<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
		<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

		<!--  js custom 파일  -->
		
		<script type ="text/javascript">//Start Script
				//페이지 로딩완료
				console.log("basic enter!");

				//토큰 jsp로 받아오고, navbar에서 쓸 entity도 받아옴
				
				<%
				String token = CookieBox.get(request, "token"); 
				NavBarEntity entity= NavBarService.getInstance().getNavBarInfoForLoginUser(token); 
				String myEmail = JwtToken.getPayloadValue(token, "email").toString();
				%>
				
				var username ="<%= entity.getUsername()%>";
				var count ="<%= entity.getHandlingProjectCount()%>";
				var notification ="<%= entity.getNotificationCount()%>";

				
				var myEmail ="<%= myEmail%>";
				//myEmail은 나중에 프로젝트가 내프로젝트인지, 남프로젝트인지 구분할 때 쓰임
				
				console.log("confirm myEmail : "+myEmail);
			
				var page = 1;
				
				var id;
				var title;
				var description;
				var leaderEmail;
				var leaderName;
				var projectPics;
				var regdate;
				var place;
				var recruitDueDate;
				var datePeriodStart;
				var datePeriodStartY;
				var datePeriodStartM;
				var datePeriodStartD;
				var datePeriodStartT;						
				var datePeriodEnd;
				var entryTerms;
				var projectStatus;
				var longProject;
				var participantActualCount
				var participantCount;

				
				//ajax 시작!
				console.log("start ajax!");

				//Start ajax로 토큰보내고, 프로젝트 리스트 정보 받아올 예정
				$.ajax({//Start ajax
					url : "/Project/List/"+page, //1page, 2page로 올리면 해당 페이지가 넘어옴
					dataType : "json",
					type : "get",
					success : function(data) {//Start ajax success
						//Start 여기다가 프토젝트 리스트에 쓸 것들 모두 담고 적용함
						console.log("confirm project_list ajax!");
						
						var success = data.success;
						console.log("**success 잘 담겼는지 확인 : "+success);

						if(success == 1){//Start success if문

						page++;//page 하나 올림
						
						var projects = data.projects;
						var projectCount = projects.length;

						console.log("**프로젝트 총 정보 시작**");
						console.log(projects);
						console.log("**프로젝트 총 정보 끝**");

						for(var i=0; i < projectCount; i++){
							//프로젝트 일반 정보 시작
							id = projects[i].id;
							title = projects[i].title;
							description = projects[i].description;
							leaderEmail = projects[i].leader_email;
							leaderName = projects[i].leader_name;
							projectPics = projects[i].project_pics;
							regdate = projects[i].regdate;
							place = projects[i].place;
							recruitDueDate = projects[i].recruit_due_date;
							datePeriodStart = projects[i].date_period_start;
							datePeriodEnd = projects[i].date_period_end;
							entryTerms = projects[i].entry_terms;
							projectStatus = projects[i].project_status;
							longProject = projects[i].long_project;
							participantActualCount = projects[i].participant_actual_count;
							participantCount = projects[i].participant_count;
							//프로젝트 일반 정보 끝
							
							var isMyProject = (leaderEmail == myEmail);
							
						
							//날짜 자르기 시작 -> Ex.2015. 01. 11, 07:00:00
							datePeriodStartY = datePeriodStart.substring(0,4);//2015
							datePeriodStartM = datePeriodStart.substring(5,7);//01
							datePeriodStartD = datePeriodStart.substring(8,10);//11
							datePeriodStartT = datePeriodStart.substring(12,16);//07:00
							//날짜 자르기 끝
							
							

							//** 화면구성 중복되는 부분 'html'속에 모두 담음
							

							var html = 
							"<div class=\"project_list\">\n"+
								"<div class=\"list-name\">\n"+ 
									"<div class=\"list-name-title\">\n"+//프로젝트 제목
										title+
									"</div>\n"+
								"</div>\n"+
								"<div class=\"leader-pic\">\n"+ //프로젝트 리더 사진
									//리더 사진 어떻게 처리할지 백이 알려줄 것(백 미구현)
								"</div>\n"+
								"<div class=\"leader-name\">\n"+ //프로젝트 리더 이름
									leaderName+
								"</div>\n"+
								"<div class=\"main-pic\">\n"+//프로젝트 메인 사진
									projectPics+
								"</div>\n"+
								"<div class=\"participant-info-box\">\n"+
									"<div class=\"participant-box-up\">\n"+//참여자, 모집인원
										"참여자"+
										participantActualCount+
										"  / 모집인원  "+participantCount+
										"<div class=\"participant-manage-btn\">\n"+//참여자 관리 버튼
										((isMyProject)?'<input type="button" value="관리" id="btnManage" />':'')+
										//(내가 추진중인 프로젝트일 때만! 버튼이 만들어져야 함)
										"</div>\n"+
										//참가한 인원 정보가 들어가야 함(백 미구현) 사진
									"</div>\n"+
									"<div class=\"participant-box-mid\">\n"+
									//참여 현황 바 구현하면 됨
									"</div>\n"+
									"<div class=\"participant-box-bottom\">\n"+
									//참가한 사람들의 이미지 정보들이 들어가야함
									"</div>\n"+
								"</div>\n"+
								"<div class=\"line\">\n"+
								"</div>\n"+//participant-info-box 끝

								"<div class=\"project-box\">\n"+
									"<div class=\"project-info-date\">\n"+	
										"일시"+
										datePeriodStartY+
										"년"+
										datePeriodStartM+
										"월"+
										datePeriodStartD+
										"일"+//모임 시작일에 몇 시부터 몇시까지인지는 미구현(백 미구현)
									"</div>\n"+
									"<div class=\"project-info-time\">\n"+	
										"시간 "+
										datePeriodStartT+ 
									"</div>\n"+
									"<div class=\"project-info-place\">\n"+	
										"장소"+
										place+ 
									"</div>\n"+
									"<div class=\"project-info-due\">\n"+	
										"모집마감일"+
										recruitDueDate+ 
									"</div>\n"+
								"</div>\n"+
								"<div class=\"description\">\n"+ 
									description+
								"</div>\n"+
								"<div class=\"comment-box\">\n"+ 
									"<div class=\"comment-btn\" onclick=\"commentAdd('"+id+"')\">\n"+ 
										//댓글 몇 개?
										" / 내용 더 보기"+
										((isMyProject)?'<input type="button" value="수정" id="btnEdit" />':'')+
										((isMyProject)?'<input type="button" value="삭제" id="btnDel" />':'')+
									"</div>\n"
								"</div>\n"+							
							"</div>";

							//container에 추가
							$("#container").append(html);
													


							//화면 구성 : 남이 진행하는 프로젝트 일 때,	
							if(!isMyProject) {
								
							//화면 구성 : 남이 진행하는 프로젝트 일 때,	참가신청이 완료된 경우
							
							//화면 구성 : 남이 진행하는 프로젝트 일 때,	참가신청만하고 승인이 안된 경우
							
							//화면 구성 : 남이 진행하는 프로젝트 일 때,	아직 참가신청 안한 경우

							}
							
							
							//화면 구성 : 장기 VS 단기							

							}//for 구문 끝	
							

							
						//End success if문	
						}
					//End ajax success
					}
				//End ajax
				});
				
				//** 댓글(내용더보기 클릭시) 작동하는 함수 시작
				function commentAdd(id){//Start commentAdd function
					
					console.log("댓글더보기함수");
					
					var commentId = id;
					var commentUser;
					var commentUserEmail;
					var commentDepth;
					var commentRegdate;
					
					console.log("ID값 : "+commentId);

					
				//** 댓글(내용더보기 클릭시) 받아오기 ajax 시작
				$.ajax({//Start ajax
					url : "/Comment/"+commentId,
					dataType : "json",
					type : "get",
					success : function(data) {//Start ajax-Success
													

	
							
						}//End ajax-Success
					});//End ajax
				};//End commentAdd function
				//** 댓글(내용더보기 클릭시) 작동하는 함수 끝
				
		</script>
		<script type="text/javascript" src="js/custom/navbar.js"></script>
		
	</body>
</html>
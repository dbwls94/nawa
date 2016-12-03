<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/open_project.css">

<title>Insert title here</title>
</head>
<body>
	<header>
		<nav class="navbar-top">

             <a href=""> <img class="nav-logo" src="" alt=""/><a/>  
             
             <input type="search"  placeholder="search" >
             
             <input type="button" class="search-button"value="test" onClick=""/>
            
            <a class="nav-profile"><img class="profile-img" src="img/photo.jpg" alt=""/>
              
             <span class="profile-name">name</span>             
            <a/> 

            <a href="">
              logout
            </a>
          
              <a href=""><img src="" class="nav-friend">friend</a>
            
                 <a href=""><img src="" class="nav-arlam">arlam</a>

                 <a href="project_add" class="nav-project-add">start</a>

              
  </nav> 

</header>




	<div class="project-list">
		<input type="text" id="title-id" name="title-name" value="모임 이름을 입력해주세요"/>
		<label>단기 프로젝트</label><input type="radio" id="long-id" name="long-name"/>
		<div id ="when">
			
		</div>
	</div> <!-- project-list -->

</body>
</html>
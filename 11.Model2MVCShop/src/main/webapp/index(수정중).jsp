<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>


<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ///////////////////////////// 로그인시 Forward  /////////////////////////////////////// -->
 <c:if test="${ ! empty user }">
 	<jsp:forward page="main.jsp"/>
 </c:if>
 <!-- //////////////////////////////////////////////////////////////////////////////////////////////////// -->

<!doctype html>
<html lang="ko">
  <head>
    <meta charset="euc-kr"> <!-- 한글설정 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge"> <!-- IE 에서만 해석되는 tag, IE=edge는 최신버전으로 랜더링 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> <!-- width=device-width: 플랫폼 가로 크기에 맞춤, 수치를 넣으면 수치에맞게 맞춰짐 --> 																	 <!-- initial-scale=1: 페이지 로딩시 확대비율 -->
	
	<link href="css/main.css" rel="stylesheet">	
	<script type="text/javascript">
		
		//============= 회원원가입 화면이동 =============
		$( function() {
			//==> 추가된부분 : "addUser"  Event 연결
			$("a[href='#' ]:contains('회원가입')").on("click" , function() {
				self.location = "/user/addUser"
			});
		});
		
		//============= 로그인 화면이동 =============
		$( function() {
			//==> 추가된부분 : "addUser"  Event 연결
			$("a[href='#' ]:contains('로 그 인')").on("click" , function() {
				self.location = "/user/login"
			});
		});
		
	</script>
	    
  </head>
  
   <body>

		<!-- Header -->
			<header id="header">
				<h1>Model2 MVC Shop</h1>
				<p>model,view,controller
				<a href="https://developer.mozilla.org/ko/docs/Glossary/MVC">MVC</a>.</p>
			</header>

		<!-- Signup Form -->
			<form id="signup-form" method="post" action="#">
				<input type="email" name="email" id="email" placeholder="Email Address" />
				<input type="submit" value="Sign Up" />
			</form>

		<!-- Footer -->
			<footer id="footer">
				<ul class="icons">
					<li><a href="#" class="icon brands fa-twitter"><span class="label">Twitter</span></a></li>
					<li><a href="#" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
					<li><a href="#" class="icon brands fa-github"><span class="label">GitHub</span></a></li>
					<li><a href="#" class="icon fa-envelope"><span class="label">Email</span></a></li>
				</ul>
				<ul class="copyright">
					<li>&copy; Untitled.</li><li>Credits: <a href="http://html5up.net">HTML5 UP</a></li>
				</ul>
			</footer>

		<!-- Scripts -->
			<script src="javascript/main.js"></script>

	</body>
</html>
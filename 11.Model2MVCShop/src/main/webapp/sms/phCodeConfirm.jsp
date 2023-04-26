<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style></style>
    
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">
	
		var smsConfirmNum; // smsConfirmNum 값을 사용하기 위한 전역변수 선언
	
		//=============  "확인"  Event 처리 =============
		$(function() {
			
			$("#phCodeConfirm").focus();
			
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$(".btn-action-1").on("click" , function() {				
								 
				var phCodeConfirm = $("#phCodeConfirm").val();
				
				$.ajax({
				    url: "/sms/phCodeConfirm",
				    type: "POST",
				    contentType: "application/json; charset=utf-8",
				    data: JSON.stringify({
				        smsConfirmNum: smsConfirmNum,
				        phCodeConfirm: phCodeConfirm
				    }),
				    dataType: "json",
				    success: function(response) {				    	
				    	
				    	if(response.result) {
				    		
				    		var phCode = window.opener.$('#phCode');
				    		phCode.val("인증완료");
				    		phCode.prop("disabled", true);
				    		
				    		window.close();				    		
				    		
				    	}else if($('#phCodeConfirm').val() == ''){
				    		alert('인증번호를 입력해주세요.');
				    	}else{
				    		alert('인증번호가 일치하지 않습니다.');
				    	}
				    	
				    },
				    error: function(error) {
				        
				    	alert("실패");
				    	
				    }
				});
			});
		});
	
	
		//=============  "재전송"  Event 처리 =============
		$(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$(".btn-action-2").on("click" , function() {
				
				window.opener.resendSmsConfirmNum().then(function(newSmsConfirmNum) {				
				
				smsConfirmNum = newSmsConfirmNum;				
				
				});
			});
		})		
		
		//=============   "닫기"  Event  처리 =============
		$(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$("button.btn.btn-primary").on("click" , function() {
				window.close();
			});
		});

	</script>
	
</head>

<body>
	
	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
		
		<br/><br/>
		
		<!-- form Start /////////////////////////////////////-->
		<form class="form-inline">
		
		  <div class="form-group">
		    <label for="phcode"></label>
		    <input type="text" class="form-control" name="phCodeConfirm" id="phCodeConfirm"  placeholder="인증번호를입력해주세요">		    																		
		  </div>
		  <button type="button" class="btn btn-info btn-action-1">확 인</button>
		  
		  <button type="button" class="btn btn-info btn-action-2">재전송</button>		  
		  
		  <button type="button" class="btn btn-primary">닫 기</button>
		  
		  
		 
		</form>
		<!-- form Start /////////////////////////////////////-->
	
 	</div>
 	<!--  화면구성 div End /////////////////////////////////////-->

</body>

</html>
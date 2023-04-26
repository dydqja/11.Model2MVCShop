<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- ���� : http://getbootstrap.com/css/   ���� -->
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
	
		var smsConfirmNum; // smsConfirmNum ���� ����ϱ� ���� �������� ����
	
		//=============  "Ȯ��"  Event ó�� =============
		$(function() {
			
			$("#phCodeConfirm").focus();
			
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
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
				    		phCode.val("�����Ϸ�");
				    		phCode.prop("disabled", true);
				    		
				    		window.close();				    		
				    		
				    	}else if($('#phCodeConfirm').val() == ''){
				    		alert('������ȣ�� �Է����ּ���.');
				    	}else{
				    		alert('������ȣ�� ��ġ���� �ʽ��ϴ�.');
				    	}
				    	
				    },
				    error: function(error) {
				        
				    	alert("����");
				    	
				    }
				});
			});
		});
	
	
		//=============  "������"  Event ó�� =============
		$(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$(".btn-action-2").on("click" , function() {
				
				window.opener.resendSmsConfirmNum().then(function(newSmsConfirmNum) {				
				
				smsConfirmNum = newSmsConfirmNum;				
				
				});
			});
		})		
		
		//=============   "�ݱ�"  Event  ó�� =============
		$(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$("button.btn.btn-primary").on("click" , function() {
				window.close();
			});
		});

	</script>
	
</head>

<body>
	
	<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
	<div class="container">
		
		<br/><br/>
		
		<!-- form Start /////////////////////////////////////-->
		<form class="form-inline">
		
		  <div class="form-group">
		    <label for="phcode"></label>
		    <input type="text" class="form-control" name="phCodeConfirm" id="phCodeConfirm"  placeholder="������ȣ���Է����ּ���">		    																		
		  </div>
		  <button type="button" class="btn btn-info btn-action-1">Ȯ ��</button>
		  
		  <button type="button" class="btn btn-info btn-action-2">������</button>		  
		  
		  <button type="button" class="btn btn-primary">�� ��</button>
		  
		  
		 
		</form>
		<!-- form Start /////////////////////////////////////-->
	
 	</div>
 	<!--  ȭ�鱸�� div End /////////////////////////////////////-->

</body>

</html>
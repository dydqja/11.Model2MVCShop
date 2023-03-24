<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<title>상품등록</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >

	<script type="text/javascript" src="../javascript/calendar.js"></script>
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
       body > div.container{
        	border: 3px solid #D6CDB7;
            margin-top: 10px;
        }
        .header_div {
        	width: 100%;
        	height: 37px;
        	background-color: whitesmoke;
        }
        .form-group {
        	width: 104px;
        	height: 50px;        	
        }
        .manuDate {
        	width: 104px;
        	height: 50px;
        	
        }         
        
    </style>
    
	<!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">
	
		//=====기존Code 주석 처리 후  jQuery 변경 ======//
		function fncAddProduct(){
			//Form 유효성 검증
 			//var name = document.detailForm.prodName.value;
			//var detail = document.detailForm.prodDetail.value;
			//var manuDate = document.detailForm.manuDate.value;
			//var price = document.detailForm.price.value;
			
			var name=$("input[name='prodName']").val();
			var detail=$("input[name='prodDetail']").val();
			var manuDate=$("input[name='manuDate']").val();
			var price=$("input[name='price']").val();
			

			if(name == null || name.length<1){
				alert("상품명은 반드시 입력하여야 합니다.");
				return;
			}
			if(detail == null || detail.length<1){
				alert("상품상세정보는 반드시 입력하여야 합니다.");
				return;
			}
			
			if(manuDate == null || manuDate.length<1){
				alert("제조일자는 반드시 입력하셔야 합니다.");
				return;
			}
			if(price == null || price.length<1){
				alert("가격은 반드시 입력하셔야 합니다.");
				return;
			}

			//document.detailForm.action='/product/addProduct';
			//document.detailForm.submit();
			$("form").attr("method","POST").attr("enctype","multipart/form-data").attr("action","/product/addProduct").submit();
		}
		//===========================================//
		//==> 추가된부분 : "상품등록"  Event 연결
		 $(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			//==> 1 과 3 방법 조합 : $("tagName.className:filter함수") 사용함.	
			 $( ".submit" ).on("click" , function() {
				//Debug..
				//alert(  $( "td.ct_btn01:contains('등록')" ).html() );
				fncAddProduct();
			});
		//==> 추가된부분 : "취소"  Event 처리 및  연결
			 $( ".cancel" ).on("click" , function() {
				 $("form")[0].reset();
			
		 		});
			 });
			
		
		
		/*============= jQuery 변경 주석처리 =============
		function resetData(){
			document.detailForm.reset();
		}========================================	*/
	
	</script>
	
</head>

<body bgcolor="#ffffff" text="#000000">


<form class="form-inline" name="detailForm">
<!-- div 수정공간 -->


<div class="header_div" >
	<img src="/images/ct_ttl_img01.gif" dwith="15" height="37"/>상품등록		
</div>
<br/><br/>
 
<div class="form-group ct_write">
	<label for="prodName">상품명&nbsp;<img src="/images/ct_icon_red.gif"></label>
	<input type="text" class="form-control ct_input_g" id="prodName" name="prodName" >
</div>
<br/><br/>

<div class="form-group ct_write">
	<label for="prodDetail">상품상세정보&nbsp;<img src="/images/ct_icon_red.gif"></label>
	<input type="text" class="form-control ct_input_g" id="prodDetail" name="prodDetail" >
</div>
<br/><br/>
 
<div class="form-group ct_write manuDate">
	<label>제조일자 &nbsp;<img src="../images/ct_icon_date.gif" width="15" height="15"
							   onclick="show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value)"/>&nbsp;
						  <img src="/images/ct_icon_red.gif">
	</label>
	<input type="text" class="form-control ct_input_g" id="manuDate" name="manuDate" placeholder="달력을 클릭해 입력.." readonly>	
</div>
<br/><br/>

<div class="form-group ct_write">
	<label for="price">가격&nbsp;<img src="/images/ct_icon_red.gif"></label>
	<input type="text" class="form-control ct_input_g" id="price" name="price" >
</div>
<br/><br/>
 
<div class="form-group ct_write">
    <label for="uploadFiles">상품이미지</label>
    <input type="file" id="uploadFiles" name="uploadFiles" multiple="multiple">
    <p class="help-block">최대용량 어쩌구</p>
</div>
<br/><br/>
<br/><br/>

<div>
<button class="btn btn-primary submit" >등록</button>
<button class="btn btn-danger cancel" >취소</button>
</div>






<!-- div 수정공간 끝 -->




</form>


</body>
</html>

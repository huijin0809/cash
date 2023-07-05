<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
	<!-- 부트스트랩5 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<!-- JQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<div class="container p-5 my-5 border">
		<h1>Cashbook</h1>
		<p>2023.06.30~ mvc 방식으로 가계부 페이지 만들기</p>
		<p>GDJ66 김희진</p>
	</div>
	<div class="container">
		<h1>로그인</h1>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<table class="table-bordered">
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="memberId" id="memberId" class="form-control">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="memberPw" id="memberPw" class="form-control"></td>
				</tr>
			</table> <br>
			<button type="submit" id="submitBtn" class="btn btn-dark btn-sm">로그인</button>
			<a href="${pageContext.request.contextPath}/addMember" class="btn btn-outline-dark btn-sm">회원가입</a>
			<!-- a태그로 값을 보내면 get방식 -->
		</form>
	</div>
	
	<!-- JQuery 코드 시작 -->
	<script>
		// 메세지창 띄우기
		$(document).ready(function() {
	        let urlParams = new URLSearchParams(window.location.search);
	        // URLSearchParams() -> URL에서 쿼리 문자열을 다룰 수 있는 메서드
	        // 쿼리 문자열? -> URL에서 ?키:값으로 이루어진 부분 -> success=ture
	        let msgParam = urlParams.get('msg');
	        // urlParams.get() -> 매개변수가 키인 값을 반환
	        if (msgParam != null) {
	            alert(msgParam);
	        }
	    });
		
		// 공백 검사
		$('#submitBtn').click(function(event) {
			if($('#memberId').val() == '') {
				alert("아이디를 입력해주세요");
				event.preventDefault(); // form 제출 막기
				return;
			} else if($('#memberPw').val() == '') {
				alert("비밀번호를 입력해주세요");
				event.preventDefault(); // form 제출 막기
				return;
			}
		}); 
	</script>
</body>
</html>
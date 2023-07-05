<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>removeMember.jsp</title>
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
		<h1>회원 탈퇴</h1>
		<h6>탈퇴하시려면 비밀번호를 입력해주세요</h6>
		<form action="${pageContext.request.contextPath}/removeMember" method="post">
			<table class="table-bordered">
				<tr>
					<td><input type="password" name="inputMemberPw" id="inputMemberPw" class="form-control"></td>
				</tr>
			</table> <br>
			<a href="${pageContext.request.contextPath}/memberOne" class="btn btn-outline-dark btn-sm">취소</a>
			<button type="submit" class="btn btn-danger btn-sm" id="delBtn">탈퇴</button>
		</form>
	</div>
	
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
		$('#delBtn').click(function(event) {
			if($('#inputMemberPw').val() == '') {
				alert("비밀번호를 입력해주세요");
				event.preventDefault(); // form 제출 막기
				return;
			}
		}) 
	</script>
</body>
</html>
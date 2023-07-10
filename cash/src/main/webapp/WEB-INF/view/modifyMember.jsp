<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyMember.jsp</title>
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
		<h1>회원정보 수정</h1>
		<h6>현재 비밀번호 수정만 가능합니다</h6>
		<form action="${pageContext.request.contextPath}/modifyMember" method="post">
			<table class="table">
				<tr>
					<th>아이디</th>
					<td>${member.memberId}</td> <!-- 수정 불가 -->
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="memberPw" id="memberPw_1"></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" id="memberPw_2">
						<span id="pwCheckResult"></span>
					</td>
				</tr>
				<tr>
					<th>마지막 수정일</th>
					<td>${member.updatedate}</td> <!-- 수정 불가 -->
				</tr>
				<tr>
					<th>최초 가입일</th>
					<td>${member.createdate}</td> <!-- 수정 불가 -->
				</tr>
			</table> <br>
			<a href="${pageContext.request.contextPath}/memberOne" class="btn btn-outline-dark btn-sm">뒤로</a>
			<button type="submit" id="modifyBtn" class="btn btn-dark btn-sm">수정</button>
		</form>
	</div>
	
	<!-- JQuery 코드 시작 -->
	<script>
		// 수정 활성화 검사 변수 선언
		let pwChk = 0;
		
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
		
		// pw 일치 확인
		$('#memberPw_2').on('input',function() { // 해당 id의 input태그에 값이 입력될때마다 이벤트 발생
			if($('#memberPw_1').val() != $('#memberPw_2').val()) {
				$('#pwCheckResult').html('<span style="color: red;">비밀번호가 일치하지 않습니다</span>');
				pwChk = 0; // pwChk 비활성화
				console.log('비밀번호 체크 : ' + pwChk);
			} else if($('#memberPw_1').val() == $('#memberPw_2').val()) {
				$('#pwCheckResult').html('<span style="color: green;">비밀번호가 일치합니다</span>');
				pwChk = 1; // pwChk 활성화
				console.log('비밀번호 체크 : ' + pwChk);
			}
		});
		
		// 수정 버튼 활성화 유무 확인
		$('#modifyBtn').click(function(event) {
			if(pwChk != 1) {
				event.preventDefault(); // form 제출 막기
				alert('비밀번호 확인을 진행해주세요');
			}
		});
	</script>
</body>
</html>
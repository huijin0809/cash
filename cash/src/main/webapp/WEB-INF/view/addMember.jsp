<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addMember.jsp</title>
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
		<h1>회원가입</h1>
		<form action="${pageContext.request.contextPath}/addMember" method="post">
			<table class="table">
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="memberId" id="inputId">
						<button type="button" id="idCheck" class="btn btn-secondary btn-sm">중복검사</button>
						<span id="idCheckResult"></span>
					</td>
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
			</table> <br>
			<!-- a태그로 값을 보내면 get방식 -->
			<a href="${pageContext.request.contextPath}/login" class="btn btn-danger btn-sm">취소</a>
			<button type="submit" class="btn btn-dark btn-sm" id="signupBtn">회원가입</button>
		</form>
	</div>
	
	<!-- JQuery 코드 시작 -->
	<script>
		// 메세지창 띄우기
		$(document).ready(function() {
	        let urlParams = new URLSearchParams(window.location.search);
	        // URLSearchParams() -> URL에서 쿼리 문자열을 다룰 수 있는 메서드
	        // 쿼리 문자열? -> URL에서 ?키:값으로 이루어진 부분 -> success=ture
	        let successParam = urlParams.get('success');
	        // urlParams.get() -> 매개변수가 키인 값을 반환
	        if (successParam == 'false') {
	            alert('회원가입에 실패하였습니다 다시 시도해주세요');
	        }
	    });
	
		// 회원가입 활성화 검사 변수 선언
		let idChk = 0;
		let pwChk = 0;
		
		// id 중복 검사
		$('#idCheck').click(function() {
			if($('#inputId').val == '') {
				alert('중복검사할 아이디를 입력하세요');
			} else {
				$.ajax({
					url : '${pageContext.request.contextPath}/memberIdCheck',
					data : {inputId : $('#inputId').val()},
					dataType: 'json',
					type : 'post',
					success : function(result) {
						if(result >= 1) {
							$('#idCheckResult').html('<span style="color: red;">이미 사용중인 id입니다</span>');
							idChk = 0; // idChk 비활성화
							console.log('아이디 체크 : ' + idChk);
						} else if(result == 0) {
							$('#idCheckResult').html('<span style="color: green;">사용가능한 id입니다</span>');
							idChk = 1; // idChk 활성화
							console.log('아이디 체크 : ' + idChk);
						}
					},
					error : function(err) {
						alert('err');
						console.log(err);
					}
				});
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
		
		// 회원가입 버튼 활성화 유무 확인
		$('#signupBtn').click(function(event) {
			if(idChk != 1 || pwChk != 1) {
				event.preventDefault(); // form 제출 막기
				if(idChk != 1) {
					alert('아이디 중복 검사를 진행해주세요');
				} else if(pwChk != 1) {
					alert('비밀번호 확인을 진행해주세요');
				}
			}
		});
	</script>
	
</body>
</html>
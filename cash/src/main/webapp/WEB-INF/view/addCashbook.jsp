<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addCashbook.jsp</title>
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
		<h1>가계부 추가</h1>
		<form action="${pageContext.request.contextPath}/addCashbook" method="post">
		<input type="hidden" name="memberId" value="${memberId}">
			<table class="table">
				<tr>
					<th>cashbookDate</th>
					<td>
						<input type="date" name="cashbookDate" value="${cashbookDate}" id="cashbookDate">
					</td>
				</tr>
				<tr>
					<th>category</th>
					<td>
						<input type="radio" name="category" value="수입">수입
						<input type="radio" name="category" value="지출">지출
					</td>
				</tr>
				<tr>
					<th>price</th>
					<td><input type="number" name="price" id="price"></td>
				</tr>
				<tr>
					<th>memo</th>
					<td>
						<textarea rows="15" cols="30" name="memo" class="form-control" placeholder="해시태그를 사용해서 작성해보세요!" id="memo"></textarea>
					</td>
				</tr>
			</table> <br>
			<a href="${pageContext.request.contextPath}/calendar" class="btn btn-danger btn-sm">취소</a>
			<button type="submit" class="btn btn-dark btn-sm" id="btn">추가</button>
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
		$('btn').click(function(event) {
			// 입력값 변수에 저장
			let cashbookDate = $('#cashbookDate').val();
			let category = $('input[name="category"]:checked').val();
			let price = $('#price').val();
			let memo = $('#memo').val();
			
			if(cashbookDate == '') {
				alert("날짜를 선택해주세요");
				event.preventDefault(); // form 제출 막기
				return;
			} else if(category == undefined) {
				alert("수입/지출을 선택해주세요");
				event.preventDefault();
				return;
			} else if(price == '') {
				alert("가격을 입력해주세요");
				event.preventDefault();
				return;
			} else if(memo == '') {
				alert("메모를 작성해주세요");
				event.preventDefault();
				return;
			}
		});
	</script>
</body>
</html>
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
						<input type="date" name="cashbookDate" value="${cashbookDate}">
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
					<td><input type="number" name="price"></td>
				</tr>
				<tr>
					<th>memo</th>
					<td>
						<textarea rows="15" cols="30" name="memo" class="form-control" placeholder="해시태그를 사용해서 작성해보세요!"></textarea>
					</td>
				</tr>
			</table> <br>
			<a href="${pageContext.request.contextPath}/calendar" class="btn btn-danger btn-sm">취소</a>
			<button type="submit" class="btn btn-dark btn-sm">추가</button>
		</form>
	</div>

</body>
</html>
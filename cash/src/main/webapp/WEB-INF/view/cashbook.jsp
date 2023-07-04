<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbook.jsp</title>
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
		<h1>가계부</h1>
		<a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-dark btn-sm">로그아웃</a>
		<a href="${pageContext.request.contextPath}/calendar" class="btn btn-dark btn-sm">달력</a>
		<a href="${pageContext.request.contextPath}/memberOne" class="btn btn-dark btn-sm">회원정보</a>
	</div>
</body>
</html>
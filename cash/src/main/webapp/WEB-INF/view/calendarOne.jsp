<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbookOne.jsp</title>
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
		<h3>${memberId}님의 ${fn:substring(cashbookDate,0,4)}년 ${fn:substring(cashbookDate,5,7)}월 ${fn:substring(cashbookDate,8,10)}일 가계부</h3>
		<div style="float:left;">
			<form action="${pageContext.request.contextPath}/calendarOne" method="get">
				<input type="hidden" name="memberId" value="${memberId}">
				<input type="hidden" name="cashbookDate" value="${cashbookDate}">
				<select name="category" onchange="this.form.submit()">
					<option value="" <c:if test="${category == ''}"> selected </c:if>>==전체==</option>
					<option value="수입" <c:if test="${category == '수입'}"> selected </c:if>>수입</option>
					<option value="지출" <c:if test="${category == '지출'}"> selected </c:if>>지출</option>
				</select>
				<select name="orderBy" onchange="this.form.submit()">
					<option value="latestDate" <c:if test="${orderBy == '' || orderBy == 'latestDate'}"> selected </c:if>>최신순</option>
					<option value="oldestDate" <c:if test="${orderBy == 'oldestDate'}"> selected </c:if>>오래된순</option>
					<option value="highPrice" <c:if test="${orderBy == 'highPrice'}"> selected </c:if>>높은금액순</option>
					<option value="lowPrice" <c:if test="${orderBy == 'lowPrice'}"> selected </c:if>>낮은금액순</option>
				</select>
			</form>
		</div>
		<div style="float:right;">
			<a href="${pageContext.request.contextPath}/calendar" class="btn btn-dark btn-sm">달력보기</a>
			<a href="${pageContext.request.contextPath}/addCashbook?cashbookDate=${cashbookDate}" class="btn btn-outline-dark btn-sm">내역추가</a>
		</div>
		<table class="table">
			<tr>
				<th>category</th>
				<th>price</th>
				<th>memo</th>
				<th>updatedate</th>
				<th>createdate</th>
			</tr>
			<c:forEach var="c" items="${list}">
				<tr>
					<td>${c.category}</td>
					<td>
						<c:if test="${c.category == '수입'}">
							<span style="color:green">+${c.price}</span>
						</c:if>
						<c:if test="${c.category == '지출'}">
							<span style="color:orange">-${c.price}</span>
						</c:if>
					</td>
					<td>${c.memo}</td>
					<td>${c.updatedate}</td>
					<td>${c.createdate}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
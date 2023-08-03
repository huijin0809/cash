<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbookListByTag.jsp</title>
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
		<h3>${memberId}님의 #${word} 가계부 목록</h3>
		<a href="${pageContext.request.contextPath}/calendar" class="btn btn-dark btn-sm">달력보기</a>
		<!-- 정렬 기능 추가 -->
		<form action="${pageContext.request.contextPath}/cashbookListByTag" method="get">
			<input type="hidden" name="word" value="${word}">
			<input type="date" name="beginDate" value="${beginDate}"> ~ <input type="date" name="endDate" value="${endDate}">
			<button type="submit">검색</button>
		</form>
		<!-- rowPerPage 선택 기능 추가 예정... -->
		<table class="table">
			<tr>
				<th>category</th>
				<th>price</th>
				<th>memo</th>
				<th>updatedate</th>
				<th>createdate</th>
				<th>cashbookDate</th>
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
					<td>${c.cashbookDate}</td>
				</tr>
			</c:forEach>
		</table>
		
		<!-- 페이지 네비게이션 -->
		<div>
			<nav>
            	<ul class="pagination justify-content-center">
            		<c:if test="${beginPage != 1}">
            			<li class="page-item">
            				<a href="${pageContext.request.contextPath}/cashbookListByTag?currentPage=${beginPage - 1}&rowPerPage=${rowPerPage}&word=${word}&beginDate=${beginDate}&endDate=${endDate}" class="page-link">
            					&laquo; <!-- 이전 버튼 -->
            				</a>
            			</li>
            		</c:if>
            		<c:if test="${beginPage == 1}"> <!-- 1페이지에서는 버튼 비활성화 -->
            			<li class="page-item disabled">
					      <span class="page-link">&laquo;</span>
					    </li>
            		</c:if>
            		
            		<c:forEach var="i" begin="${beginPage}" end="${endPage}" step="1">
            			<c:if test="${i != currentPage}">
            				<li class="page-item">
								<a href="${pageContext.request.contextPath}/cashbookListByTag?currentPage=${i}&rowPerPage=${rowPerPage}&word=${word}&beginDate=${beginDate}&endDate=${endDate}" class="page-link">
									${i}
								</a>
							</li>
            			</c:if>
            			<c:if test="${i == currentPage}"> <!-- 현재 페이지에서는 버튼 비활성화 -->
            				<li class="page-item disabled">
								<span class="page-link">${i}</span>
							</li>
            			</c:if>
            		</c:forEach>
            		
            		<c:if test="${endPage != lastPage}">
            			<li class="page-item">
            				<a href="${pageContext.request.contextPath}/cashbookListByTag?currentPage=${endPage + 1}&rowPerPage=${rowPerPage}&word=${word}" class="page-link">
            					&raquo; <!-- 다음 버튼 -->
            				</a>
            			</li>
            		</c:if>
            		<c:if test="${endPage == lastPage}"> <!-- 마지막 페이지에서는 버튼 비활성화 -->
            			<li class="page-item disabled">
					      <span class="page-link">&raquo;</span>
					    </li>
            		</c:if>
            	</ul>
            </nav>
		</div>
	</div>

</body>
</html>
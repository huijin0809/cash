<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%> <!-- JSTL 사용하기 위해 -->
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <!-- JSTL substring 호출하기 위해 -->
<!--  JSP컴파일시 자바코드로 변환되는 c:...(제어문 코드) 커스템태그 사용가능 -->
<%
	// jsp에 자바코드 없애기...!
	
	// 1. 변수값 or 반환값 => EL사용 $표현식
	/*
		속성값 대신에 EL사용하는 방법
		ex) request.getAttribute("targetYear") => $(requestScope.targetYear)
			단, 알아서 request에서 값을 찾기 때문에 requestScope는 생략가능하다
			즉, $(targetYear) 라고만 작성 가능!
	*/
	// => 결론적으로 값을 변수에 받아올 필요가 없다!! 바로 EL을 이용하여 값 사용 가능
	
	// 2. 자바코드(제어문) => JSTL 사용 (lib폴더에 jar파일 적용 필요)
	// prefix="c" => c: 입력시 코드 사용 가능함
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar.jsp</title>
	<!-- 부트스트랩5 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style>
	a {
		text-decoration: none;
	}
</style>
</head>
<body>
	<div class="container p-5 my-5 border">
		<h1>Cashbook</h1>
		<p>2023.06.30~ mvc 방식으로 가계부 페이지 만들기</p>
		<p>GDJ66 김희진</p>
	</div>
	
	<div class="container">
		<table>
			<tr>
				<td>
					<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth - 1}" class="btn btn-outline-dark btn-sm">
						이전달
					</a>
				</td>
				<td>
					<h3>${memberId}님의 ${targetYear}년 ${targetMonth + 1}월 달력</h3>
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth + 1}" class="btn btn-outline-dark btn-sm">
						다음달
					</a>
				</td>
			</tr>
		</table>
		<div style="float:right;">
			<a href="${pageContext.request.contextPath}/cashbook" class="btn btn-dark">뒤로</a>
			<a href="${pageContext.request.contextPath}/addCashbook" class="btn btn-outline-dark">내역추가</a>
		</div>
		
		<div>
			<h2>이달의 해시태그</h2>
			<div>
				<c:forEach var="m" items="${htList}">
					<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${m.word}">${m.word}(${m.cnt})</a>
				</c:forEach>
			</div>
		</div>
		
		<table class="table">
			<thead class="table-dark">
				<tr>
					<th>일</th>
					<th>월</th>
					<th>화</th>
					<th>수</th>
					<th>목</th>
					<th>금</th>
					<th>토</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<!-- 주의) end는 해당 값까지 포함해서 반복하기 때문에 -1 해주어야함! -->
					<c:forEach var="i" begin="0" end="${totalCell - 1}" step="1"> <!-- for문처럼 작성 가능 -->
						<c:if test="${i != 0 && i % 7 == 0}">
							</tr><tr>
						</c:if>
						<c:set var="day" value="${i - beginBlank + 1}"></c:set> <!-- 변수 선언 -->
						<!-- choose, when, otherwise를 사용하면 if-else문처럼 작성 가능 -->
						<!-- choose문 안에 주석이 들어가면 에러 발생하므로 주의 -->
						<c:choose>
							<c:when test="${day > 0 && day <= lastDate}">
								<td>
									<c:choose>
										<c:when test="${i % 7 == 0}">
											<div style="color:red;">${day}</div>
										</c:when>
										<c:when test="${i % 7 == 6}">
											<div style="color:blue;">${day}</div>
										</c:when>
										<c:otherwise>
											<div>${day}</div>
										</c:otherwise>
									</c:choose>
										
									<c:forEach var="c" items="${list}"> <!-- JSTL로 setter,getter 사용하는법 -->
										<a href="${pageContext.request.contextPath}/calendarOne?cashbookDate=${c.cashbookDate}"> <!-- 클릭시 해당 날짜 상세보기 -->
											<c:if test="${day == fn:substring(c.cashbookDate,8,10)}">
												<div>
													<c:if test="${c.category == '수입'}">
														<span style="color:green">+${c.price}</span>
													</c:if>
													<c:if test="${c.category == '지출'}">
														<span style="color:orange">-${c.price}</span>
													</c:if>
												</div>
											</c:if>
										</a>
									</c:forEach>
								</td>
							</c:when>
							
							<c:when test="${day < 1}">
								<td style="color:gray">${preEndDate + day}</td>
							</c:when>
							
							<c:otherwise>
								<td style="color:gray">${day - lastDate}</td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
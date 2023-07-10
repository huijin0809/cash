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
		<h3>${memberId}님의 ${fn:substring(cashbookDate,0,4)}년 ${fn:substring(cashbookDate,5,7)}월 ${fn:substring(cashbookDate,8,10)}일 가계부</h3>
		<div style="float:left;">
			<form action="${pageContext.request.contextPath}/calendarOne" method="get">
				<input type="hidden" name="memberId" value="${memberId}">
				<input type="hidden" name="cashbookDate" value="${cashbookDate}">
				<select name="category" onchange="this.form.submit()">
					<option value="" <c:if test="${category == ''}"> selected </c:if>>전체</option>
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
		<form action="${pageContext.request.contextPath}/removeCashbook" method="post">
		<input type="hidden" name="cashbookDate" value="${cashbookDate}">
			<table class="table">
				<tr>
					<th>
						<input type="checkbox" id="chkAll">
					</th>
					<th>category</th>
					<th>price</th>
					<th>memo</th>
					<th>updatedate</th>
					<th>createdate</th>
					<th>수정</th>
				</tr>
				<c:forEach var="c" items="${list}">
					<tr>
						<td>
							<input type="checkbox" name="cashbookNo" value="${c.cashbookNo}">
						</td>
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
						<td>
							<a href="${pageContext.request.contextPath}/modifyCashbook?cashbookNo=${c.cashbookNo}" class="btn btn-outline-dark btn-sm">
								수정
							</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<button type="submit" id="delBtn" class="btn btn-danger btn-sm">삭제</button>
		</form>
		
		<!-- 페이지 네비게이션 -->
		<div>
			<nav>
            	<ul class="pagination justify-content-center">
            		<c:if test="${beginPage != 1}">
            			<li class="page-item">
            				<a href="${pageContext.request.contextPath}/calendarOne?currentPage=${beginPage - 1}&rowPerPage=${rowPerPage}&cashbookDate=${cashbookDate}&category=${category}&orderBy=${orderBy}" class="page-link">
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
								<a href="${pageContext.request.contextPath}/calendarOne?currentPage=${i}&rowPerPage=${rowPerPage}&cashbookDate=${cashbookDate}&category=${category}&orderBy=${orderBy}" class="page-link">
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
            				<a href="${pageContext.request.contextPath}/calendarOne?currentPage=${endPage + 1}&rowPerPage=${rowPerPage}&cashbookDate=${cashbookDate}&category=${category}&orderBy=${orderBy}" class="page-link">
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
		
		// 체크박스 전체선택/해제
		let chkAll = $('#chkAll'); // 전체선택박스 버튼
		let chkList = $('input[name=cashbookNo]'); // 체크박스 버튼
		let total = chkList.length; // 체크박스 전체 수
		// console.log(total + "<- total");
		let checked = 0; // 선택된 체크박스 수
		
		chkAll.click(function() { // 전체선택박스 버튼 클릭시
			if(chkAll.is(":checked")) { // is(":checked") -> 체크되어있으면 true 반환
				chkList.prop("checked", true); // prop("checked") -> 마찬가지로 체크되어있으면 true 반환 // 대신 prop는 값을 바꿀 수도 있다
			} else {
				chkList.prop("checked", false);
			}
			checked = chkList.filter(":checked").length; // filter(":checked") -> 체크되어있는 체크박스만 필터링해준다
			// console.log(checked + "<- checked");
		});

		chkList.click(function() { // 체크박스 버튼 클릭시
			checked = chkList.filter(":checked").length;
			if(total == checked) { // 선택된 체크박스 수가 체크박스 전체 수와 같다면
				chkAll.prop("checked", true); // 전체선택박스도 체크
			} else {
				chkAll.prop("checked", false);
			}
			// console.log(checked + "<- checked");
		});
		
		// 삭제 버튼 유효성 검사
		$('#delBtn').click(function(event) {
			if(checked == 0) { // 체크박스가 하나도 선택되지 않았을 경우
				alert("삭제할 내역을 선택해주세요");
				event.preventDefault(); // form 제출 막기
				return;
			}
			let result = confirm('정말 삭제하시겠습니까?'); // 확인(true) or 취소(false) 반환
			if(result == false) {
				event.preventDefault(); // form 제출 막기
				return;
			}
		});
	</script>
</body>
</html>
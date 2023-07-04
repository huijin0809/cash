<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "cash.vo.*" %>
<%
	Member member = (Member)request.getAttribute("member");
	String memberId = member.getMemberId();
	String memberPw = member.getMemberPw();
	String updatedate = member.getUpdatedate();
	String createdate = member.getCreatedate();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyMember.jsp</title>
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
		<h1>회원정보 수정</h1>
		<h6>현재 비밀번호 수정만 가능합니다</h6>
		<form action="${pageContext.request.contextPath}/modifyMember" method="post">
			<table class="table">
				<tr>
					<th>아이디</th>
					<td><%=memberId%></td> <!-- 수정 불가 -->
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="memberPw"></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" name="memberPw_2">
						<span id="pwCheckResult"></span>
					</td>
				</tr>
				<tr>
					<th>마지막 수정일</th>
					<td><%=updatedate%></td> <!-- 수정 불가 -->
				</tr>
				<tr>
					<th>최초 가입일</th>
					<td><%=createdate%></td> <!-- 수정 불가 -->
				</tr>
			</table> <br>
			<a href="${pageContext.request.contextPath}/memberOne" class="btn btn-outline-dark btn-sm">뒤로</a>
			<button type="submit" class="btn btn-dark btn-sm">수정</button>
		</form>
	</div>
</body>
</html>
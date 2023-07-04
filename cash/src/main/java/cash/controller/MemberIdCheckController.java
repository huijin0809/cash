package cash.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cash.model.*;
import com.google.gson.Gson;

@WebServlet("/memberIdCheck")
public class MemberIdCheckController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사 코드
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath() + "/cashbook");
			return;
		}
		
		response.setContentType("application/json"); // json방식으로 인코딩
		PrintWriter out = response.getWriter(); // 응답을 출력하기 위한 PrintWriter 객체 생성
		
		// request 값 저장
		String inputId = request.getParameter("inputId");
		// 모델값 구하기 // id 체크 DAO 호출
		MemberDao memberDao = new MemberDao();
		int result = memberDao.memberIdCheck(inputId);
		System.out.println("아이디 중복 검사 결과 : " + result);
		
		// JSON 문자열로 변환하기
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		out.print(jsonStr);
	}

}

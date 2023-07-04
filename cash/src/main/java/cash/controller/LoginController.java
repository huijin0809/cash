package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.vo.*;
import cash.model.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	// get 방식으로 요청시..
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사 코드
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath() + "/cashbook");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
	}
	
	// post 방식으로 요청시..
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 값 받기
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		// 객체에 값 저장
		Member member = new Member(memberId, memberPw, null, null); // setter 대신에 이렇게도 가능
		
		// 모델값 구하기 (DAO 메소드 호출)
		MemberDao memberDao = new MemberDao();
		Member loginMember = memberDao.selectMemberById(member);
		
		if(loginMember == null) { // 로그인 실패시
			System.out.println("로그인 실패");
			response.sendRedirect(request.getContextPath() + "/login?success=loginFalse");
			return;
		}
		
		// 로그인 성공시 : session에 정보 저장
		HttpSession session = request.getSession(); // 현재 jsp가 아니기 때문에 세션 사용시에는 이렇게 작성해야함
		session.setAttribute("loginMember", loginMember);
		System.out.println("로그인 성공");
		response.sendRedirect(request.getContextPath() + "/cashbook");
	}

}

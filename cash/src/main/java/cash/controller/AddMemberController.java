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

@WebServlet("/addMember")
public class AddMemberController extends HttpServlet {
	
	// addMember.jsp 회원가입폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 (session이 null이 아닐때)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath() + "/cashbook");
			return;
		}
		
		// jsp 페이지로 포어드(디스패치)
		request.getRequestDispatcher("WEB-INF/view/addMember.jsp").forward(request, response);
	}

	// 회원가입 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 (session이 null이 아닐때)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath() + "/cashbook");
			return;
		}
		
		// request.getParameter()
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		System.out.println("paramMemberId : " + memberId + "paramMemberPw : " + memberPw);
		Member member = new Member(memberId, memberPw, null, null);
		
		// 회원가입 DAO 호출
		MemberDao memberDao = new MemberDao();
		int row = memberDao.insertMember(member);
		if(row == 0) { // 회원가입 실패
			// addMember.jsp view를 이동하는 controller를 리다이렉트
			System.out.println("회원가입 실패");
			response.sendRedirect(request.getContextPath() + "/addMember?success=false");
		} else if(row == 1) { // 회원가입 성공
			// login.jsp view를 이동하는 controller를 리다이렉트
			System.out.println("회원가입 성공");
			response.sendRedirect(request.getContextPath() + "/login?success=true"); // success 값 같이 보내기
		} else { // 그 외 에러 발생
			System.out.println("addMember error!");
		}
	}
}

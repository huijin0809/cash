package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/removeMember")
public class RemoveMemberController extends HttpServlet {
	
	// 비밀번호 입력 폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/removeMember.jsp").forward(request, response);
	}

	// 회원 탈퇴 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// 세션 정보에서 아이디값 가져오기
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String loginMemberId = loginMember.getMemberId();
		// request 값 받기
		String inputMemberPw = request.getParameter("inputMemberPw");
		
		// 모델값 구하기 (DAO 메소드 호출)
		MemberDao memberDao = new MemberDao();
		int row = memberDao.deleteMemberOne(loginMemberId, inputMemberPw);
		
		if(row == 0) { // 탈퇴 실패
			System.out.println("회원탈퇴 실패");
			response.sendRedirect(request.getContextPath() + "/removeMember");
		} else if(row == 1) { // 탈퇴 성공
			System.out.println("회원탈퇴 성공");
			session.invalidate(); // 세션값 초기화
			response.sendRedirect(request.getContextPath() + "/login");
		} else { // 그 외 에러 발생
			System.out.println("removeMember error!");
		}
	}

}

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

@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {
	
	// 회원 수정 폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		
		// 수정 전 회원정보 불러올 DAO 호출
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(loginMember.getMemberId());
		// request가 공유되니까 request안에 넣어서 공유!
		request.setAttribute("member", member);
		
		request.getRequestDispatcher("/WEB-INF/view/modifyMember.jsp").forward(request, response);
	}

	// 회원 수정 액션
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
		// request 값 받아서 객체에 저장
		String memberPw = request.getParameter("memberPw");
		Member member = new Member(null, memberPw, null, null);
		
		// 회원정보 수정 DAO 호출
		MemberDao memberDao = new MemberDao();
		int row = memberDao.updateMemberOne(loginMemberId, member);
		if(row == 0) { // 수정 실패
			System.out.println("회원정보 수정 실패");
			response.sendRedirect(request.getContextPath() + "/modifyMember?success=false");
		} else if(row == 1) { // 수정 성공
			System.out.println("회원정보 수정 성공");
			response.sendRedirect(request.getContextPath() + "/memberOne?success=true");
		} else { // 그 외 에러 발생
			System.out.println("modifyMember error!");
		}
	}

}

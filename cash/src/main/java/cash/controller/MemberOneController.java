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

@WebServlet("/memberOne")
public class MemberOneController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 후 session 값 저장
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		
		// 모델 값 구하기 (DAO의 메소드 호출)
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(loginMember.getMemberId());
		
		// 비밀번호 앞 두자리만 보여주고 나머지 가려서 출력하기
		int memberPwLength = member.getMemberPw().length(); // 비밀번호 총 길이 추출
		String memberPw_1 = member.getMemberPw().substring(0,2); // 앞 두자리 추출
		String memberPw_2 = "";
		for(int i=0; i < memberPwLength-2; i++) { // 비밀번호 나머지 뒷자리 만큼 * 생성
			memberPw_2 += "*";
		}
		String printMemberPw = memberPw_1 + memberPw_2;
		member.setMemberPw(printMemberPw);
		
		// member 출력하는 (포워딩 대상) memberOne.jsp에도 공유되어야 한다
		// request가 공유되니까 request안에 넣어서 공유!
		request.setAttribute("member", member);
		
		// memberOne.jsp 포워딩
		request.getRequestDispatcher("/WEB-INF/view/memberOne.jsp").forward(request, response);
	}

}

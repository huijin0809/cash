package cash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cash.model.*;
import cash.vo.*;

@WebServlet("/calendarOne")
public class CalendarOneController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 후 session 값 저장
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// cashbookDate 유효성 검사
		if(request.getParameter("cashbookDate") == null || request.getParameter("cashbookDate").equals("")) {
			response.sendRedirect(request.getContextPath() + "/calendar");
			return;
		}
		
		// 세션 정보에서 아이디값 가져오기
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		// request 값 받기
		String cashbookDate = request.getParameter("cashbookDate");
		String category = "";
		if(request.getParameter("category") != null) {
			category = request.getParameter("category");
		}
		String orderBy = "";
		if(request.getParameter("orderBy") != null) {
			orderBy = request.getParameter("orderBy");
		}
		
		// 모델값 구하기 (DAO 호출)
		CashbookDao cashbookDao = new CashbookDao();
		List<Cashbook> list = cashbookDao.selectCashbookOne(memberId, cashbookDate, category, orderBy);
		// view에 값 넘기기 (request 속성)
		request.setAttribute("memberId", memberId);
		request.setAttribute("cashbookDate", cashbookDate);
		request.setAttribute("category", category);
		request.setAttribute("orderBy", orderBy);
		request.setAttribute("list", list);
		
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/calendarOne.jsp").forward(request, response);
	}

}

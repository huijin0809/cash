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
		// 페이징에 필요한 변수값 가져오기
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int rowPerPage = 5;
		if(request.getParameter("rowPerPage") != null) {
			rowPerPage = Integer.parseInt(request.getParameter("rowPerPage"));
		}
		int beginRow = (currentPage - 1) * rowPerPage;
				
		// list 출력 DAO 호출
		CashbookDao cashbookDao = new CashbookDao();
		List<Cashbook> list = cashbookDao.selectCashbookListByDate(memberId, cashbookDate, category, orderBy, beginRow, rowPerPage);
		
		// 페이징 알고리즘
		int pagePerPage = 5;
		int beginPage = (((currentPage - 1) / pagePerPage) * pagePerPage) + 1;
		int endPage = beginPage + (pagePerPage - 1);
		int totalRow = cashbookDao.selectCashbookCnt(memberId, cashbookDate, category); // cnt DAO 호출
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage = lastPage + 1;
		}
		if(endPage > lastPage) {
			endPage = lastPage;
		}
				
		// view에 값 넘기기 (request 속성)
		request.setAttribute("memberId", memberId);
		request.setAttribute("cashbookDate", cashbookDate);
		request.setAttribute("category", category);
		request.setAttribute("orderBy", orderBy);
		request.setAttribute("list", list);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("lastPage", lastPage);
		
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/calendarOne.jsp").forward(request, response);
	}

}

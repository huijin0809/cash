package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import cash.vo.*;
import cash.model.*;

@WebServlet("/cashbookListByTag")
public class CashbookListController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// word 유효성 검사
		if(request.getParameter("word") == null || request.getParameter("word").equals("")) {
			response.sendRedirect(request.getContextPath() + "/calendar");
			return;
		}
		String word = request.getParameter("word");
				
		// beginDate, endDate 유효성 검사
		String beginDate = "";
		String endDate = "";
		if(request.getParameter("beginDate") != null) {
			beginDate = request.getParameter("beginDate");
		}
		if(request.getParameter("endDate") != null) {
			endDate = request.getParameter("endDate");
		}
		
		// 세션 정보에서 아이디값 가져오기
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		
		// System.out.println(memberId);
		// System.out.println(word);
		System.out.println(beginDate);
		System.out.println(endDate);
		
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
		List<Cashbook> list = cashbookDao.selectCashbookListByTag(memberId, word, beginDate, endDate, beginRow, rowPerPage);
		
		// 페이징 알고리즘
		int pagePerPage = 5;
		int beginPage = (((currentPage - 1) / pagePerPage) * pagePerPage) + 1;
		int endPage = beginPage + (pagePerPage - 1);
		int totalRow = cashbookDao.selectCashbookCntByTag(memberId, word); // cnt DAO 호출
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage = lastPage + 1;
		}
		if(endPage > lastPage) {
			endPage = lastPage;
		}
		
		// view로 값 보내기
		request.setAttribute("memberId", memberId);
		request.setAttribute("word", word);
		request.setAttribute("beginDate", beginDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("list", list);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("lastPage", lastPage);
		
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
	}
}

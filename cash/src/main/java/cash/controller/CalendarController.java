package cash.controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cash.model.*;
import cash.vo.*;

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 후 session 값 저장
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// 세션 정보에서 아이디값 가져오기
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		
		// 모델값 구하기
		// 1. view에 넘겨줄 달력정보 구하기
		Calendar firstDay = Calendar.getInstance();
		// 오늘 날짜로 기본셋팅
		int targetYear = firstDay.get(Calendar.YEAR); 
		int targetMonth = firstDay.get(Calendar.MONTH);
		firstDay.set(Calendar.DATE, 1); // 해당 월의 1일
		// 출력하고자하는 년도와 월이 매개값으로 넘어왔다면
		if(request.getParameter("targetYear") != null && request.getParameter("targetMonth") != null) {
			// 사용자가 요청한 날짜로 셋팅
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			firstDay.set(Calendar.YEAR, targetYear);
			firstDay.set(Calendar.MONTH, targetMonth);
			// API에서 자동으로 Calendar.MONTH값이 12이면 YEAR에 +1, -1이면 YEAR에 -1이 된다
			// 바뀐 년도와 월 정보를 다시 셋팅
			targetYear = firstDay.get(Calendar.YEAR);
			targetMonth = firstDay.get(Calendar.MONTH);
		}
		System.out.println(targetYear + "년 <- targetYear");
		System.out.println(targetMonth + "월 <- targetMonth");
		// 해당 월의 1일의 요일 구하기
		int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK) - 1; // 1일의 요일에 따른 달력 시작 공백 (일요일:1 ~ 토요일:6)
		System.out.println(beginBlank + "개 <- beginBlank");
		// 해당 월의 마지막 날짜 구하기
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);
		System.out.println(lastDate + "일 <- lastDate");
		// 해당 월의 마지막 날짜에 따른 달력 끝 공백 (전체 출력 셀의 개수가 7로 나누어떨어져야 한다)
		int endBlank = 0;
		if((beginBlank + lastDate) % 7 != 0) {
			endBlank = 7 - ((beginBlank + lastDate) % 7);
		}
		int totalCell = beginBlank + lastDate + endBlank;
		System.out.println(totalCell + "개 <- totalCell");
		System.out.println(endBlank + "개 <- endBlank");
		// 전월 마지막 날짜 구하기
		Calendar preDate = Calendar.getInstance();
		preDate.set(Calendar.YEAR, targetYear);
		preDate.set(Calendar.MONTH, targetMonth - 1);
		int preEndDate = preDate.getActualMaximum(Calendar.DATE);
		
		
		// 2. 타겟 월의 수입/지출 데이터 (DAO 호출)
		CashbookDao cashbookDao = new CashbookDao();
		List<Cashbook> list = cashbookDao.selectCashbookListByMonth(memberId, targetYear, targetMonth + 1);
		// java와 mariadb 의 월 시작이 다르므로 +1
		// System.out.println(list);
		
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> htList = hashtagDao.selectWordCountByMonth(memberId, targetYear, targetMonth + 1);
		
		// view에 값 넘기기 (request 속성)
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("beginBlank", beginBlank);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("totalCell", totalCell);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("preEndDate", preEndDate);
		request.setAttribute("memberId", memberId);
		request.setAttribute("list", list);
		request.setAttribute("htList", htList);
		
		// 달력을 출력하는 view
		request.getRequestDispatcher("/WEB-INF/view/calendar.jsp").forward(request, response);
	}
}

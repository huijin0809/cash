package cash.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cash.model.*;

@WebServlet("/removeCashbook")
public class RemoveCashbookController extends HttpServlet {

	// 삭제 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// 요청값 유효성 검사
		if(request.getParameter("cashbookDate") == null) {
			response.sendRedirect(request.getContextPath() + "/calendar");
			return;
		}
		String cashbookDate = request.getParameter("cashbookDate");
		if(request.getParameterValues("cashbookNo") == null) {
			response.sendRedirect(request.getContextPath() + "/calendarOne?cashbookDate" + cashbookDate);
			return;
		}
		
		// request 값 받기 (cashbookNo는 다중선택이므로 배열로 받는다)
		String[] chkCashbookNo = request.getParameterValues("cashbookNo");
		// int 타입 배열로 바꾸기
		int[] intChkCashbookNo = null;
		if(chkCashbookNo != null) {
			intChkCashbookNo = new int[chkCashbookNo.length];
			for(int i=0; i<intChkCashbookNo.length; i++) {
				intChkCashbookNo[i] = Integer.parseInt(chkCashbookNo[i]);
				System.out.println(intChkCashbookNo[i] + " <- RemoveCashbookController intChkCashbookNo");
			}
		}
		
		// delete DAO 호출
		CashbookDao cashbookDao = new CashbookDao();
		String msg = null;
		int row = cashbookDao.deleteCashbook(intChkCashbookNo);
		if(row == 0) { // 삭제 실패
			System.out.println("cashbook 삭제 실패");
			msg = URLEncoder.encode("내역이 삭제되지 않았습니다 다시 시도해주세요", "utf-8");
			response.sendRedirect(request.getContextPath() + "/calendarOne?cashbookDate" + cashbookDate + "&msg=" + msg);
		} else if(row != 0) { // 삭제 성공
			System.out.println("cashbook 삭제 성공");
			msg = URLEncoder.encode("내역이 삭제되었습니다", "utf-8");
			response.sendRedirect(request.getContextPath() + "/calendarOne?cashbookDate=" + cashbookDate + "&msg=" + msg);
		} else { // 그 외 에러 발생
			System.out.println("removeCashbook error!");
		}
	}
}

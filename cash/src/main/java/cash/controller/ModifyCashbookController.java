package cash.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cash.vo.*;
import cash.model.*;


@WebServlet("/modifyCashbook")
public class ModifyCashbookController extends HttpServlet {
	
	// 가계부 수정 폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		// 요청값 유효성 검사
		if(request.getParameter("cashbookNo") == null) {
			response.sendRedirect(request.getContextPath() + "/calendar");
			return;
		}
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		// System.out.println(cashbookNo);
		
		// 수정 전 가계부 정보 불러올 DAO 호출
		CashbookDao cashbookDao = new CashbookDao();
		Cashbook cashbook = cashbookDao.selectCashbookOne(cashbookNo);
		System.out.println(cashbook.getCashbookDate());
		System.out.println(cashbook.getCategory());
		System.out.println(cashbook.getPrice());
		System.out.println(cashbook.getMemo());
		// request 속성에 담아서 view로 보내기
		request.setAttribute("cashbook", cashbook);
		
		request.getRequestDispatcher("/WEB-INF/view/modifyCashbook.jsp").forward(request, response);
	}

	// 가계부 수정 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨지지 않게 인코딩
		request.setCharacterEncoding("utf-8");
		
		// request 매개값 받아서 객체에 저장
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		String category = request.getParameter("category");
		String cashbookDate = request.getParameter("cashbookDate");
		int price = Integer.parseInt(request.getParameter("price"));
		String memo = request.getParameter("memo");
		Cashbook cashbook = new Cashbook(cashbookNo, null, category, cashbookDate, price, memo, null, null);
		
		// 모델값 구하기
		// 기존 cashbook 수정 -> 기존 hashtag 삭제 -> 새롭게 hashtag 입력
		CashbookDao cashbookDao = new CashbookDao();
		HashtagDao hashtagDao = new HashtagDao();
		String msg = null;
		
		// 1) 기존 cashbook 수정
		int cashbookUpdateRow = cashbookDao.updateCashbook(cashbook);
		if(cashbookUpdateRow == 0) {
			System.out.println(cashbookUpdateRow + " <- cashbook 수정 실패!");
			msg = URLEncoder.encode("정상적으로 수정되지 않았습니다 다시 시도해주세요", "utf-8");
			response.sendRedirect(request.getContextPath() + "/modifyCashbook?cashbookNo=" + cashbookNo + "&msg=" + msg);
			return;
		}
		System.out.println("1. cashbook 수정 성공!");
		
		// 2) 기존 hashtag 삭제
		int hashtagDeleteRow = hashtagDao.deleteHashtag(cashbookNo);
		System.out.println("2. 기존 hashtag " + hashtagDeleteRow + "개 삭제 성공!");
		
		// 3) 새롭게 hashtag 입력
		// 해시태그 추출 알고리즘
		String memo2 = memo.replace("#", " #"); // 해시태그 공백으로 띄우기 // "#구디#아카데미" -> "#구디 #아카데미"
		int insertHashtagRow = 0;
		for(String ht : memo2.split(" ")) { // 공백을 기준으로 해시태그 분류하기
			if (ht.startsWith("#")) { // memo2 중에서 # 시작하는 해시태그만 선택
				String ht2 = ht.replace("#", ""); // # 없애기
				if(ht2.length() > 0) {
					Hashtag hashtag = new Hashtag();
					hashtag.setCashbookno(cashbookNo);
					hashtag.setWord(ht2);
					// DAO 호출
					insertHashtagRow += hashtagDao.insertHashtag(hashtag);
				}
			}
		}
		if(insertHashtagRow != 0) {
			System.out.println("3. 새로운 hashtag " + insertHashtagRow + "개 입력 성공!");
		}
		
		// 모든 작업 완료 후 상세 페이지로 이동
		msg = URLEncoder.encode("내역이 정상적으로 수정되었습니다", "utf-8");
		response.sendRedirect(request.getContextPath() + "/calendarOne?cashbookDate=" + cashbookDate + "&msg=" + msg);
	}

}

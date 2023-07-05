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

@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	
	// 입력 폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사 후 session 값 저장
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// 세션 정보에서 아이디값 가져오기
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		
		// request 매개값 // cashbookDate 값이 있다면 (calendarOne에서 넘어왔을시)
		String cashbookDate = "";
		if(request.getParameter("cashbookDate") != null) {
			cashbookDate = request.getParameter("cashbookDate");
		}
		
		// view에 값 넘기기 (request 속성)
		request.setAttribute("memberId", memberId);
		request.setAttribute("cashbookDate", cashbookDate);
		// 나머지 데이터는 입력폼에서 사용자가 직접 입력
		
		// 포워딩
		request.getRequestDispatcher("/WEB-INF/view/addCashbook.jsp").forward(request, response);
	}

	// 입력 액션 cashbook (+ hashtag)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨지지 않게 인코딩
		request.setCharacterEncoding("utf-8");
		
		// request 매개값 받기
		String memberId = request.getParameter("memberId");
		String category = request.getParameter("category");
		String cashbookDate = request.getParameter("cashbookDate");
		int price = Integer.parseInt(request.getParameter("price"));
		String memo = request.getParameter("memo");
		// 객체에 저장
		Cashbook cashbook = new Cashbook(0, memberId, category, cashbookDate, price, memo, null, null);
		
		// 모델값 구하기
		String msg = null;
		// 1. Cashbook 입력 DAO 호출
		CashbookDao cashbookDao = new CashbookDao();
		int cashbookNo = cashbookDao.insertCashbook(cashbook); // insert 후 키값 저장
		if(cashbookNo == 0) { // 입력 실패시
			System.out.println("cashbook insert 실패! <- AddCashbookController");
			msg = URLEncoder.encode("내역이 추가되지 않았습니다 다시 시도해주세요", "utf-8");
			response.sendRedirect(request.getContextPath() + "/addCashbook?cashbookDate=" + cashbookDate + "&msg=" + msg);
			return;
		}
		System.out.println("cashbook insert 성공! <- AddCashbookController");
		
		// 2. 성공시 -> 해시태그가 있다면! -> 해시태그 추출 -> 해시태그 입력 (반복문)
		// Hashtag 입력 DAO 호출
		HashtagDao hashtagDao = new HashtagDao();
		
		// 해시태그 추출 알고리즘
		String memo2 = memo.replace("#", " #"); // 해시태그 공백으로 띄우기 // "#구디#아카데미" -> "#구디 #아카데미"
		int row = 0;
		for(String ht : memo2.split(" ")) { // 공백을 기준으로 해시태그 분류하기
			if (ht.startsWith("#")) { // memo2 중에서 # 시작하는 해시태그만 선택
				String ht2 = ht.replace("#", ""); // # 없애기
				if(ht2.length() > 0) {
					Hashtag hashtag = new Hashtag();
					hashtag.setCashbookno(cashbookNo);
					hashtag.setWord(ht2);
					// DAO 호출
					row += hashtagDao.insertHashtag(hashtag);
				}
			}
		}
		if(row != 0) {
			System.out.printf("해시태그 %d개 입력 성공! <- AddCashbookController", row);
		}
		
		// 모든 작업 완료 후 상세 페이지로 이동
		msg = URLEncoder.encode("내역이 추가되었습니다", "utf-8");
		response.sendRedirect(request.getContextPath() + "/calendarOne?cashbookDate=" + cashbookDate + "&msg=" + msg);
	}

}

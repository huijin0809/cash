package cash.model;

import java.util.*;
import java.sql.*;
import cash.vo.*;

public class CashbookDao {
	// 타겟 월의 수입/지출 데이터 리스트
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		Cashbook c = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT category, price, cashbook_date cashbookDate FROM cashbook WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? ORDER BY createdate ASC";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			rs = stmt.executeQuery();
			// System.out.println(stmt);
			while(rs.next()) {
				c = new Cashbook();
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 해당 해시태그의 cashbook 리스트 조회
	public List<Cashbook> selectCashbookListByTag(String memberId, String word, String beginDate, String endDate, int beginRow, int rowPerPage) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		Cashbook c = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT c.cashbook_no cashbookNo, c.member_id memberId, c.category category, c.cashbook_date cashbookDate, c.price price, c.memo memo, c.updatedate updatedate, c.createdate createdate, c.cashbook_date cashbookDate FROM cashbook c INNER JOIN hashtag h ON c.cashbook_no = h.cashbook_no WHERE c.member_id = ? AND h.word = ?";
		// 매개값에 따라 다른 동적쿼리 작성
		if(!beginDate.equals("") && endDate.equals("")) { // 시작날짜만 입력
			sql += " AND c.cashbook_date > ?";
		} else if(beginDate.equals("") && !endDate.equals("")) { // 끝날짜만 입력
			sql += " AND c.cashbook_date < ?";
		} else if(!beginDate.equals("") && !endDate.equals("")) { // 둘다 입력
			sql += " AND c.cashbook_date BETWEEN ? AND ?";
		}
		// 정렬 부분 추가
		sql += " ORDER BY c.cashbook_date DESC LIMIT ?, ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, word);
			
			int parameterIndex = 3; // 물음표 인덱스
			// 동적으로 인덱스 셋팅
			if(!beginDate.equals("") && endDate.equals("")) { // 시작날짜만 입력
				stmt.setString(parameterIndex++, beginDate);
			} else if(beginDate.equals("") && !endDate.equals("")) { // 끝날짜만 입력
				stmt.setString(parameterIndex++, endDate);
			} else if(!beginDate.equals("") && !endDate.equals("")) { // 둘다 입력
				stmt.setString(parameterIndex++, beginDate);
				stmt.setString(parameterIndex++, endDate);
			}
			stmt.setInt(parameterIndex++, beginRow);
			stmt.setInt(parameterIndex++, rowPerPage);
			
			rs = stmt.executeQuery();
			System.out.println(stmt);
			
			while(rs.next()) {
				c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setMemberId(rs.getString("memberId"));
				c.setCategory(rs.getString("category"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setUpdatedate(rs.getString("updatedate"));
				c.setCreatedate(rs.getString("createdate"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 해당 해시태그의 cashbook totalRow
	public int selectCashbookCntByTag(String memberId, String word) {
		int totalRow = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT COUNT(*) FROM cashbook c INNER JOIN hashtag h ON c.cashbook_no = h.cashbook_no WHERE c.member_id = ? AND h.word = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, word);
			rs = stmt.executeQuery();
			// System.out.println(stmt);
			if(rs.next()) {
				totalRow = rs.getInt(1);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return totalRow;
	}
	
	// 해당 날짜의 지출/수입 리스트
	public List<Cashbook> selectCashbookListByDate(String memberId, String cashbookDate, String category, String orderBy, int beginRow, int rowPerPage) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		Cashbook c = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT cashbook_no cashbookNo, category, cashbook_date cashbookDate, price, memo, updatedate, createdate FROM cashbook WHERE member_id = ? AND cashbook_date = ?";
		/*
 			1) 수입/지출 카테고리 선택
 				AND category = ?
 			2) 정렬 선택
 				ORDER BY createdate DESC LIMIT ?, ? -- 최신순
 				ORDER BY createdate ASC LIMIT ?, ? -- 오래된순
 				ORDER BY price DESC LIMIT ?, ? -- 높은금액순
 				ORDER BY price ASC LIMIT ?, ? -- 낮은금액순
		*/
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			
			// 1) 수입/지출 카테고리 선택
			if(!category.equals("")) {
				sql += " AND category = ?";
			}
			// 2) 정렬 선택 조회
			switch(orderBy) {
				case "latestDate": // 최신순
					sql += " ORDER BY createdate DESC LIMIT ?, ?";
					break;
				case "oldestDate": // 오래된순
					sql += " ORDER BY createdate ASC LIMIT ?, ?";
					break;
				case "highPrice": // 높은금액순
					sql += " ORDER BY price DESC LIMIT ?, ?";
					break;
				case "lowPrice": // 낮은금액순
					sql += " ORDER BY price ASC LIMIT ?, ?";
					break;
				default:
					// 기본적으로 최신순으로 정렬
					sql += " ORDER BY createdate DESC LIMIT ?, ?";
					break;
			}
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, cashbookDate);
			
			int parameterIndex = 3; // 물음표 인덱스
			// 1)
			if(!category.equals("")) {
				stmt.setString(parameterIndex++, category);
			}
			// 2)
			stmt.setInt(parameterIndex++, beginRow);
			stmt.setInt(parameterIndex++, rowPerPage);
			
			rs = stmt.executeQuery();
			// System.out.println(stmt);
			while(rs.next()) {
				c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setUpdatedate(rs.getString("updatedate"));
				c.setCreatedate(rs.getString("createdate"));
				list.add(c);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 해당 날짜의 cashbook totalRow
	public int selectCashbookCnt(String memberId, String cashbookDate, String category) {
		int totalRow = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT COUNT(*) FROM cashbook WHERE member_id = ? AND cashbook_date = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			
			// 수입/지출 카테고리 선택시
			if(!category.equals("")) {
				sql += " AND category = ?";
			}
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, cashbookDate);
			if(!category.equals("")) {
				stmt.setString(3, category);
			}
			
			rs = stmt.executeQuery();
			// System.out.println(stmt);
			if(rs.next()) {
				totalRow = rs.getInt(1);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return totalRow;
	}
	
	// cashbook 1개 상세보기
	public Cashbook selectCashbookOne(int cashbookNo) {
		Cashbook cashbook = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT cashbook_no cashbookNo, category, cashbook_date cashbookDate, price, memo, updatedate, createdate FROM cashbook WHERE cashbook_no = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			rs = stmt.executeQuery();
			// System.out.println(stmt);
			if(rs.next()) {
				cashbook = new Cashbook();
				cashbook.setCashbookNo(rs.getInt("cashbookNo"));
				cashbook.setCategory(rs.getString("category"));
				cashbook.setCashbookDate(rs.getString("cashbookDate"));
				cashbook.setPrice(rs.getInt("price"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setUpdatedate(rs.getString("updatedate"));
				cashbook.setCreatedate(rs.getString("createdate"));
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cashbook;
	}
	
	
	// 가계부 입력 -> 반환값 : cashbook_no 키값
	public int insertCashbook(Cashbook cashbook) {
		int cashbookNo = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; // 입력 후 생성된 키값을 반환받기 위해 필요
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "INSERT INTO cashbook(member_id, category, cashbook_date, price, memo, updatedate, createdate) VALUES(?,?,?,?,?,NOW(),NOW())";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // 키값 같이 받아오기
			stmt.setString(1, cashbook.getMemberId());
			stmt.setString(2, cashbook.getCategory());
			stmt.setString(3, cashbook.getCashbookDate());
			stmt.setInt(4, cashbook.getPrice());
			stmt.setString(5, cashbook.getMemo());
			
			int row = stmt.executeUpdate(); // insert 성공 유무 row 값
			rs = stmt.getGeneratedKeys(); // 키값 같이 받아오기
			// System.out.println(stmt);
			if(rs.next()) {
				cashbookNo = rs.getInt(1); // 키값 (cashbookNo) 저장
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cashbookNo;
	}
	
	// 가계부 수정
	public int updateCashbook(Cashbook cashbook) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "UPDATE cashbook SET category = ?, cashbook_date = ?, price = ?, memo = ?, updatedate = NOW() WHERE cashbook_no = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cashbook.getCategory());
			stmt.setString(2, cashbook.getCashbookDate());
			stmt.setInt(3, cashbook.getPrice());
			stmt.setString(4, cashbook.getMemo());
			stmt.setInt(5, cashbook.getCashbookNo());
			row = stmt.executeUpdate();
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return row;
	}
	
	// 가계부 삭제
	public int deleteCashbook(int[] intChkCashbookNo) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			
			String sql = "DELETE FROM cashbook WHERE cashbook_no IN(?"; // 기본쿼리는 물음표 1개를 넣고 시작
			// 쿼리에 물음표가 몇개 들어갈지 셋팅
			for(int i=1; i<intChkCashbookNo.length; i++) {
				sql += ",?";
			}
			sql += ")"; // length만큼 물음표가 출력되고 마지막에 괄호 닫기
			stmt = conn.prepareStatement(sql);
			// 물음표에 들어갈 값 넣기
			for(int i=0; i<intChkCashbookNo.length; i++) {
				stmt.setInt(i+1, intChkCashbookNo[i]);
				// 첫번째 물음표(i+1)에 배열의 값(인덱스 0부터) 순서대로 넣어주기
			}
			row = stmt.executeUpdate();
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return row;
	}
}

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
	
	// 해당 날짜의 지출/수입 상세보기
	public List<Cashbook> selectCashbookOne(String memberId, String cashbookDate, String category, String orderBy) {
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
 				ORDER BY createdate DESC -- 최신순
 				ORDER BY createdate ASC -- 오래된순
 				ORDER BY price DESC -- 높은금액순
 				ORDER BY price ASC -- 낮은금액순
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
					sql += " ORDER BY createdate DESC";
					break;
				case "oldestDate": // 오래된순
					sql += " ORDER BY createdate ASC";
					break;
				case "highPrice": // 높은금액순
					sql += " ORDER BY price DESC";
					break;
				case "lowPrice": // 낮은금액순
					sql += " ORDER BY price ASC";
					break;
				default:
					// 기본적으로 최신순으로 정렬
					sql += " ORDER BY createdate DESC";
					break;
			}
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, cashbookDate);
			if(!category.equals("")) {
				stmt.setString(3, category);
			}
			
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
			stmt.setString(1, cashbook.getMember_id());
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
}

package cash.model;

import java.sql.*;
import cash.vo.*;

public class MemberDao {
	// 로그인
	public Member selectMemberById(Member paramMember) {
		Member returnMember = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT member_id memberId, member_pw memberPw FROM member WHERE member_id=? AND member_pw = PASSWORD(?)";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("memberId"));
				returnMember.setMemberPw(rs.getString("memberPw"));
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
		
		return returnMember;
	}
	
	// 회원가입
	public int insertMember(Member paramMember) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String insertSql = "INSERT INTO member VALUES(?,PASSWORD(?),NOW(),NOW())";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(insertSql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
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
	
	// 아이디 중복 검사
	public int memberIdCheck(String inputMemberId) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inputMemberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
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
		
		return row;
	}
	
	// 회원 상세정보 조회
	public Member selectMemberOne(String memberId) {
		Member returnMember = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "SELECT member_id memberId, member_pw memberPw, updatedate, createdate FROM member WHERE member_id=?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("memberId"));
				returnMember.setMemberPw(rs.getString("memberPw"));
				returnMember.setUpdatedate(rs.getString("updatedate"));
				returnMember.setCreatedate(rs.getString("createdate"));
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
		
		return returnMember;
	}
	
	// 회원정보 수정
	public int updateMemberOne(String loginMemberId, Member member) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "UPDATE member SET member_pw = PASSWORD(?), updatedate = NOW() WHERE member_id = ?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberPw());
			stmt.setString(2, loginMemberId);
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
	
	// 회원 탈퇴
	public int deleteMemberOne(String loginMemberId, String inputMemberPw) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbid = "root";
		String dbpw = "java1234";
		String sql = "DELETE FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbid,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, loginMemberId);
			stmt.setString(2, inputMemberPw);
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

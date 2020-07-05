package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DTO.memberDTO;

public class memberDAO {
	private memberDAO() {

	}

	private static memberDAO instance = new memberDAO();

	public static memberDAO getInstance() {
		return instance;
	}

	//
	public int Insert(memberDTO member) throws SQLException {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		// ResultSet rs = null;

			// 드라이버 로딩
			con = DBManager.getConnection();

			// insert
			String sql = "insert into member values(seq_mno.nextVal,?,?,?)";
			String sql2 = "COMMIT";

			// 쿼리문 작성
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, member.getMname());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddr());

			// 실행
			count = pstmt.executeUpdate();
			System.out.println(count);	// 실행되면 1을 반환, 실패하면 0을 반환
			if (count > 0) {
				System.out.println("Insert 성공");
				pstmt = con.prepareStatement(sql2);
				pstmt.executeQuery();
			} else {
				System.out.println("Insert 실패");
			}
			DBManager.close(con, pstmt);
		
		return count;

	}
	
	public int update(memberDTO member) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		// ResultSet rs = null;

		try {

			// 드라이버 로딩
			con = DBManager.getConnection();

			// update
			String sql = "UPDATE member SET mname=?, phone=?, addr=? WHERE mno =?";
			String sql2 = "COMMIT";

			// 쿼리문 작성
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, member.getMname());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddr());
			pstmt.setInt(4, member.getMno());

			// 실행
			count = pstmt.executeUpdate();
			System.out.println(count);	// 실행되면 1을 반환, 실패하면 0을 반환
			if (count > 0) {
				System.out.println("Update 성공");
				pstmt = con.prepareStatement(sql2);
				pstmt.executeQuery();
			} else {
				System.out.println("Update 실패");
			}
			DBManager.close(con, pstmt);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터를 저장하는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}
		
		return count;
	}
	
	public void delete(String mno) {
		Connection con = DBManager.getConnection();
		PreparedStatement pstmt;
		String sql = "DELETE FROM member WHERE mno = '"+mno+"'";
		String sql2 = "COMMIT";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(sql2);
			pstmt.executeQuery();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "대출 내역이 있는지 확인하세요.\n대출 내역이 없다면 관리자에게 문의하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public ArrayList<memberDTO> selectMember(String comboText, String keyword){
		
		ArrayList<memberDTO> members = new ArrayList<memberDTO>();
		Connection con;
		PreparedStatement pstmt;
		ResultSet rs;
		con = DBManager.getConnection();
		String sql;
		
		if(comboText.equals("이름"))
			sql = "SELECT m.mno, m.mname, m.phone, m.addr, COUNT(h.mno)" + 
					" FROM member m, hirelist h" + 
					" WHERE m.mno = h.mno(+)" + 
					" AND UPPER(m.mname) LIKE '%'||UPPER('"+keyword+"')||'%'" + 
					" GROUP BY m.mno, m.mname, m.phone, m.addr"
					+ " ORDER BY m.mno";
		else
			sql = "SELECT  m.mno, m.mname, m.phone, m.addr, COUNT(h.mno)" + 
					" FROM member m, hirelist h" + 
					" WHERE m.mno = h.mno(+)" + 
					" AND m.phone LIKE '%"+keyword+"'" + 
					" GROUP BY m.mno, m.mname, m.phone, m.addr"
					+ " ORDER BY m.mno";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memberDTO member = new memberDTO();
				member.setMno(rs.getInt("mno"));
				member.setMname(rs.getString("mname"));
				member.setPhone(rs.getString("phone"));
				member.setAddr(rs.getString("addr"));
				member.setBcount(rs.getInt("count(h.mno)"));
				members.add(member);
			}
			DBManager.close(con, pstmt, rs);
			return members;			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터를 불러오는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}		
		return null;
	}
	
	public memberDTO selectOne(int mno) {
		Connection con = DBManager.getConnection();
		PreparedStatement pstmt;
		ResultSet rs;
		memberDTO member = new memberDTO();
		
		String sql = "SELECT * FROM member WHERE mno = "+ mno;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member.setMno(rs.getInt("mno"));
				member.setMname(rs.getString("mname"));
				member.setPhone(rs.getString("phone"));
				member.setAddr(rs.getString("addr"));
			}
			return member;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "해당 회원정보를 불러오는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}
		return null;			
	}

}

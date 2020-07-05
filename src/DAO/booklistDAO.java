package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DTO.booklistDTO;

public class booklistDAO {
	private booklistDAO() {

	}

	private static booklistDAO instance = new booklistDAO();

	public static booklistDAO getInstance() {
		return instance;
	}

	//
	public void Insert(booklistDTO User) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		// ResultSet rs = null;

			int count = 0;

			// 드라이버 로딩
			con = DBManager.getConnection();

			// insert
			String sql = "insert into booklist(bno, title, author, loc) values(seq_bno.nextVal, ?, ?, ?)";
			String sql2 = "COMMIT";

			// 쿼리문 작성
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, User.getTitle());
			pstmt.setString(2, User.getAuthor());
			pstmt.setString(3, User.getLoc());


			// 실행
			count = pstmt.executeUpdate();
			System.out.println(count);
			if (count > 0) {
				System.out.println("Insert 성공");
				pstmt = con.prepareStatement(sql2);
				pstmt.executeQuery();
			} else {
				System.out.println("Insert 실패");
			}
			DBManager.close(con, pstmt);


	}
	
	public ArrayList<booklistDTO> selectBook(String comboText, String keyword){
		ArrayList<booklistDTO> books = new ArrayList<booklistDTO>();
		Connection con = DBManager.getConnection();
		String sql;
		
		if(comboText.equals("제목"))
			sql = "SELECT * FROM booklist WHERE UPPER(title) LIKE '%'||UPPER('"+keyword+"')||'%' ORDER BY bno";
		else
			sql = "SELECT * FROM booklist WHERE UPPER(author) LIKE '%'||UPPER('"+keyword+"')||'%' ORDER BY bno";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				booklistDTO book = new booklistDTO();
				book.setBno(rs.getInt("bno"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setLoc(rs.getString("loc"));
				book.setCon(rs.getString("con"));
				books.add(book);				
			}
			DBManager.close(con, pstmt, rs);
			return books;			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터를 불러오는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}		
		return null;
	}
	
	public void delBookList(String bno) {
		Connection con = DBManager.getConnection();
		String sql = "DELETE FROM booklist WHERE bno = "+bno;
		String sql2 = "COMMIT";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(sql2);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "대출 내역이 있는지 확인하세요.\n대출 내역이 없다면 관리자에게 문의하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
		}
		DBManager.close(con, pstmt);		
	}
	


}

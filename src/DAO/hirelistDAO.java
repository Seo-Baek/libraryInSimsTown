package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DTO.hirelistDTO;

public class hirelistDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	
	private hirelistDAO() {

	}

	private static hirelistDAO instance = new hirelistDAO();

	public static hirelistDAO getInstance() {
		return instance;
	}

	
	public void Insert(Integer bno, Integer mno) throws SQLException {

		
		// ResultSet rs = null;



			int count = 0;

			// 드라이버 로딩
			con = DBManager.getConnection();

			// insert
			String sql = "insert into hirelist(bno, mno) values(?, ?)";
			String sql2 = "COMMIT";

			// 쿼리문 작성
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, bno);
			pstmt.setInt(2, mno);


			// 실행
			count = pstmt.executeUpdate();
			System.out.println(count);
			if (count > 0) {
				System.out.println("Insert 성공");
				pstmt = con.prepareStatement(sql2);
				pstmt.executeQuery();
				JOptionPane.showMessageDialog(null, "대여되었습니다.");
				
			} else {
				System.out.println("Insert 실패");
			}
			DBManager.close(con, pstmt);


	}
	
	
	public void UpdateAll(String rentText, String condition, Integer bno) {
		con = DBManager.getConnection();
		String sql = "UPDATE booklist SET con = '"+ condition+ "' WHERE bno = " + bno;
		String sql2 = "COMMIT";
		int plag = 0;
		
			try {
					pstmt = con.prepareStatement(sql);
					plag = pstmt.executeUpdate();
				
				
				pstmt = con.prepareStatement(sql2);
				pstmt.execute();
				if(plag == 1) {
					System.out.println("Update 성공");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "데이터를 저장하는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
			}
			DBManager.close(con, pstmt);
			
		}
	
	public ArrayList<hirelistDTO> selectBook(String comboText, String keyword){
		ArrayList<hirelistDTO> hires = new ArrayList<hirelistDTO>();
		con = DBManager.getConnection();
		String sql = null;
		
		if(comboText.equals("이름"))
			sql = "SELECT * FROM v_hirelist WHERE UPPER(mname) LIKE '%'||UPPER('"+keyword+"')||'%' ORDER BY outdate";
		else
			sql = "SELECT * FROM v_hirelist WHERE phone LIKE '%"+keyword+"%' ORDER BY outdate";
		
		pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				hirelistDTO hire = new hirelistDTO();
				hire.setMname(rs.getString("mname"));
				hire.setPhone(rs.getString("phone"));
				hire.setTitle(rs.getString("title"));
				hire.setAuthor(rs.getString("author"));
				hire.setOutdate(rs.getString("outdate"));
				hire.setIndate(rs.getString("indate"));
				hire.setBno(rs.getInt("bno"));
				hires.add(hire);				
			}
			DBManager.close(con, pstmt, rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터를 불러오는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}		
		return hires;			
		
	}
	
	public void delHireList(Integer bno) {
		con = DBManager.getConnection();
		String sql = "DELETE FROM hirelist WHERE bno = "+bno;
		String sql2 = "COMMIT";
		int plag = 0;
		pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			plag = pstmt.executeUpdate();
			pstmt = con.prepareStatement(sql2);
			pstmt.executeQuery();
			if(plag == 1) {
				System.out.println("Delete 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터를 삭제하는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}
		DBManager.close(con, pstmt);		
	}
	
	public Integer[] countBook(String combobox, String keyword){
		Integer[] res = new Integer[2];
		Connection con = DBManager.getConnection();
		String sql;
		String sql2;

		if(combobox.equals("제목"))
			sql = "select count(*) allcount from booklist where title LIKE '%'||UPPER('"+keyword+"')||'%'";
		else if(combobox.equals("지은이"))
			sql = "select count(*) allcount from booklist where  author LIKE '%'||UPPER('"+keyword+"')||'%'";
		else 
			sql = "select count(*) allcount from booklist";	
		
		if(combobox.equals("제목"))
			sql2 = "select count(*) selectedcount from booklist where title LIKE '%'||UPPER('"+keyword+"')||'%' and con = 'in'";
		else if(combobox.equals("지은이"))
			sql2 = "select count(*) selectedcount from booklist where  author LIKE '%'||UPPER('"+keyword+"')||'%' and con = 'in'";
		else 
			sql2 = "select count(*) selectedcount from booklist where con = 'in'";

		PreparedStatement pstmt = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs1 = pstmt.executeQuery();
			while(rs1.next()) {
				res[0] = rs1.getInt("allcount");
			}
			pstmt = con.prepareStatement(sql2);
			rs2 = pstmt.executeQuery();
			while(rs2.next()) {
				res[1] = rs2.getInt("selectedcount");
			}
			DBManager.close(con, pstmt, rs1);
			rs2.close();		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "계산된 데이터를 불러오는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}	
		for(int k : res) {			
			System.out.println(k);
		}
		return res;
	}
	
	public int selectMember(String name, String phone) {
		int member = 0;
		Connection con = DBManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql= "SELECT mno" + 
				" FROM member" + 
				" WHERE phone = "+ phone+"'" +
				" AND UPPER(mname) = UPPER('"+name+"')" ;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			member = rs.getInt("mno");
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		DBManager.close(con, pstmt, rs);
		return member;
	}
	
	public ArrayList<hirelistDTO> selectOverdue(){
		
		ArrayList<hirelistDTO> overdue = new ArrayList<hirelistDTO>();
		con = DBManager.getConnection();
		String sql = null;
		
			sql = "SELECT * FROM v_hirelist WHERE indate <= sysdate ORDER BY outdate";
		
		pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				hirelistDTO hire = new hirelistDTO();
				hire.setMname(rs.getString("mname"));
				hire.setPhone(rs.getString("phone"));
				hire.setTitle(rs.getString("title"));
				hire.setAuthor(rs.getString("author"));
				hire.setOutdate(rs.getString("outdate"));
				hire.setIndate(rs.getString("indate"));
				hire.setBno(rs.getInt("bno"));
				overdue.add(hire);				
			}
			DBManager.close(con, pstmt, rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터를 불러오는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
		}		
		return overdue;			
		
	}
}

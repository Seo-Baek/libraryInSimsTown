package DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBManager {
	public static Connection getConnection() {

		Connection con = null;
		
		String Driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "project";
		String pwd = "1234";

		try {
			Class.forName(Driver);
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("db연결 성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Occurrence!!\nDB Connection Exception.\n\n관리자에게 문의해주세요.\n"+ "　",
					null, JOptionPane.ERROR_MESSAGE);
		}
		
		return con;
	}
	
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void close(Connection conn, Statement stmt) {
		try {
			stmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.BoardBean;


public class BoardDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//데이터 베이스의 커넥션풀을 사용하도록 설정하는 메소드

	public void getCon() {
		
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			//datasource
			con = ds.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertBoard(BoardBean bean){
		getCon();
		int ref = 0;
		int re_stop = 1;
		int re_level = 1;
		try {
			
			String refSQL = "SELECT max(ref) FROM BOARD";
			
			pstmt = con.prepareStatement(refSQL);
			
			 rs = pstmt.executeQuery();	
			if(rs.next()){
				ref = rs.getInt(1)+1;	
			}

			String SQL = "INSERT INTO BOARD VALUES(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(SQL);
			
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_stop);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());		
			
			pstmt.executeUpdate();	
			
			con.close();
			}catch(Exception e) {
				e.printStackTrace();	
			}	
	}
}

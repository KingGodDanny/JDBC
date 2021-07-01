package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAuthorOneSelectApp {

	public static void main(String[] args) {
		
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
				    // 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
				    // 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");

				    // 3. SQL문 준비 / 바인딩 / 실행
				    String query = "";
				    query += " select bo.book_id, ";
				    query += " 		  bo.title, ";
				    query += " 		  bo.pubs, ";
				    query += " 		  to_char(bo.pub_date, 'YYYY-MM-DD') as dateD, ";
				    query += " 		  bo.author_id, ";
				    query += " 		  au.author_name, ";
				    query += " 		  au.author_desc ";
				    query += " from author au, ";
				    query += " 		book bo ";
				    query += " where au.author_id = bo.author_id ";
				    query += " and book_id = ? ";
				    pstmt = conn.prepareStatement(query);
				    
				    pstmt.setInt(1, 2);
				    
				    rs = pstmt.executeQuery();
				    
				    		
				    // 4.결과처리   -- > book_id , title같은것은 SQL디벨로퍼에서 컬럼명을 나타내는것이므로 변수명.title로 정하면안된다.
				    while(rs.next()) {
				    	int bbookId = rs.getInt("book_id");
				    	String btitleT = rs.getString("title");
				    	String bPubs = rs.getString("pubs");
				    	String bpDate = rs.getString("dateD");
				    	int bauthorId = rs.getInt("author_id");
				    	String aauthorN = rs.getString("author_name");
				    	String aauthorD = rs.getString("author_desc");
				    	
				    	System.out.println(bbookId + ", " + btitleT + ", " + bPubs + ", " + 
				    			bpDate + ", " + bauthorId + ", " + aauthorN + ", " + aauthorD);
				    }
				    
				    
				} catch (ClassNotFoundException e) {
				    System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
				    System.out.println("error:" + e);
				} finally {
				   
				    // 5. 자원정리
				    try {
				        if (rs != null) {
				            rs.close();
				        }                
				        if (pstmt != null) {
				            pstmt.close();
				        }
				        if (conn != null) {
				            conn.close();
				        }
				    } catch (SQLException e) {
				        System.out.println("error:" + e);
				    }

				}


	}

}

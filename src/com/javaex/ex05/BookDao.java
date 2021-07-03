package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
	
	//필드
	
	
	//생성자
	
	
	//메소드(게터세터)
	
	
	//메소드(일반)
	
	
	//책 리스트 가져오기****************************************
	public List<BookVo> getBookList() { //리스트 출력 메소드
		
		
		//리스트 생성하기
		List<BookVo> bookList = new ArrayList<BookVo>();
		
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
		    query += " select book_id, ";
		    query += "        title, ";
		    query += "        pubs, ";
		    query += "        pub_date, ";
		    query += "        author_id ";
		    query += " from book ";
		    query += " order by book_id asc "; 
			
		    pstmt = conn.prepareStatement(query);
		    
		    rs = pstmt.executeQuery();
		   
			
		    // 4.결과처리
		    while (rs.next()) {
		    	int bookId = rs.getInt("book_id");
		    	String titleB = rs.getString("title");
		    	String pubsB = rs.getString("pubs");
		    	String pubDate = rs.getString("pub_date");
		    	int authorId = rs.getInt("author_id");
		    	
		    	BookVo bookVo = new BookVo(bookId, titleB, pubsB, pubDate, authorId);
		    	
		    	bookList.add(bookVo);
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
		return bookList;
		
		
	}
	
	
	
	
}

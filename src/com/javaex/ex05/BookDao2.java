package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao2 {
	
	//필드
	
	
	//생성자
	
	
	//메소드(게터세터)
	
	
	//메소드(일반)
	
	//작가 삭제하기**********************************************
	public int bookDelete(int authorId) {
		
		int count = -1;
		
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
		    query += " delete from book ";
		    query += " where author_id = ? ";
		    
		    pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, authorId);
		    
		    count = pstmt.executeUpdate();
			
		    // 4.결과처리
		    System.out.println(count + "건이 삭제되었습니다.7");
		    
		    
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
		return count;
		
	}
	//********************************************************************************
	
	//작가 수정하기 ***********************************************
	public int bookUpdate(BookVo bookVo) {
		
		int count = -1;
		
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
		    query += " update book ";
		    query += " set pubs = ? ";
		    query += " where auther_id = ? ";
		    
		    pstmt = conn.prepareStatement(query);
		    
		    pstmt.setString(1, bookVo.getPubs());
		    pstmt.setInt(2, bookVo.getAuthorId());
		    
		    count = pstmt.executeUpdate();
		    
			
			
		    // 4.결과처리
		    System.out.println(count + "건이 수정되었습니다.");

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
		
		return count;
	}
	//********************************************************************************
	
	
	//작가 등록하기*********************************************
	public int bookInsert(BookVo bookVo) {
		
		int count = -1;
		
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
		    query += " insert into book ";
		    query += " VALUES(bseq_book_id.nextval, ?, ?, ?, ?) ";
		    
		    pstmt = conn.prepareStatement(query);
		    
		    pstmt.setString(1, bookVo.getTitle());
		    pstmt.setString(2, bookVo.getPubs());
		    pstmt.setString(3, bookVo.getPubDate());
		    pstmt.setInt(4, bookVo.getAuthorId());
			
		    count = pstmt.executeUpdate();
			
		    // 4.결과처리
		    System.out.println(count + "건이 등록되었습니다. ");
		    
		    
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

		return count;
		
	}
	//********************************************************************************
	
	
	//책 리스트 가져오기****************************************
	public List<BookVo> getBookSelect() { //리스트 출력 메소드
		
		
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
	//********************************************************************************
	
	//북&작가 리스트 가져오기******************************************************
	public List<BookVo> getBookList() {
		// DB값을 가져와서 ArrayList로 전달
		
		//리스트 생성
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
		    
		    pstmt = conn.prepareStatement(query);
		    
		    rs = pstmt.executeQuery();
			
			
		    // 4.결과처리
		    while(rs.next()) {
		    	int bookId = rs.getInt("book_id");
		    	String title = rs.getString("title");
		    	String pubs = rs.getString("pubs");
		    	String pubDate = rs.getString("dateD");
		    	int authorId = rs.getInt("author_id");
		    	String authorName = rs.getString("author_name");
		    	String authorDesc = rs.getString("author_desc");
		    	
		    	BookVo bookVo = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);
		    	
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

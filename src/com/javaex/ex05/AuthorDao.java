package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	
	// 생성자

	// 메소드(게터세터)

	// 메소드(일반)
	
	//Author 테이블 만들기 ***************************************************
	public void authorTable() {
		
		this.getConnection();
		
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
		    String query = "";
		    query += " create table author ( ";
		    query += " 	    author_id number(10), ";
		    query += "      author_name VARCHAR2(100) not null, ";
		    query += "      author_desc VARCHAR2(500), ";
		    query += "      PRIMARY KEY (author_id)) ";
		    
		    pstmt = conn.prepareStatement(query);
		   
		    pstmt.executeUpdate();
		    

		    // 4.결과처리
		    

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.close();
		
		
	}
	//**********************************************************************
	
	//Author 시퀀스 생성****************************************************
	public void authorSeq() {
		
		this.getConnection();
		
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
		    String query = "";
		    query += " create SEQUENCE sequ_author_id ";
		    query += " INCREMENT BY 1 ";
		    query += " START WITH 1 ";
		
		    
		    pstmt = conn.prepareStatement(query);
		   
		    pstmt.executeUpdate();
		    

		    // 4.결과처리
		    

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.close();
		
	}
	//*************************************************************************
	
	//1~2.DB 연결**************************************************************
	private void getConnection() { //public에서 private로 바꿔주는 이유는 App에서 사용하지못하게 하고 이 Class안에서만 사용될거기때문.
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	
	//5.자원정리
	private void close() { //public에서 private로 바꿔주는 이유는 App에서 사용하지못하게 하고 이 Class안에서만 사용될거기때문.
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
	
	
	// 작가 삭제하기*****************************************************************
	public int authorDelete(int authorId) {
		int count = -1;
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";

			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			count = pstmt.executeUpdate();

			
			// 4.결과처리
			System.out.println(count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		this.close();
			
		
		return count;
		
	}
	//********************************************************************************
	
	
	// 작가 수정하기*****************************************************************
	public int authorUpdate(AuthorVo authorVo) {

		int count = -1;

		this.getConnection();

		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update author ";
			query += " set author_name = ?, ";
			query += "     author_desc = ? ";
			query += " where author_id = ? ";

			
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setString(1, authorVo.getAuthorName());
			pstmt.setString(2, authorVo.getAuthorDesc());
			pstmt.setInt(3, authorVo.getAuthorId());

			
			count = pstmt.executeUpdate();

			

		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 

		// 4.결과처리
		System.out.println(count + "건이 수정되었습니다.");
			
		this.close();	

		return count;
		
	}
	//********************************************************************************
	
	
	// 작가 등록하기*****************************************************************
	public int authorInsert(AuthorVo authorVo) {

		int count = -1;

		this.getConnection();

		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into author ";
			query += " values(sequ_author_id.nextval, ?, ?) ";

			
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setString(1, authorVo.getAuthorName());
			pstmt.setString(2, authorVo.getAuthorDesc());

			
			count = pstmt.executeUpdate();

			
			// 4.결과처리
		

		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 

			
		this.close();

		return count; // 성공갯수 리턴
		
	}
	
	//********************************************************************************
	
	
	
	// 작가 리스트 가져오기************************************************************
	public List<AuthorVo> getAuthorList() {
		// DB값을 가져와서 ArrayList로 전달

		
		// 리스트 생성
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		
		this.getConnection();

		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select  author_id, ";
			query += "         author_name, ";
			query += "         author_desc ";
			query += " from author ";
			query += " order by author_id asc ";

			
			pstmt = conn.prepareStatement(query);

			
			rs = pstmt.executeQuery();

			
			// 4.결과처리
			while (rs.next()) {
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				AuthorVo authorVo = new AuthorVo(authorId, authorName, authorDesc);

				authorList.add(authorVo);
			}

		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		
		
		this.close();	

		return authorList;

	}
	//***************************************************************************
	
	
}
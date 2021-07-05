package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

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
	
	// Book 테이블 만들기*********************************************************
	public void bookTable() {

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " create table book ( ";
			query += "     book_id number(10), ";
			query += "     title VARCHAR2(100) not null, ";
			query += "     pubs VARCHAR2(100), ";
			query += "     pub_date date, ";
			query += "     author_id number(10), ";
			query += "     PRIMARY KEY (book_id), ";
			query += "     CONSTRAINT book_fk FOREIGN KEY (author_id) ";
			query += "     REFERENCES author(author_id)) ";

			pstmt = conn.prepareStatement(query);

			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.close();

	}

	// Book 시퀀스 생성****************************************************************
	public void bookSeq() {

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " create SEQUENCE bseq_book_id ";
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
	// *************************************************************************

	// 1~2.DB연결 메소드********************************************************
	private void getConnection() {

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
	// ********************************************************************************

	// 5.자원정리 메소드***************************************************************
	private void close() {

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
	// ********************************************************************************

	// 작가 삭제하기*******************************************************************
	public int bookDelete(int authorId) {

		int count = -1;

		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from book ";
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

		close();

		return count;

	}
	// ********************************************************************************

	// 작가 수정하기 ******************************************************************
	public int bookUpdate(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update book ";
			query += " set title = ?, ";
			query += " 	   pubs = ?, ";
			query += "     pub_date = ?, ";
			query += "     author_id = ? ";
			query += " where book_id = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());
			pstmt.setInt(5, bookVo.getBookId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건이 수정되었습니다.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.close();

		return count;
	}
	// ********************************************************************************

	// 작가 등록하기*******************************************************************
	public int bookInsert(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {
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
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.close();

		return count;

	}
	// ********************************************************************************

	// 책 리스트 가져오기****************************************
	public List<BookVo> getBookSelect() { // 리스트 출력 메소드

		// 리스트 생성하기
		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select book_id, ";
			query += "        title, ";
			query += "        pubs, ";
			query += "        to_char(pub_date, 'YYYY-MM-DD') as dateD, ";
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
				String pubDate = rs.getString("dateD");
				int authorId = rs.getInt("author_id");

				BookVo bookVo = new BookVo(bookId, titleB, pubsB, pubDate, authorId);

				bookList.add(bookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;

	}
	// ********************************************************************************

	// 북&작가 리스트 가져오기******************************************************
	public List<BookVo> getBookList() {
		// DB값을 가져와서 ArrayList로 전달

		// 리스트 생성
		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {
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
			while (rs.next()) {
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

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;

	}

	// search 메소드**************************************************************************
	public List<BookVo> searchList(String search) {

		// 리스트 생성
		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {
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
			query += " and bo.title || bo.pubs || au.author_name like ";
			query += "'%" + search + "%' ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
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

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;

	}
	
	
}

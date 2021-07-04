package com.javaex.ex05;

import java.util.List;


public class BookAuthorApp {

	public static void main(String[] args) {

		AuthorDao authorDao = new AuthorDao(); 
		List<AuthorVo> authorList;  // 리스트에 담기
		
		BookDao bookDao = new BookDao();  
		List<BookVo> bookList; // 리스트에 담기
		
		authorDao.authorTable();
		authorDao.authorSeq();
		
		bookDao.bookTable();
		bookDao.bookSeq();
		
		
		
		//Author 등록--------------------------------------------------------
		AuthorVo iAuthorVo1 = new AuthorVo("김문열", "경북 영양");
		authorDao.authorInsert(iAuthorVo1);
		AuthorVo iAuthorVo2 = new AuthorVo("박경리", "경상남도 통영");
		authorDao.authorInsert(iAuthorVo2);
		AuthorVo iAuthorVo3 = new AuthorVo("유시민", "17대 국회의원");
		authorDao.authorInsert(iAuthorVo3);
		AuthorVo iAuthorVo4 = new AuthorVo("기안84", "기안동에서 산 84년생");
		authorDao.authorInsert(iAuthorVo4);
		AuthorVo iAuthorVo5 = new AuthorVo("강풀", "온라인 만화가 1세대");
		authorDao.authorInsert(iAuthorVo5);
		AuthorVo iAuthorVo6 = new AuthorVo("김영하", "알쓸신잡");
		authorDao.authorInsert(iAuthorVo6);
		
		System.out.println("");
		System.out.println("작가 리스트 출력");
		authorList = authorDao.getAuthorList();
		printList(authorList);
		
		
		//Book 등록---------------------------------------------------------
		BookVo iBookVo1 = new BookVo("우리들의 일그러진 영웅", "다림", "1998-02-22", 1);
		bookDao.bookInsert(iBookVo1);
		BookVo iBookVo2 = new BookVo("삼국지", "민음사", "2002-03-01", 1);
		bookDao.bookInsert(iBookVo2);
		BookVo iBookVo3 = new BookVo("토지", "마로니에북스", "2012-08-15", 2);
		bookDao.bookInsert(iBookVo3);
		BookVo iBookVo4 = new BookVo("유시민의 글쓰기 특강", "생각의길", "2015-04-01", 3);
		bookDao.bookInsert(iBookVo4);
		BookVo iBookVo5 = new BookVo("패션왕", "중앙북스(books)", "2012-02-22", 4);
		bookDao.bookInsert(iBookVo5);
		BookVo iBookVo6 = new BookVo("순정만화", "재미주의", "2011-08-03", 5);
		bookDao.bookInsert(iBookVo6);
		BookVo iBookVo7 = new BookVo("오직두사람", "문학동네", "2017-05-04", 6);
		bookDao.bookInsert(iBookVo7);
		BookVo iBookVo8 = new BookVo("26년", "재미주의", "2012-02-04", 5);
		bookDao.bookInsert(iBookVo8);
		
		System.out.println("");
		System.out.println("책 리스트 출력");
		bookList = bookDao.getBookSelect();
		bookList(bookList);
		
		System.out.println("");
		System.out.println("책&작가 리스트 출력");
		bookList = bookDao.getBookList();
		getBookList(bookList);
		

	}
	
	
	//작가 리스트 출력 
	public static void printList(List<AuthorVo> authorList) {
		
		for(int i=0; i<authorList.size(); i++) {
			
			AuthorVo authorVo = authorList.get(i);
			System.out.println(authorVo.getAuthorId() + "\t" + authorVo.getAuthorName() + "\t\t" + authorVo.getAuthorDesc());
			
		}
	}
	

	//책 리스트 출력
	public static void bookList(List<BookVo> bookList) {
		for(int i=0; i<bookList.size(); i++) {
			BookVo bookVo = bookList.get(i);
			System.out.println(bookVo.getBookId() + "\t" + bookVo.getTitle() + "\t\t" + bookVo.getPubs() + "\t" + bookVo.getPubDate() + "\t" + bookVo.getAuthorId());
		}
	}
	
	//작가 & 책 리스트 출력
	public static void getBookList(List<BookVo> bookList) {
		for(int i=0; i<bookList.size(); i++) {
			BookVo bookVo = bookList.get(i);
			System.out.println(bookVo.getBookId() + "\t" + bookVo.getTitle() + "\t\t" + bookVo.getPubs() + "\t" + bookVo.getPubDate() + "\t" + bookVo.getAuthorId() + "\t" + bookVo.getAuthorName() + "\t" + bookVo.getAuthorDesc());
			
		}
	}
	
}

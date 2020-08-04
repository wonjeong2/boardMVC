package com.springbook.board;


public class BoardVO {
	private int i_board;
	private String title;
	private String ctnt;
	private String r_dt;
	
	private int sIdx;  
	private int count; //한페이지에 보여줄 게시글의 수
	private String searchText;  //검색
	
	public int getI_board() {
		return i_board;
	}
	
	public void setI_board(int i_board) {
		this.i_board = i_board;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCtnt() {
		return ctnt;
	}
	
	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}
	
	public String getR_dt() {
		return r_dt;
	}
	
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	
	public int getsIdx() {
		return sIdx;
	}
	
	public void setsIdx(int sIdx) {
		this.sIdx = sIdx;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getSearchText() {
		return searchText;
	}
	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}	
	
}

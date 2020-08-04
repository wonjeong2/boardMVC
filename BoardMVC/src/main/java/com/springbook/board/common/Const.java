package com.springbook.board.common;

public class Const {
	public static final String KAKAO_CLIENT_ID = "edd65637e2924cd47316ba4bcd6c5766";
	public static final String KAKAO_AUTH_REDIRECT_URI = "http://localhost:8090/user/joinKakao";
	public static final String KAKAO_ACCESS_TOKEN_HOST = "https://kauth.kakao.com/oauth/token";
	public static final String KAKAO_API_HOST = "https://kapi.kakao.com";
	
	//게시판 한번에 가져올 데이터 수. 한페이지에 가져올 글의 수
	public static final int ROW_COUNT = 30;
	
}

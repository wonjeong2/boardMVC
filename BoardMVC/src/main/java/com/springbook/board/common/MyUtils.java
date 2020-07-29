package com.springbook.board.common;

import org.mindrot.jbcrypt.BCrypt;

public class MyUtils {
	
	public static String gensalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
}

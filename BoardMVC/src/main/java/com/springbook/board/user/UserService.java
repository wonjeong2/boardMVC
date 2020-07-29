package com.springbook.board.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.board.common.MyUtils;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	public int join(UserVO param) {
		int result = 0;
		String salt = MyUtils.gensalt();
		String pw = param.getUpw();
		String hashPw = MyUtils.hashPassword(pw, salt);		
		
		param.setUpw(hashPw);
		param.setSalt(salt);
		//param.setUpw(MyUtils.hashPassword(param.getUpw()));
				
		result = mapper.join(param);
		return result;
	}
	
	public int login(UserVO param, HttpSession hs) {
		int result = 0;
		
		return result;
	}
}












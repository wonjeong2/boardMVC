
package com.springbook.board.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}	
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public String login(UserVO param, HttpSession hs) {
		
		return "user/login";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/joinPost", method=RequestMethod.POST)
	public String join(UserVO param) {
		int result = service.join(param);
		
		return "redirect:/user/login";
	}
}









package com.springbook.board.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbook.board.common.Const;
import com.springbook.board.common.MyUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)  //화면열기
	public String login() {
		return "user/login";
	}	
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)   //id, password 값 가져오기
	public String login(UserVO param, HttpSession hs, Model model) {
		
		 System.out.println("id:" + param.getUid());
		 System.out.println("pw:" + param.getUpw());
		 System.out.println("run user/login ");
		 
		 int result = service.login(param, hs);
	     
		 System.out.println("result :" + result);
		 
		 String msg = "에러발생";
		 String jsp = "/user/login";
	     
	      if (result == 1) {
	         jsp = "redirect:/board/list";
	         return jsp;
	      } 
	      
	      switch (result) {
	      case 2:
	    	  msg = "없는 아이디입니다.";
	    	  break;
	      case 3:
	    	  msg = "없는 비밀번호 입니다";
	    	  break;
	      }
	        
	         model.addAttribute("msg", msg);

	         return jsp;
	      }

	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(Model model, @RequestParam(required=false) String err) {
		
		if(err != null) {
			model.addAttribute("msg", err);
			
		}
		
		return "user/join";
	}
	
	@RequestMapping(value="/joinPost", method=RequestMethod.POST)
	public String join(UserVO param, HttpSession hs, RedirectAttributes ra) {
		
		String rNumbers = (String)hs.getAttribute("rNumbers"); //인증번호 가져오기
		
		if(!rNumbers.equals(param.getPhAuthNumber())) {
			ra.addAttribute("err", "인증번호가 맞지 않습니다.");
			return "redirect:/user/join";
		}
		
		int result = service.join(param);  //회원정보 db저장
		return "redirect:/user/login";
	}
	
	@ResponseBody //@ResponseBody 이걸쓰면 jackson이 반응한다.
	@RequestMapping(value="/phAuth", method=RequestMethod.GET)
	public Map<String, Object> phAuth(@RequestParam String ph, HttpSession hs) {  
		System.out.println("ph :" + ph);
		
		//4자리 랜덤값(0~9)
		String rNumbers = MyUtils.makeRandomNumber(4);  //인증번호4자리받는것
		System.out.println("rNumbers : " + rNumbers);
		
		hs.setAttribute("rNumbers", rNumbers);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", 1);  //key와 value (list와 다른점은 list는 인덱스가 있고 얘는 키와 밸류로 이루워져있다. / 순서가있다 : list, 순서가 없다 : map) 
//		키값은 항상 문자열, value값은 ""안에있으면 문자열, ""없으면 정수
						

		return map;
	}
	
	@RequestMapping(value="/loginKAKAO", method=RequestMethod.GET)
	public String loginKAKAO() {
		//인증코드 받기
		String uri = String.format("redirect:https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", 
				Const.KAKAO_CLIENT_ID, Const.KAKAO_AUTH_REDIRECT_URI);
		
		return uri;
	}
	
	@RequestMapping(value="/joinKakao", method=RequestMethod.GET)
	public String joinKAKAO(@RequestParam(required=false) String code,  
			@RequestParam(required=false) String error,
			HttpSession hs) {
		//기본값은 @RequestParam(required=true)인데 false로 설정해주면 값이 안넘어와도 에러가 안터진다. code, error 값이 하나만 넘어와도 된다 !
		
		
		System.out.println("code : " + code);  //코드가 넘어왔다면 로그인 성공
		System.out.println("error : " + error);  //에러가 넘어왔다면 로그인 실패
		
		if(code == null) { //로그인 실패
			return "redirect:/user/login";
		}
		
		int result = service.kakaoLogin(code, hs);
		
		return "redirect:/board/list";		
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model) {

		return "user/profile";
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST)
	public String profile(@RequestParam("uploadProfile") MultipartFile uploadProfile) {
		System.out.println("uploadProfile :" + uploadProfile);

		return "user/profile";
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession hs) {
		hs.invalidate();
		
		return "redirect:/user/login";
	}
	
	
}








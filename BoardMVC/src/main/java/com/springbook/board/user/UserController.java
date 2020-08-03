
package com.springbook.board.user;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbook.board.common.Const;
import com.springbook.board.common.KakaoAuth;
import com.springbook.board.common.MyUtils;

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
	public String login(UserVO param, HttpSession hs, Model model) {
		
		 System.out.println("run user/login ");

		 int result = service.login(param, hs);
	      
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
		
		String uri = String.format("redirect:https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", 
				Const.KAKAO_CLIENT_ID, Const.KAKAO_AUTH_REDIRECT_URI);
		
		return uri;
	}
	
	@RequestMapping(value="/joinKakao", method=RequestMethod.GET)
	public String joinKAKAO(@RequestParam(required=false) String code,  
			@RequestParam(required=false) String error) {
		//기본값은 @RequestParam(required=true)인데 false로 설정해주면 값이 안넘어와도 에러가 안터진다. code, error 값이 하나만 넘어와도 된다 !
		
		
		System.out.println("code : " + code);  //코드가 넘어왔다면 로그인 성공
		System.out.println("error : " + error);  //에러가 넘어왔다면 로그인 실패
		
		if(code == null) {
			return "redirect:/user/login";
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		Charset utf8 = Charset.forName("UTF-8");  //메타정보를 넘겨주는 곳:haeder
		MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, utf8);
		headers.setAccept(Arrays.asList(mediaType));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  
		headers.set("KEY", "VALUE");
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();  //정보를 넘겨주는곳:parameter
		map.add("grant_type","authorization_code");
		map.add("client_id", Const.KAKAO_CLIENT_ID);
		map.add("redirect_uri",Const.KAKAO_AUTH_REDIRECT_URI);
		map.add("code",code);
		
		HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity(map, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> respEntity 
		= restTemplate.exchange(Const.KAKAO_ACCESS_TOKEN_HOST, HttpMethod.POST, entity, String.class);
		
		String result = respEntity.getBody();  //json형태로 넘어온다. 문자열 형태, 이걸 VO 만들어서 따로 떼어서 넣어준다.
		
		System.out.println("result :" + result);
		//expires_in : 엑세스토큰의 살아있는 시간 , refresh_token_expires_in : refresh_token의 살아있는 시간
		
		ObjectMapper om = new ObjectMapper(); //json을 object형태로 바꿔주는것
		//ObjectMapper om = new ObjectMapper().; : json형태로 바꿔놓은 값중 굳이 필요없는 값은 안가져오고싶을때 사용. set/get 안만들어도 에러안터진다!
		try {
			KakaoAuth auth = om.readValue(result, KakaoAuth.class);  //json형태의 문자열을 쪼개서 KakaoAuth에 담아주는것
			
			System.out.println("access_token : " + auth.getAccess_token());
			System.out.println("refresh_token : " + auth.getRefresh_token());
			System.out.println("expires_in : " + auth.getExpires_in());
			System.out.println("refresh_token_expires_in : " + auth.getRefresh_token_expires_in());
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/user/login";
	}
	
	
}








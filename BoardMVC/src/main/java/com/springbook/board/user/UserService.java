package com.springbook.board.user;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbook.board.common.Const;
import com.springbook.board.common.KakaoAuth;
import com.springbook.board.common.KakaoUserInfo;
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
		      UserVO db = mapper.selUser(param);
		      
		      if (db != null) {  //로그인성공
		         String pw = param.getUpw();  //내가 입력한값
		         String salt = db.getSalt();
		         String hashPw = MyUtils.hashPassword(pw, salt);
		         if (db.getUpw().equals(hashPw)) {  //로그인성공
		        	db.setUpw(null);
		        	db.setUid(null);
		            hs.setAttribute("loginUser", db);
		            result = 1;
		         } else {
		            result = 3; //비밀번호 틀림
		         }
		      } else {
		         result = 2;  //아이디없음
		      }
		      return result;
		   }
	   
	   
	   
	   public int kakaoLogin(String code, HttpSession hs) {
		   //result : 리턴할 정수값 , 
		   int result = 0;
		   
		   //사용자 엑세스토큰 받기
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
			
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity(map, headers);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> respEntity 
			= restTemplate.exchange(Const.KAKAO_ACCESS_TOKEN_HOST, HttpMethod.POST, entity, String.class);
			
			String result3 = respEntity.getBody();  //json형태로 넘어온다. 문자열 형태, 이걸 VO 만들어서 따로 떼어서 넣어준다.
			
			System.out.println("result :" + result3);
			//expires_in : 엑세스토큰의 살아있는 시간 , refresh_token_expires_in : refresh_token의 살아있는 시간
			
			ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //json을 object형태로 바꿔주는것
			//ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); : json형태로 바꿔놓은 값중 굳이 필요없는 값은 안가져오고싶을때 사용. set/get 안만들어도 에러안터진다!
			
			KakaoAuth auth = null;
			
			try {
				auth = om.readValue(result3, KakaoAuth.class);  //json형태의 문자열을 쪼개서 KakaoAuth에 담아주는것
				
				System.out.println("access_token : " + auth.getAccess_token());
				System.out.println("refresh_token : " + auth.getRefresh_token());
				System.out.println("expires_in : " + auth.getExpires_in());
				System.out.println("refresh_token_expires_in : " + auth.getRefresh_token_expires_in());
				
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			//-------------사용자정보 가져오기---------------START
			HttpHeaders headers2 = new HttpHeaders();	 //사용자 정보 가져오기 위한 통신 셋팅	
	
			MediaType mediaType2 = new MediaType(MediaType.APPLICATION_JSON, utf8);		
			headers2.setAccept(Arrays.asList(mediaType2));
			headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers2.set("Authorization", "Bearer " +  auth.getAccess_token());
			
			HttpEntity<LinkedMultiValueMap<String, Object>> entity2 = new HttpEntity("", headers2);
					
			ResponseEntity<String> respEntity2 
			= restTemplate.exchange(Const.KAKAO_API_HOST + "/v2/user/me", HttpMethod.GET, entity2, String.class);
			
			String result2 = respEntity2.getBody();
			System.out.println("result2 : " + result2);
			
			KakaoUserInfo kui = null;
			
			try {    //json문자열을 짤라서 가져오기
				kui = om.readValue(result2, KakaoUserInfo.class);  //json형태의 문자열을 쪼개서 KakaoAuth에 담아주는것
				
				System.out.println("id : " + kui.getId()); 
				System.out.println("connected_at : " + kui.getConnected_at());
				System.out.println("nickname : " + kui.getProperties().getNickname());
				System.out.println("profile_image : " + kui.getProperties().getProfile_image());
				System.out.println("thumbnail_image : " + kui.getProperties().getThumbnail_image());

			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			//-------------사용자정보 가져오기---------------END
		   
			//아이디 존재 체크
			UserVO param = new UserVO();
			param.setUid(String.valueOf(kui.getId()));
			
			UserVO dbResult = mapper.selUser(param);
			
			if(dbResult == null) { //회원가입
				param.setNm(kui.getProperties().getNickname());
				param.setUpw("");
				param.setPh("");
				param.setSalt("");
				param.setAddr("");
				
				mapper.join(param);
				
				dbResult = param;
			}
			
			//로그인처리(세션에 값 add)
			hs.setAttribute("loginUser", dbResult);
			
		   return result;
	   }
	   
	   
	   public void delProfileImgParent(HttpSession hs) {  //DB profileImg에 빈칸 넣기
		   delProfileImg(hs);
		   
		   UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		   
		   UserVO param = new UserVO();
		   param.setI_user(loginUser.getI_user());
		   param.setProfileImg("");
		   
		   mapper.updUser(param);
	   }
	   
	   
	   public void delProfileImg(HttpSession hs) {
		   UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		   
		   UserVO dbUser = mapper.selUser(loginUser);
		   
		   String realPath = hs.getServletContext().getRealPath("/"); //루트 절대경로 가져오기
		   String imgFolder = realPath + "/resources/img/user/" + loginUser.getI_user();
		   
		   if("".equals(dbUser.getProfileImg())) {  //기존 이미지가 있으면 삭제처리
			   String imgPath = imgFolder + "/" + dbUser.getProfileImg();
			   MyUtils.deleteFile(imgPath);
		   }
	   }
	   
	   
	   //파일업로드
	   public void uploadPfoFile(MultipartFile file, HttpSession hs) {
		   UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		   
		   String realPath = hs.getServletContext().getRealPath("/"); //루트 절대경로 가져오기
		   // realPath => C:\javaBackend2020\workspace_jsp\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\BoardMVC 여기경로까지 가져온다.
		   String imgFolder = realPath + "/resources/img/user/" + loginUser.getI_user();
		   
		   delProfileImg(hs); //기존 이미지 삭제
		   
		   String fileNm = MyUtils.saveFile(imgFolder, file); //리턴되는 값은 파일명
		   
		   UserVO param = new UserVO();
		   param.setI_user(loginUser.getI_user());
		   param.setProfileImg(fileNm);
		   
		   mapper.updUser(param); //프로필, 이름, 폰번호, 비밀번호등이 바꼈을때 m_dt 업데이트
	   }
	   
	   
	   public String getProfileImg(HttpSession hs) {  //db에서 프로필 사진 가져오기
		   String profileImg = null;
		   
		   UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		   UserVO dbResult = mapper.selUser(loginUser);
		   
		   profileImg = dbResult.getProfileImg();
		   
		   if(profileImg == null || profileImg.equals("")) {
			   profileImg = "/resources/img/base_profile.png";
		   } else {
			   profileImg = "/resources/img/user/" + loginUser.getI_user() + "/" + profileImg;
		   }
		   
		   return profileImg;
		   
	   }
}



 








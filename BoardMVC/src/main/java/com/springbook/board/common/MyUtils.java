package com.springbook.board.common;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

public class MyUtils {
   public static String gensalt() {
      return BCrypt.gensalt();
   }

   public static String hashPassword(String pw, String salt) {
      return BCrypt.hashpw(pw, salt);
   }
   
   
   //len:길이
   public static String makeRandomNumber(int len) {
	   
	   String result = "";
	   
	   for(int i = 0; i < len; i++) {		   
		   result += (int)(Math.random() * 10);	      
	   }
	   
	   return result;
   }
   
  
   // 리턴값 : 저장된 파일명
   public static String saveFile(String path, MultipartFile file) {  //path : 원하는 위치값 , file:
	   
	   String fileNm = null;  
	   UUID uuid = UUID.randomUUID(); //중복되지않는 값을 랜덤으로 만들어준다.
	   
	   //확장자
	   String ext = FilenameUtils.getExtension(file.getOriginalFilename());
	   System.out.println("ext : " + ext);
	   
	   
	   fileNm = String.format("%s.%s", uuid, ext);
	   String saveFileNm = String.format("%s/%s", path, fileNm);   
	   // "path : /resources/user/??(pk값) ,  fileNm : 파일명 그래서 두개 합쳐서 /resources/user/??/파일명

	   File saveFile = new File(saveFileNm); //저장위치
	   saveFile.mkdirs();
	   
	   try {
		   file.transferTo(saveFile); //업로드 파일에 saveFile의 위치로 저장했다.
	   } catch (IOException e) {
		   e.printStackTrace();
		   return null;
	   }
	   
	   return fileNm; 
	   
   }
   
}
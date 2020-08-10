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
   
  
   
   public static String saveFile(String path, MultipartFile file) {  //path : 원하는 위치값 , file:
	   
	   String fileNm = null;  
	   UUID uuid = UUID.randomUUID(); //중복되지않는 값을 랜덤으로 만들어준다. 랜덤한 파일명
	   
	   //확장자
	   String ext = FilenameUtils.getExtension(file.getOriginalFilename()); //전체 파일명이 넘어오는데 그중에 확장자만 가져오는 것
	   System.out.println("ext : " + ext);
	   
	   
	   fileNm = String.format("%s.%s", uuid, ext);
	   String saveFileNm = String.format("%s/%s", path, fileNm);   
	   // "path : /resources/user/??(pk값) ,  fileNm : 파일명 그래서 두개 합쳐서 /resources/user/??/파일명

	   File saveFile = new File(saveFileNm); //저장위치
	   saveFile.mkdirs(); //만약 폴더가 없다면 폴더를 만들어라.
	   
	   try {
		   file.transferTo(saveFile); //업로드 파일을 saveFile의 위치로 저장하겠다.
	   } catch (IOException e) {
		   e.printStackTrace();
		   return null;
	   }
	   
	   return fileNm; // 리턴값 : 저장된 파일명
	   
   }
   
   public static boolean deleteFile(String filePath) {  //프로필 변경할 경우 원래 있던 파일 삭제
	   boolean result = false;
	   File file = new File(filePath);
	   
	   if(file.exists()) {
		   result = file.delete();
	   }
	   
	   return result;
   }
   
}


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필</title>
</head>
<body>
	<div>
		<img src="${myProfile}">
	</div>
	<div>
		<a href="/user/delProfile">기본 이미지 설정</a>
		<form id="frm" method="post" action="/user/profile" enctype="multipart/form-data" onsubmit="return chk()">		   
		    <div>
		    	<label> 이미지 선택 : <input type="file" name="uploadProfile" accept="image/*"></label>
		    </div>
		    <div>
		    	<button type="submit">저장</button>
		    </div>
		</form>
	</div>
	<script>
		function chk() {
			if(frm.uploadProfile.value == '') {
				alert('이미지를 선택해 주세요')
				return false
			}
		}
	</script>
</body>
</html>
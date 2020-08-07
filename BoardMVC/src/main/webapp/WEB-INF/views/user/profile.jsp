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
		<img src="${myProfile != null?myProfile : '/resources/img/base_profile.png'}">
	</div>
	<div>
		<form method="post" action="/user/profile" enctype="multipart/form-data">
  			<div>
  				<label>
  					이미지선택 :<input type="file" name="uploadProfile"  accept="image/*">          
  				</label>
  			</div>
			<div>
    			<button type="submit">저장</button>	
			</div>
		</form>
	</div>
</body>
</html>
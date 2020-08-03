<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<div>${msg}</div>
	<div>
		<form action="/user/loginPost" method="post">
			<div>아이디 : <input type="text" name="uid"></div>
			<div>비밀번호 : <input type="password" name="upw"></div>
			<div><input type="submit" value="로그인"></div>
		</form>
		<div><a href="/user/join">회원가입</a></div>
		<div><a href="/user/loginKAKAO">카카오톡 로그인</a></div>
		
	</div>
</body>
</html>
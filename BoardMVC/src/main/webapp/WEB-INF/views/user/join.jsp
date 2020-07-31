<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="/resources/js/axios.min.js"></script>
</head>
<body>
	<div>
		<div><a href="/user/login">로그인</a></div>
		<form action="/user/joinPost" method="post">
			<div>아이디 : <input type="text" name="uid"></div>
			<div>비밀번호 : <input type="password" name="upw"></div>
			<div>확인 비밀번호 : <input type="password" name="upwConfirm"></div>
			<div>Phone : 010 - <input type="text" name="ph" id="ph"><button type="button" onclick="sendPhAuthNumber()">인증번호 보내기</button></div>
			<div>인증번호 : <input type="text" name="phAuthNumber"></div>
			<div id="phAuthResult"></div>
			<div>이름 : <input type="text" name="nm"></div>
			<div>주소 : <input type="text" name="addr"></div>
			<div><input type="submit" value="회원가입"></div>
		</form>
	</div>
	<script>
		function sendPhAuthNumber() {
			console.log('ph :' + ph.value)
			axios.get('/user/phAuth', {
				params : {
					ph : ph.value
				}
			}).then(function(res) {
				
				if(res.data.result == 1) {
					alert('통신완료')
				} else {
					alert('에러발생')
				}
			})
		}
	
	</script>
</body>
</html>
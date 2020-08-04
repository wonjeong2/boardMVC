<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
	<div>
		${loginUser.nm }님 환영합니다. 
		<a href="/user/logout"><button>로그아웃</button></a>
	</div>
	<a href="/board/write">글등록</a>
	<table id="contentTable">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>등록일시</th>
		</tr>
	</table>
	<script>
		function moveToDetail(i_board) {
			location.href = '/board/detail?i_board=' + i_board
		}
		
		function addRows(res) {
			res.data.result.forEach(function(item) {  //item에 레코드 하나씩 담는다.
				
				var td1 = document.createElement("td");  //레코드 하나의 값을 가져오기 위한것
				td1.innerHTML = item.i_board
				
				var td2 = document.createElement("td");
				td2.innerHTML = item.title
				
				var td3 = document.createElement("td");
				td3.innerHTML = item.r_dt
				
				var tr = document.createElement('tr')  //그 레코드를 한줄에 찍을려고!!
				tr.appendChild(td1)
				tr.appendChild(td2)
				tr.appendChild(td3)
			
				var ele = document.querySelector('#contentTable') //DOM을 찾을 때 쓰는것			
				ele.appendChild(tr)
			})
		}
				
		function getBoardData(page) {
			axios.get('/board/getListData', {
				params: {
					page: page
				}
			}).then(function (res) {   //addRows로간다.
				addRows(res)
			})
		}
		
		var page = 1
		getBoardData(page)
	</script>
</body>
</html>







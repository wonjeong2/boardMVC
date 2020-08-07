<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<style>
	.hoverSelected:hover {
		background-color : #ecf0f1;
		cursor : pointer;
	}
</style>

</head>
<body>
	<div>
		${loginUser.nm }님 환영합니다.
		<a href="/user/profile"><button>프로필</button></a> 
		<a href="/user/logout"><button>로그아웃</button></a>
		
	</div>
	<a href="/board/write">글등록</a>
	<div>
		검색 : <input type="search" id="searchText"><button onclick="search(searchText)">검색</button>
	</div>
	
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>등록일시</th>
		</tr>		
		<tbody id="contentTable">		
		</tbody>
	</table>
	
	
	<script>
		function search(searchText) {
			if(searchText.value == '') {
				alert('검색어를 입력해 주세요')
				searchText.focus()
				return
			}
			
			var ele = document.querySelector('#contentTable')	
			ele.innerHTML = ''
			page = 1
			globalSearchText = searchText.value
			getBoardData(page, globalSearchText)
		}
		
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
				
				tr.addEventListener('click', function() {  
					location.href=`/board/detail?i_board=\${item.i_board}` //`: 문자열안에 값을 삽입하는것
					// location.href='/board/detail?i_board=' + item.i_board  : 이렇게 써도 된다
						//closure(클로져) : 지역변수가 사라지지 않고 여기서도 쓸수있는것(i_board를 쓸수 있는것)
				})
				
				tr.classList.add("hoverSelected")
				
				var ele = document.querySelector('#contentTable')	//DOM을 찾을 때 쓰는것					
				ele.appendChild(tr)
			})
		}
				
		function getBoardData(page, searchText) {		
			axios.get('/board/getListData', {
				params: {
					page,
					searchText
				}
			}).then(function (res) {  
				addRows(res)	 //addRows로간다.			
				if(res.data.result.length < 60) {
					isBreak = true	
				}
			})
		}
		
		var isBreak = false
		var page = 1
		var globalSearchText = ''
		getBoardData(page, globalSearchText)
		
		window.addEventListener('scroll', function() {
			if(isBreak) {
				return
			}
			if(window.scrollY + window.innerHeight >= document.body.scrollHeight) {
				getBoardData(++page, globalSearchText)	
			}
		})
	</script>
</body>
</html>





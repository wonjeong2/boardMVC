<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
</head>
<body>
	<a href="/board/write">글등록</a>
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>등록일시</th>
		</tr>
	<c:forEach items="${data}" var="item">
		<tr onclick="moveToDetail(${item.i_board})">
			<td>${item.i_board }</td>
			<td>${item.title }</td>
			<td>${item.r_dt }</td>
		</tr>
	</c:forEach>
	</table>
	<script>
		function moveToDetail(i_board) {
			location.href = '/board/detail?i_board=' + i_board
		}
	</script>
</body>
</html>







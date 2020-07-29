<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>디테일</title>
</head>
<body>
	<div>
		<a href="/board/list">리스트</a>
		<a href="/board/del?i_board=${data.i_board}">삭제</a>
		<a href="/board/upd?i_board=${data.i_board}">수정</a>
	</div>
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>내용</th>
			<th>등록일시</th>
		</tr>
		<tr>
			<td>${data.i_board}</td>
			<td>${data.title}</td>
			<td>${data.ctnt}</td>
			<td>${data.r_dt}</td>
		</tr>
	</table>
</body>
</html>
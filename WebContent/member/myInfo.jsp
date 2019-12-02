<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<style>
#box {
	border: 1px solid black;
	width: 400px;
	height: 500px;
	margin: auto;
	text-align: center;
}

.left {
	text-align: right;
	width: 100px;
}

.btn {
	text-align: center;
}
</style>
</head>
<body>
	<table id=box>
		<tr>
			<th colspan="2">My page</th>
		</tr>
		<tr>
			<td class="left">ID :</td>
			<td>${dto.id }</td>
		</tr>
		<tr>
			<td class="left">Name :</td>
			<td>${dto.name }</td>
		</tr>
		<tr>
			<td class="left">Phone :</td>
			<td>${dto.phone }</td>
		</tr>
		<tr>
			<td class="left">Email :</td>
			<td>${dto.email }</td>
		</tr>
		<tr>
			<td class="left">Zipcode :</td>
			<td>${dto.zipcode}</td>
		</tr>
		<tr>
			<td class="left">Adress1 :</td>
			<td>${dto.address1}</td>
		</tr>
		<tr>
			<td class="left">Adress2 :</td>
			<td>${dto.address2}</td>
		</tr>
		<tr>
			<td class=btn colspan="2">
				<input type="button" value="돌아가기" id=btn>
			</td>
		</tr>
	</table>
	<script>
		$("#btn").on("click",function(){
			location.href = "index.jsp";
		})
	</script>
</body>
</html>
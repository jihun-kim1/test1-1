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
#login_box {
	border: 1px solid black;
	width: 200px;
	height: 200px;
	margin: auto;
	text-align: center;
}

.input {
	text-align: center;
}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${loginInfo == null }">
			<div id="login_box">
				<h2>Login</h2>
				<form action="FrontController.mem" method="post" id="loginForm">
					<input class="input" type="text" name=id id="id"
						placeholder="Input your ID"> <input class="input"
						type="password" name=pw placeholder="Input your PW"><br>
					<input type="button" value="Login" id="loginBtn"> <input
						type="button" value="Sign up" id=signupBtn>
				</form>
				<input type="checkbox" id="remID">Remember my ID
			</div>
		</c:when>
		<c:otherwise>
			<div id="logined_box">
				<h2>Welcome</h2>
				<div>${loginInfo}님환영합니다.</div>
				<input type="button" value="로그아웃" id=logout> <input
					type="button" value="탈퇴하기" id=memberout> <input type="button"
					value="수정하기" id=modify> <input type="button"
					value="나의 정보" id=myInfo> <input type="button" value="게시판"
					id=board>
			</div>
		</c:otherwise>

	</c:choose>
	<script>
		//member
		$("#loginBtn").on("click", function() {
			$("#loginForm").submit();
		})
		$("#signupBtn").on("click", function() {
			location.href = "member/signupForm.jsp";
		})
		$("#logout").on("click", function() {
			location.href = "logout.mem";
		})
		$("#memberout").on("click",function(){
			location.href = "memberout.mem";
		})
		$("#modify").on("click", function() {
			location.href = "modify.mem";
		})
		$("#myInfo").on("click",function(){
			location.href = "myInfo.mem";
		})
		//board
		$("#board").on("click", function() {
			location.href = "board.board";
		})
		

		function cookieAsJSON() {
			var cookieJSON = {};
			var cookie = document.cookie;
			var trimedCookie = cookie.replace(/ /g, "");
			var entryArr = trimedCookie.split(";");

			for (var i = 0; i < entryArr.length; i++) {
				var entry = entryArr[i].split("=");
				cookieJSON[entry[0]] = entry[1];
			}
			return cookieJSON;
		}
		$("#remID").on("change", function() {
			var exDate = new Date();
			if ($("#remID").prop("checked")) {

				exDate.setDate(exDate.getDate() + 30);
				var id = $("#id").val();
				document.cookie = "id=" + id + ";expires=" + exDate.toString();
			} else {
				exDate.setDate(exDate.getDate() - 1);
				document.cookie = "id=;expires=" + exDate.toString();
			}
		})
		$(function() {
			if (document.cookie != "") {
				var cookie = cookieAsJSON();
				$("#id").val(cookie.id);
				$("#remID").prop("checked", "true");
			}
		})
	</script>
</body>
</html>
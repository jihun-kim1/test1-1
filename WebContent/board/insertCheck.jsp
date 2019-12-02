<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${result > 0}">
			<script>
				alert("게시글 작성이 완료되었습니다.");
				location.href="../board.board";
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert("실패했습니다.");
				location.href="../board.board";
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>
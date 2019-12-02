<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/chatStyle.css">
</head>
<body>




	<table border=3>
		<tr>
			<td colspan="5" align=center>자유게시판</td>
		</tr>
		<tr>
			<td width=30></td>
			<td align=center width=670>Title</td>
			<td align=center width=100>Writer</td>
			<td align=center width=100>Date</td>
			<td align=center width=100>View</td>
		</tr>

		<c:choose>
			<c:when test="${list.size() ==0}">
				<td colspan="5" align=center>게시글이 없습니다.</td>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list}" var="dto">
					<tr>
						<td align=center width=30>${dto.board_seq}</td>
						<td align=center width=670><a
							href="contentsView.board?seq=${dto.board_seq}">${dto.title}</a></td>
						<td align=center width=100>${dto.writer}</td>
						<td align=center width=100>${dto.writeDate}</td>
						<td align=center width=100>${dto.viewCount}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<tr>
			<td colspan="5" align=center>${sb}</td>
		</tr>
		<tr>
			<td colspan="5" align=right><input type="button" value="글쓰기"
				id=write><input type="button" value="돌아가기" id=return></td>
			
	</table>
	<script>
		$("#write").on("click", function() {
			location.href = "board/boardwrite.jsp";
		})
		$("#response").on("click", function() {
			$("#frm").submit();
		})
		$("#return").on("click",function(){
			location.href = "index.jsp";
		})
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
<c:choose>
	<c:when test="${result==true}">
		이미 존재하는 아이디 입니다.
	</c:when>
	<c:otherwise>
		사용 할 수 있는 아이디 입니다.
		<button id="use">Use</button>
		<button	id="cancel">Cancel</button>
	</c:otherwise>
</c:choose>
<script>
$("#cancel").on("click",function(){
	opener.document.getElementById("id").value="";
	window.close();	
});
$("#use").on("click",function(){
	window.close();
})

</script>	

</body>
</html>
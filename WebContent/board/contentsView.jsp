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
	<table border="3">
		<tr>
			<td colspan="3" align=center>자유게시판 글 쓰기</td>
		</tr>
		<tr>
			<td width="100" align=center><select>
					<option>뉴스</option>
					<option>유머</option>
					<option>잡담</option>
			</select></td>

			<td width="500"><input type="text" id=title size="60"
				value="${result.title}" readonly></td>

			<td width="100"><input type="button" value="첨부파일"
				id=attachedFile></td>
		</tr>
		<tr>
			<td colspan="3"><textarea cols=100 rows=30 style="resize: none;"
					readonly>${result.contents} </textarea></td>
		</tr>
		<tr>
			<td colspan="3"><textarea placeholder="댓글을 입력해주세요" cols=93
					rows=1 style="resize: none;" id="comment"></textarea><input
				type="button" value="확인" align=right id="commentBtn"><br>
			</td>
		</tr>
		<tr>
			<td colspan="3"><div cols=100 rows=5 id="contents_box"></div></td>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${loginInfo == result.writer}">
					<td colspan="3" align=right><input type="button" value="목록으로"
						id=board> <input type="button" value="삭제하기" id=delete>
					</td>
				</c:when>
				<c:otherwise>
					<td colspan="3" align=right><input type="button" value="목록으로"
						id=board></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>
	<script>
		$("#board").on("click", function() {
			location.href = "board.board";
		});
		$("#delete").on("click", function() {
			location.href = "delete.board?seq=${result.board_seq}";
		});
		$("#attachedFile").on("click",function(){
			$.ajax({
				url : "attachedFile.board",
				type : "post",
				data : {
					seq : ${result.board_seq}
				},
				dataType : "json"
			}).done(function(data)){
				for(var i =0; data.length; i++){
					console.log(data[i].fileName);
				}
			});
		});
		$("#commentBtn").on("click", function() {
			$.ajax({
				url : "comment.com?seq=${result.board_seq}",
				type : "post",
				data : {
					contents : $("#comment").val(),
					type : "post",
					seq : ${result.board_seq}
				},
				dataType : "json"
			}).done(function(data){
				for(var i=0; data.length; i++){
					$("#contents_box").append(data[i].writer + " : " + data[i].contents + " : " + data[i].writedate +"<br>");
				}
			})
		});
	</script>
</body>
</html>
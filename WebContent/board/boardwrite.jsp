<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>

<style>
	#title{
		width : 600px;
		height : 30px;
	}
</style>
</head>
<body>
	<div id="box">
		<div id="title">
			<input type="text" placeholder="제목을 입력하세요.">
		</div>
		<div id="summernote"></div>
	</div>
	<script>
		$('#summernote').summernote({
			placeholder : 'Hello bootstrap 4',
			tabsize : 2,
			height : 300,
			width : 600,
			callbacks : {
				onImageUpload : function(files) {
					//data 자료형을 만듦
					var data = new FormData();
					data.append("file1", files[0]);

					$.ajax({
						url : "upload.file",
						enctype : "multipart/form-data",
						type : "post",
						data : data,
						contentType : false,
						processData : false,
						cache : false,
						dataType : "Json"
					}).done(function(resp) {

						var img = $("<Img>");
						$(img).attr("src", resp.url);
						$(".note-editable").append(img);

						//    $("#summernote").summernote("insertNode",img[0]);

					}).fail(function(a, b, c) {
						console.log(a);
						console.log(b);
						console.log(c);
					})
				}
			}
		});
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<style>
#box {
	width: 500px;
	height: 400px;
	margin: auto;
	border: 1px solid black;
}

h2 {
	text-align: center
}

input[type="text"] {
	align-items: center;
}

label {
	text-align: right;
	width: 150px;
	display: inline-block;
}

#btn_box {
	text-align: center;
}

.inputCheck {
	position: relative;
	left: 160px;
	font-size: 8px;
}

.addr {
	width: 300px;
}
</style>
</head>
<body>
	<form action="modified.mem" method="post" id=form>
		<div id=box>
			<h2>회원 가입 정보 입력</h2>
			<label>아이디:</label> <input type="text" id=id name=id
				value="${result.id}" readonly><br>
			<div class=inputCheck id="idResultMsg"></div>
			
			<label>패스워드:</label> <input type="password" id="pw" name="pw"><br>
			<div class="inputCheck" id=pwResultMsg></div>
			
			<label>패스워드 확인:</label> <input type="password" id="re_pw"
				name="re_pw"><br>
			<div class="inputCheck" id=rePwResultMsg></div>

			<label>이름:</label> <input type="text" id="name" name="name"
				value="${result.name }"><br>
			<div class="inputCheck" id=nameResultMsg></div>

			<label>전화번호:</label> <input type="text" id="phone" name="phone"
				value="${result.phone}"><br>
			<div class="inputCheck" id="phoneNumResultMsg"></div>

			<label>이메일:</label> <input type="text" id="email" name="email"
				value="${result.email }"><br>
			<div class="inputCheck" id="emailResultMsg"></div>

			<label>우편번호:</label> <input type="text" id=zipcode name="zipcode"
				value="${result.zipcode }"> <input type="button" value="찾기"
				onclick="Postcode()"><br> <label>주소1:</label> <input
				class=addr type="text" id=address1 name="address1"
				value="${result.address1 }"><br> <label>주소2:</label> <input
				class=addr type="text" id=address2 name="address2"
				value="${result.address2 }"><br>
			<div id=btn_box>

				<input type="button" value="수정하기" id=modify> <input
					type="reset" value="다시 입력"><input type="button"
					value="돌아가기" id="return">

			</div>
		</div>
	</form>
	<script>
		function Postcode() {
			new daum.Postcode(
					{
						oncomplete : function(data) {

							var roadAddr = data.roadAddress;
							var extraRoadAddr = '';

							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraRoadAddr += data.bname;
							}

							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraRoadAddr += (extraRoadAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}

							if (extraRoadAddr !== '') {
								extraRoadAddr = ' (' + extraRoadAddr + ')';
							}

							document.getElementById('zipcode').value = data.zonecode;
							document.getElementById("address1").value = data.jibunAddress;
						}
					}).open();
		}
		$("#pw").on("keyup", function() {
			var regex = /[a-zA-Z\d]{8,12}/;
			var text = $("#pw").val();

			if ((regex.exec(text)) != null) {
				$("#pwResultMsg").html("");
			} else {
				$("#pwResultMsg").html("영소대문자숫자 8~12");
				$("#pwResultMsg").css("color", "red");
			}
		});

		$("#re_pw").on("keyup", function() {
			if ($("#pw").val() != $("#re_pw").val()) {
				$("#rePwResultMsg").html("비밀번호 불일치");
				$("#rePwResultMsg").css("color", "red");
			} else {
				$("#rePwResultMsg").html("");
			}
		});

		$("#name").on("keyup", function() {
			var regex = /[가-힣]{2,}/;
			var text = $("#name").val();

			if ((regex.exec(text)) != null) {
				$("#nameResultMsg").html("");
				var nameResult = 1;
			} else {
				$("#nameResultMsg").html("이름을 올바르게 입력해주세요.");
				$("#nameResultMsg").css("color", "red");
			}
		});
		$("#phone").on("keyup", function() {
			var regex = /^01\d\d{3,4}\d{4}$/;
			var text = $("#phone").val();

			if ((regex.exec(text)) != null) {
				$("#phoneNumResultMsg").html("");
				var phoneResult = 1;
			} else {
				$("#phoneNumResultMsg").html("'-'은 제외");
				$("#phoneNumResultMsg").css("color", "red");
			}
		});

		$("#email").on("keyup", function() {
			var regex = /.+@[a-z]+(\.[a-z]+){1,2}$/;
			var text = $("#email").val();

			if ((regex.exec(text)) != null) {
				$("#emailResultMsg").html("");
				var emailResult = 1
			} else {
				$("#emailResultMsg").html("올바른 이메일을 입력해주세요.");
				$("#emailResultMsg").css("color", "red");
			}
		});

		$("#modify").on("click", function() {
			var name = $("#name").val();
			var phone = $("#phone").val();
			var email = $("#email").val();

			var nameregex = /[가-힣]{2,}/;
			var phoneregex = /^01\d\d{3,4}\d{4}$/;
			var emailregex = /.+@[a-z]+(\.[a-z]+){1,2}$/;

			
			var nameregex = nameregex.exec(name);
			if (nameregex == null) {
				alert("이름양식을 다시 확인해주세요");
				return;
			}
			var phoneregex = phoneregex.exec(phone);
			if (phoneregex == null) {
				alert("핸드폰번호양식을 다시 확인해주세요");
				return;
			}
			var emailregex = emailregex.exec(email);
			if (emailregex == null) {
				alert("이메일양식을 다시 확인해주세요");
				return;
			}
			$("#form").submit();
		})
		$("#return").on("click", function() {
			location.href = "index.jsp";
		})
	</script>
</body>
</html>
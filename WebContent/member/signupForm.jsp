<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<form action="signup.mem" method="post" id=form>
		<div id=box>
			<h2>회원 가입 정보 입력</h2>
			<label>아이디:</label> <input type="text" id=id name=id> <input
				type="button" value="중복확인" id="duplecheck"><br>
			<div class=inputCheck id="idResultMsg"></div>

			<label>패스워드:</label> <input type="password" id="pw" name="pw"><br>
			<div class="inputCheck" id=pwResultMsg></div>

			<label>패스워드 확인:</label> <input type="password" id="re_pw"
				name="re_pw"><br>
			<div class="inputCheck" id=rePwResultMsg></div>

			<label>이름:</label> <input type="text" id="name" name="name"><br>
			<div class="inputCheck" id=nameResultMsg></div>

			<label>전화번호:</label> <input type="text" id="phone" name="phone"><br>
			<div class="inputCheck" id="phoneNumResultMsg"></div>

			<label>이메일:</label> <input type="text" id="email" name="email"><br>
			<div class="inputCheck" id="emailResultMsg"></div>

			<label>우편번호:</label> <input type="text" id=zipcode name="zipcode">
			<input type="button" value="찾기" onclick="Postcode()"><br>

			<label>주소1:</label> <input class=addr type="text" id=address1
				name="address1"><br> <label>주소2:</label> <input
				class=addr type="text" id=address2 name="address2"><br>
			<div id=btn_box>

				<input type="button" value="회원가입" id=sign> <input
					type="reset" value="다시 입력">

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
		
		$("#duplecheck").on("click",function() {
			var id = $("#id").val();
			if (id == "") {
				alert("아이디를 입력해주세요");
				return;
			} else {
				$.ajax({
					url : "DupleCheck.mem",
					type : "post",
					data : {
						inputId : $("#id").val()
					}
				}).done(function(data) {

					alert("you can use it")

				}).fail(function() {
					alert("you can't use it")
				})
			}
		})

		$("#id").on("keyup", function() {
			var regex = /^[a-z][a-z0-9]{3,11}$/;
			var text = $("#id").val();

			
			if ((regex.exec(text)) != null) {
				$("#idResultMsg").html("");
			} else {
				$("#idResultMsg").html("영어, 숫자, 소문자 3~11자리 가능");
				$("#idResultMsg").css("color", "red");
			}
		});

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
		
		$("#sign").on("click",function(){
			var id= $("#id").val();
			var pw= $("#pw").val();
			var re_pw= $("#re_pw").val();
			var name = $("#name").val();
			var phone= $("#phone").val();
			var email= $("#email").val();
			
			var idregex = /^[a-z][a-z0-9]{3,11}$/;
            var pwregex = /[a-zA-Z\d]{8,12}/;
            var nameregex = /[가-힣]{2,}/;
            var phoneregex = /^01\d\d{3,4}\d{4}$/;
            var emailregex = /.+@[a-z]+(\.[a-z]+){1,2}$/;

            var idregex = idregex.exec(id);
              if(idregex == null){
                 alert("아이디양식을 다시 확인해주세요");
                 return;
              }
              var pwregex = pwregex.exec(pw);
              if(pwregex == null){
                 alert("비밀번호양식을 다시 확인해주세요");
                 return;
              }
              var nameregex = nameregex.exec(name);
              if(nameregex == null){
                 alert("이름양식을 다시 확인해주세요");
                 return;
              }
              var phoneregex = phoneregex.exec(phone);
              if(phoneregex == null){
                 alert("핸드폰번호양식을 다시 확인해주세요");
                 return;
              }
              var emailregex = emailregex.exec(email);
              if(emailregex == null){
                 alert("이메일양식을 다시 확인해주세요");
                 return;
              }
              $("#form").submit();
		})
		
		
	</script>
</body>
</html>
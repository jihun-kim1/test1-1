<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${header.referer == null }">
		<script>
			location.href = "chat.do";
		</script>
	</c:if>
	<div id="chatBox">
		<div id="control"></div>
		<div id="input">
			<input type="text" id=msg>
			<button id="send">Send</button>
		</div>
	</div>
	<script>
		var ws = new WebSocket("ws://192.168.60.16/WebChat/chat.do");
		ws.onopen = function(e) {
		} //연결을 성공했을 때
		ws.onclose = function(e) {
		} //연결이 끊어졌을 때
		ws.onmessage = function(e) {
			$("#control").append(e.data + "<br>");
		} // 서버로부터 메세지가 왔을 때

		$("#send").on("click", function() {
			ws.send($("#msg").val());
			$("#msg").val("");
		})
	</script>
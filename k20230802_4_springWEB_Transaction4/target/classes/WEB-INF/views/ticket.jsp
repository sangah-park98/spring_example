<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 결제</title>
</head>
<body>

<p>카드 결제</p>

<form action="ticketCard" method="post">
	구매 아이디: <input type="text" name="consumerId"/><br/>
	티켓 구매수: <input type="text" name="amount"/><br/>
	<input type="submit" value="구매"/>
</form>

</body>
</html>
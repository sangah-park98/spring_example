<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업로드 결과 보기</title>
</head>
<body>
	
<c:forEach var="filename" items="${list}">
	${filename}<br/>
</c:forEach>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title><style type="text/css">
	@font-face {
	    font-family: 'GangwonEdu_OTFBoldA';
	    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEdu_OTFBoldA.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
	
	* {
		font-family:  GangwonEdu_OTFBoldA;
	}
	
	table {
		margin-bottom: 5px;
		background-color: ivory;
	}
	 	
 	.table:nth-child(2n + 1) {
		background-color: white;
	}
	
	a {
		text-decoration: none;
		color: black;
	}
	
	a:hover {
		color: darkgray;
		text-decoration: none;
	}
	 
	.button {
	  background-color: pink;
	  border: none;
	  color: white;
	  padding: 2px;
	  text-align: center;
	  text-decoration: none;
	  display: inline-block;
	  font-size: 14px;
	  margin: 15px 4px;
	  transition-duration: 0.4s;
	  cursor: pointer;

	}

	.button1 {
	  background-color: white; 
	  color: black; 
	  border: 1px dotted pink;
	}

	.button1:hover {
	  background-color: pink;
	  color: black;
	  
	}

	.button2 {
	  background-color: pink; 
	  color: black; 
	  border: 2px solid white;
	  cursor: default;
	}
	
	.button3 {
	  background-color: white; 
	  color: black; 
	  border: 1px solid black;
	  
	}
	
	span {
		color: palevioletred;
		font-weight: bold;
	}
</style>
</head>
<body>
<form action="update" method="post">
	<table width="600" align="center" border="1" cellpadding=5" cellspacing="0">
		<tr>
			<th colspan="4" style="background-color: mistyRose">♥ 게시글 보기 ♥</th>
		</tr>
		<tr>
			<th id="th" style="width: 80px;"><b>글번호</b></th>
			<th id="th" style="width: 290px;"><b>이름</b></th>
			<th id="th" style="width: 150px;"><b>작성일</b></th>
			<th id="th" style="width: 80px;"><b>조회수</b></th>
		</tr>
		<tr>
			<td align="center">${vo.idx}</td>
			<td align="center">
				<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"></c:set>
				<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"></c:set>
				${name}
			</td>
			<td align="center">
				<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/>
			</td>
			<td align="center">${vo.hit}</td>
		</tr>
		
		<tr>
			<th>제목</th>
			<td colspan="3">
				<input type="text" name="subject" value="${vo.subject}" style="width: 98%"/>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="3">
				<textarea rows="10" name="content" style="resize: none; width: 98%">${vo.content}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="수정하기"/>
				<input type="button" value="삭제하기" onclick="location.href='delete?idx=${vo.idx}&currentPage=${currentPage}'"/>
				<input type="button" value="답변하기" onclick="location.href='reply?idx=${vo.idx}&currentPage=${currentPage}'"/>
				<input type="button" value="돌아가기" onclick="location.href='list?currentPage=${currentPage}'"/>
			</td>
		</tr>
	</table>
	<input type="hidden" name="idx" value="${vo.idx}"/>
	<input type="hidden" name="currentPage" value="${currentPage}"/>
	
</form>

</body>
</html>










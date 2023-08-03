<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 보기</title>
<style type="text/css">
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
<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	<table width="1000" align="center" border="1" cellpadding=5" cellspacing="0">
		<tr>
			<th colspan="5" style="font-size: 30px; text-align: center; background-color: mistyRose">
				♥ 게시판 보기 ♥
			</th>
		</tr>
		<tr>
			<td colspan="5" align="right">
				${boardList.totalCount}개(${boardList.currentPage}P / ${boardList.totalPage}P)
			</td>
		</tr>
		<tr>
			<th id="th" style="width: 70px;"><b>글번호</b></th>
			<th id="th" style="width: 610px;"><b>제목</b></th>
			<th id="th" style="width: 100px;"><b>이름</b></th>
			<th id="th" style="width: 150px;"><b>작성일</b></th>
			<th id="th" style="width: 70px;"><b>조회수</b></th>
		</tr>
		<c:set var="list" value="${boardList.list}"/>
		<c:if test="${list.size() == 0}">
			<tr>
				<td colspan="5">
			<marquee>테이블에 저장된 글이 없습니다.</marquee>
				</td>
			</tr>	
		</c:if>
		
		<c:if test="${list.size() != 0}">
		<c:forEach var="vo" items="${list}">
		<tr>
			<td align="center">${vo.idx}</td>
			<!-- 레벨에 따른 들여쓰기 -->
			<td align="center">
				<c:if test="${vo.lev > 0}">
					<c:forEach var="i" begin="1" end="${vo.lev}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</c:forEach>
					<img src="images/arrow.jpg" style="width: 25px;"/>
				</c:if>
				<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt;')}"></c:set>
				<c:set var="subject" value="${fn:replace(subject, '>', '&gt;')}"></c:set>
				<a href="increment?idx=${vo.idx}&currentPage=${boardList.currentPage}">
					${subject}
				</a>
				<c:if test="${vo.hit > 10}">
					<img src="images/hot.gif"/>
				</c:if>
			</td>
				
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
		</c:forEach>	
		</c:if>
				<tr>
			<td colspan="5" align="center">
			
				<c:if test="${boardList.currentPage > 1}">
					<button 
						class='button button1' 
						type="button" 
						title="첫 페이지로 이동합니다." 
						onclick="location.href='?currentPage=1'"
					>처음</button>
				</c:if>
			
				<c:if test="${boardList.currentPage <= 1}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 첫 페이지 입니다."
					>처음</button>
				</c:if>
				
				<c:if test="${boardList.startPage > 1}">
					<button 
						class='button button1' 
						type="button" 
						title="이전 10페이지로 이동합니다." 
						onclick="location.href='?currentPage=${boardList.startPage - 1}'"
					>이전</button>
				</c:if>
				
				<c:if test="${boardList.startPage <= 1}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 첫 10페이지 입니다."
					>이전</button>
				</c:if>
				
				<c:forEach var="i" begin="${boardList.startPage}" end="${boardList.endPage}" step="1">
					<c:if test="${boardList.currentPage == i}">
						<button class='button button2' type='button' disabled='disabled'>${i}</button>
					</c:if>
					<c:if test="${boardList.currentPage != i}">
						<button 
							class='button button1' 
							type='button' 
							title="${i}페이지로 이동합니다."
							onclick="location.href='?currentPage=${i}'"
						>${i}</button>
					</c:if>
				</c:forEach>
				
				<c:if test="${boardList.endPage < boardList.totalPage}">
					<button 
						class='button button1' 
						type="button" 
						title="다음 10페이지로 이동합니다." 
						onclick="location.href='?currentPage=${boardList.endPage + 1}'"
					>다음</button>
				</c:if>
				
				<c:if test="${boardList.endPage >= boardList.totalPage}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 마지막 10페이지 입니다."
					>다음</button>
				</c:if>
				
				<c:if test="${boardList.currentPage < boardList.totalPage}">
				<button
					class='button button1'  
					type="button" 
					title="마지막 페이지로 이동합니다." 
					onclick="location.href='?currentPage=${boardList.totalPage}'"
				>끝</button>
				</c:if>
				
				<c:if test="${boardList.currentPage >= boardList.totalPage}">
					<button 
						class='button button2' 
						type="button" 
						disabled="disabled" 
						title="이미 마지막 페이지 입니다."
					>끝</button>
				</c:if>
				
			</td>
		</tr>
		
		<tr>
			<td colspan="5" align="right">
				<button 
					type="button" 
					onclick="location.href='insert'" 
					style="border: 1px solid pink; background: none; cursor: pointer;"
				>글쓰기</button>
			</td>
		</tr>
	</table>

</body>
</html>













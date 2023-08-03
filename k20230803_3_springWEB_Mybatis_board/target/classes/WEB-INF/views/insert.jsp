<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답변형 게시판 글 입력</title>
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
</head>
<body>

<form action="insertOK" method="post">
	<table width="600" align="center" border="1" cellpadding="5" cellspacing="0">
		<tr>
			<th id="id" colspan="2" style="background: mistyRose">♥ 답변형 게시판 글 입력하기 ♥</th>
		</tr>
		<tr>
			<th width="100">이름</th>
			<td width="500">
				<input type="text" name="name"/>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>
				<input type="text" name="subject" style="width: 98%;"/>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<textarea rows="10" name="content" style="width: 98%; resize: none;"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="submit" style="border: 1px solid pink; background: none; cursor: pointer;">저장하기</button>
				<button type="reset" style="border: 1px solid pink; background: none; cursor: pointer;">다시쓰기</button>
				<button type="button" onclick="history.back()" style="border: 1px solid pink; background: none; cursor: pointer;">돌아가기</button>
			</td>
		</tr>
	</table>
</form>

</body>
</html>
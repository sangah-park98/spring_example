<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
</head>
<body>

<form action="fileUploadResult" method="post" enctype="multipart/form-data">
	<input type="file" name="file1"/><br/>
	<input type="file" name="file2"/><br/>
	<input type="submit" value="file Upload"/>
</form>

</body>
</html>
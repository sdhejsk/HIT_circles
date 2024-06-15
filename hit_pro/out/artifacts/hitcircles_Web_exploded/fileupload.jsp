<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2024/6/14
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/my/update/avatar">
        <input type="file" name="upload">
        <br>
        <input type="text" name="name">
        <input type="text" name="password">
        <input type="submit" value="文件上传">
    </form>
</body>
</html>

<%@ page import="by.training.sokolov.contants.GemAppConstants" %>
<%@ page import="by.training.sokolov.controller.commands.CommandEnum" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <title>Gem Application</title>
</head>
<body>

<h1>Available commands xxx</h1>

<form action="" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="dom">
    <input type="file" name="uploadfile"/>
    <button type="submit">DOM</button>
</form>
<form action="" method="post" enctype="multipart/form-data">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>" value="<%=CommandEnum.SAX_COMMAND.getValue()%>">
    <input type="file" name="uploadfile"/>
    <button type="submit">SAX</button>
</form>
<form action="" method="post" enctype="multipart/form-data">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>" value="<%=CommandEnum.STAX_COMMAND.getValue()%>">
    <input type="file" name="uploadfile"/>
    <button type="submit">STAX</button>
</form>

<h2>Current data</h2>

<c:forEach var="gem" items="${returndata}">
    <br/>
    <c:out value="${gem}"/>
</c:forEach>

</body>
</html>
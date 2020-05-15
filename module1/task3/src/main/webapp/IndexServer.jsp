<%@ page import="by.asite.secondeditionserver.AppConstants" %>
<%@ page import="by.asite.secondeditionserver.commands.CommandName" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>JSP Application</title>
</head>
<body>
<h2>Available commands</h2>
<form method="post" enctype="multipart/form-data">
    <input type="hidden" value="<%=CommandName.ADD_COMMAND_DOM.stringName%>" name="<%=AppConstants.COMMAND_PARAM%>">
    <input type="file" name="uploadfile"/>
    <button type="submit">DOM</button>
</form>
<form method="post" enctype="multipart/form-data">
    <input type="hidden" value="<%=CommandName.ADD_COMMAND_SAX.stringName%>" name="<%=AppConstants.COMMAND_PARAM%>">
    <input type="file" name="uploadfile"/>
    <button type="submit">SAX</button>
</form>
<form method="post" enctype="multipart/form-data">
    <input type="hidden" value="<%=CommandName.ADD_COMMAND_STAX.stringName%>" name="<%=AppConstants.COMMAND_PARAM%>">
    <input type="file" name="uploadfile"/>
    <button type="submit">STAX</button>
</form>
<form method="post">
    <input type="hidden" value="<%=CommandName.DELETE_COMMAND.stringName%>" name="<%=AppConstants.COMMAND_PARAM%>">
    <button type="submit">DELETE ALL</button>
</form>
<h2>Current data</h2>

<c:forEach var="tarif" items="${returndata}">
    <br/>
    <c:out value="${tarif}"/>
</c:forEach>


</body>
</html>
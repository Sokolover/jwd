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

<h2>Choose xml-file and parser type</h2>

<form action="" method="post" enctype="multipart/form-data">
    <p><input type="radio" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
              value="<%=CommandEnum.DOM_COMMAND.getValue()%>"> DOM</p>
    <p><input type="radio" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
              value="<%=CommandEnum.SAX_COMMAND.getValue()%>"> SAX</p>
    <p><input type="radio" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
              value="<%=CommandEnum.STAX_COMMAND.getValue()%>"> StAX</p>
    <p><input type="file" name="uploadfile"/></p>
    <button type="submit">send</button>
</form>

<br/>
<h2>Result</h2>

<table border="1">
    <thead>
    <caption>Gems table</caption>
    <tr>
        <th rowspan="2" class="first">id</th>
        <th rowspan="2">Name</th>
        <th rowspan="2">Preciousness</th>
        <th rowspan="2">Origin</th>
        <th colspan="3">Visual parameters</th>
        <th rowspan="2">Value</th>
    </tr>
    <tr>
        <th class="first">Color</th>
        <th class="first">Transparency</th>
        <th class="first">Number of faces</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${gems}" var="gem">
        <tr>
            <td>${gem.getId()}</td>
            <td>${gem.getName()}</td>
            <td>${gem.getPreciousness()}</td>
            <td>${gem.getOrigin()}</td>
            <td>${gem.getColor()}</td>
            <td>${gem.getTransparency()}</td>
            <td>${gem.getNumberOfFaces()}</td>
            <td>${gem.getValue()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br/>
<c:set var="message" value="${message}" />
<c:out value="${message}"/>

</body>
</html>

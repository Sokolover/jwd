<%@ page import="by.training.sokolov.commands.CommandEnum" %>
<%@ page import="by.training.sokolov.contants.GemAppConstants" %>
<%@ page import="by.training.sokolov.parser.ParserName" %>
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

<br/>
<h4>DOM:</h4>
<form action="" method="post" enctype="multipart/form-data">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_PARSER_NAME%>"
           value="<%=ParserName.DOM.getValue()%>">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
           value="<%=CommandEnum.UPLOAD_COMMAND.getValue()%>">
    <p><input type="file" name="uploadfile"/></p>
    <button type="submit">upload</button>
</form>

<br/>
<h4>SAX:</h4>
<form action="" method="post" enctype="multipart/form-data">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_PARSER_NAME%>"
           value="<%=ParserName.SAX.getValue()%>">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
           value="<%=CommandEnum.UPLOAD_COMMAND.getValue()%>">
    <p><input type="file" name="uploadfile"/></p>
    <button type="submit">upload</button>
</form>

<br/>
<h4>StAX:</h4>
<form action="" method="post" enctype="multipart/form-data">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_PARSER_NAME%>"
           value="<%=ParserName.STAX.getValue()%>">
    <input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
           value="<%=CommandEnum.UPLOAD_COMMAND.getValue()%>">
    <p><input type="file" name="uploadfile"/></p>
    <button type="submit">upload</button>
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

<form action="" method="post" enctype="multipart/form-data">
    <p><input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
              value="<%=CommandEnum.DOWNLOAD_COMMAND.getValue()%>"></p>
    <button type="submit">download</button>
</form>

<br/>
<form action="" method="post" enctype="multipart/form-data">
    <p><input type="hidden" name="<%=GemAppConstants.QUERY_KEY_COMMAND%>"
              value="<%=CommandEnum.DELETE_ALL_COMMAND.getValue()%>"></p>
    <button type="submit">delete all</button>
</form>

<br/>
<c:set var="message" value="${message}"/>
<c:out value="${message}"/>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 30.06.2020
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">

    <jsp:useBean id="msg" scope="request" type="java.lang.String"/>
    <c:out value="${msg}"/>

</div>

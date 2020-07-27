<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 13.07.2020
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty message}">
    <div class="container justifyCenter marginTop">
        <h5 class="title is-5">
            <p><c:out value="${message}"/></p>
        </h5>
    </div>
</c:if>

<c:if test="${not empty error}">
    <div class="container justifyCenter marginTop">
        <h5 class="title is-5">
            <p class="is-danger"><c:out value="${error}"/></p>
        </h5>
    </div>
</c:if>

<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 13.07.2020
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <c:if test="${not empty message}">
        <h5 class="title is-5">
            <c:out value="${message}"/>
        </h5>
    </c:if>
</div>
<div class="container">
    <c:if test="${not empty error}">
        <h5 class="title is-5">
            <p class="is-danger"><c:out value="${error}"/></p>
        </h5>
    </c:if>
</div>

<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 13.07.2020
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <c:if test="${not empty error}">
        <h5 class="title is-5">
            <p class="is-danger"><c:out value="${error}"/></p>
        </h5>
    </c:if>
</div>

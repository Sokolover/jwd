<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty error}">
    <p class="is-danger"><c:out value="${error}"/></p>
</c:if>

<h1>This is index</h1>


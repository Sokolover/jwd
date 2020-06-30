<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>

<div class="container">

    <c:if test="${not empty error}">
        <p class="is-danger"><c:out value="${error}"/></p>
    </c:if>

    <%--    <form action="${pageContext.request.contextPath}/login" method="post">--%>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="_command" value="${CommandType.LOGIN_SUBMIT}">
        <div class="content">

            <div class="control">
                <label for="user.name">
                    <fmt:message key="user.name"/>
                </label>
                <input id="user.name" name="user.name" class="input" type="text">
            </div>

            <div class="control">
                <label for="user.password">
                    <fmt:message key="user.password"/>
                </label>
                <input id="user.password" name="user.password" class="input" type="password">
            </div>

            <fmt:message var="login_label" key="links.login"/>
            <input class="button is-primary" type="submit" value="${login_label}">

        </div>
    </form>
</div>

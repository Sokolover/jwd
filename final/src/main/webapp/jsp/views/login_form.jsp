<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>

<div class="container">

    <h5 class="title is-5">
        <c:if test="${not empty error}">
            <p class="is-danger"><c:out value="${error}"/></p>
        </c:if>
    </h5>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="_command" value="${CommandType.LOGIN_SUBMIT}">
        <div class="content">
            <div class="column is-one-third">
                <div class="control">
                    <label for="user.email">
                        <fmt:message key="user.email"/>
                        <input id="user.email" name="user.email" class="input" type="text">
                    </label>
                </div>
                <div class="control">
                    <label for="user.password">
                        <fmt:message key="user.password"/>
                        <input id="user.password" name="user.password" class="input" type="password">
                    </label>
                </div>
            </div>
            <div class="field is-grouped">
                <div class="control">
                    <fmt:message var="login_label" key="links.login"/>
                    <input class="button is-primary" type="submit" value="${login_label}">
                </div>
            </div>
        </div>
    </form>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.CommandType" %>

<div class="container">

    <c:if test="${not empty error}">
        <p class="is-danger"><c:out value="${error}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/user_register" method="post">
        <input type="hidden" name="_command" value="${CommandType.REGISTER_SUBMIT}">
        <div class="field">

            <label class="label">
                <fmt:message key="user.name"/>
                <div class="control">
                    <input class="input" name="user.name" type="text" placeholder="Text input">
                </div>
            </label>

            <label class="label">
                <fmt:message key="user.password"/>
                <div class="control">
                    <input class="input" name="user.password" type="password" placeholder="Text input">
                </div>
            </label>

            <label class="label">
                <fmt:message key="user.email"/>
                <div class="control">
                    <input class="input" name="user.email" type="text" placeholder="Text input">
                </div>
            </label>

            <label class="label">
                <fmt:message key="user.phoneNumber"/>
                <div class="control">
                    <input class="input" name="user.phoneNumber" type="text" placeholder="Text input">
                </div>
            </label>

            <label class="label">
                <fmt:message key="user.address"/>
                <div class="control">
                    <input class="input" name="user.address" type="text" placeholder="Text input">
                </div>
            </label>

            <div class="field is-grouped">
                <div class="control">
                    <fmt:message var="register_label" key="links.register"/>
                    <input class="button is-primary" type="submit" value="${register_label}">
                </div>
            </div>

        </div>
    </form>
</div>
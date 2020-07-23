<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="loginContainer">

    <c:if test="${not empty error}">
        <p class="title is-5 is-danger">
            <c:out value="${error}"/>
        </p>
    </c:if>

    <h1 class="title is-1">
        <fmt:message var="login" key="app.form.login"/>
        <c:out value="${login}"/>
    </h1>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}" value="${CommandType.LOGIN_SUBMIT}">
        <div class="">
            <div class="field">
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.email"/>
                        <input class="input" id="user.email" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}"
                               type="text" value="qwerty7@m.com">
                        <%--                        <input class="input" id="user.email" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}" type="text">--%>
                    </label>
                </div>
            </div>
            <div class="field">
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.password"/>
                        <input class="input" id="user.password" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}"
                               type="password" value="Qwerty777">
                        <%--                        <input class="input" id="user.password" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}" type="password">--%>
                    </label>
                </div>
            </div>
        </div>
        <div class="field marginTop">
            <div class="control">
                <fmt:message var="loginLabel" key="button.person.login"/>
                <input class="button is-light secondary" type="submit" value="${loginLabel}">
            </div>
        </div>
    </form>
</div>

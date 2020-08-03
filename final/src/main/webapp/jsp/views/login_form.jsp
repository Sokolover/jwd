<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="loginContainer">

<%--    <c:if test="${not empty error}">--%>
<%--        <div class="justifyCenter marginTop">--%>
<%--            <h5 class="title is-5">--%>
<%--                <p class="is-danger"><c:out value="${error}"/></p>--%>
<%--            </h5>--%>
<%--        </div>--%>
<%--    </c:if>--%>

    <c:if test="${not empty message}">
        <div class="justifyCenter marginTop">
            <h5 class="title is-5">
                <p><c:out value="${message}"/></p>
            </h5>
        </div>
    </c:if>

    <jsp:include page="validation_messages.jsp"/>

    <h1 class="title is-1">
        <fmt:message var="login" key="app.form.login"/>
        <c:out value="${login}"/>
    </h1>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="${CommonAppConstants.QUERY_PARAM_COMMAND}" value="${CommandType.LOGIN_USER_SUBMIT}">
        <div class="">
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.email"/>
                    <input class="input" id="user.email" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}"
                           type="text" value="qwerty7@m.com">
                    <%--                        <input class="input" id="user.email" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}" type="text">--%>
                </label>
            </div>
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.password"/>
                    <input class="input" id="user.password" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}"
                           type="password" value="Qwerty777">
                    <%--                        <input class="input" id="user.password" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}" type="password">--%>
                </label>
            </div>
        </div>
        <div class="field control marginTop">
            <fmt:message var="loginLabel" key="button.person.login"/>
            <input class="button is-light secondary" type="submit" value="${loginLabel}">
        </div>
    </form>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="loginContainer">

    <c:if test="${not empty error}">
        <p class="title is-5 is-danger">
            <c:out value="${error}"/>
        </p>
    </c:if>

<%--    <jsp:include page="command_result_message.jsp"/>--%>

    <h1 class="title is-1">
        <fmt:message var="register" key="app.form.register"/>
        <c:out value="${register}"/>
    </h1>

    <form action="${pageContext.request.contextPath}/user_register" method="post">
        <input type="hidden" name="${CommonAppConstants.QUERY_PARAM_COMMAND}" value="${CommandType.REGISTER_USER_SUBMIT}">
        <div class="">
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.name"/>
                    <input class="input" name="${CommonAppConstants.USER_NAME_JSP_PARAM}" type="text"
                           placeholder="Text input">
                </label>
            </div>
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.password"/>
                    <input class="input" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}" type="password"
                           placeholder="Password input">
                </label>
            </div>
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.password.confirm"/>
                    <input class="input" name="${CommonAppConstants.USER_PASSWORD_CONFIRM_JSP_PARAM}" type="password"
                           placeholder="Password input">
                </label>
            </div>
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.email"/>
                    <input class="input" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}" type="text"
                           placeholder="Text input">
                </label>
            </div>
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.phoneNumber"/>
                    <input class="input" name="${CommonAppConstants.USER_PHONE_NUMBER_JSP_PARAM}" type="text"
                           placeholder="Text input">
                </label>
            </div>
            <div class="control field">
                <label class="label">
                    <fmt:message key="user.address"/>
                    <input class="input" name="${CommonAppConstants.USER_ADDRESS_JSP_PARAM}" type="text"
                           placeholder="Text input">
                </label>
            </div>
        </div>
        <div class="control field marginTop">
            <fmt:message var="register_label" key="links.person.register"/>
            <input class="button is-light secondary" type="submit" value="${register_label}">
        </div>
    </form>
</div>
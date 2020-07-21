<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="container">

    <h5 class="title is-5">
        <c:if test="${not empty error}">
            <p class="is-danger"><c:out value="${error}"/></p>
        </c:if>
    </h5>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}" value="${CommandType.LOGIN_SUBMIT}">
        <div class="field">
            <div class="column is-one-third">
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.email"/>
<%--                        <input class="input" id="user.email" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}" type="text" value="qwerty7@m.com">--%>
                        <input class="input" id="user.email" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}" type="text">
                    </label>
                </div>
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.password"/>
<%--                        <input class="input" id="user.password" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}" type="password" value="Qwerty777">--%>
                        <input class="input" id="user.password" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}" type="password">
                    </label>
                </div>
            </div>
            <div class="field is-grouped">
                <div class="control">
                    <fmt:message var="login_label" key="button.person.login"/>
                    <input class="button is-primary" type="submit" value="${login_label}">
                </div>
            </div>
        </div>
    </form>
</div>

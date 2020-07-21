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

    <form action="${pageContext.request.contextPath}/user_register" method="post">
        <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}" value="${CommandType.REGISTER_USER}">
        <div class="field">
            <div class="column is-one-third">
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.name"/>
                        <input class="input" name="${CommonAppConstants.USER_NAME_JSP_PARAM}" type="text" placeholder="Text input">
                    </label>
                </div>
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.password"/>
                        <input class="input" name="${CommonAppConstants.USER_PASSWORD_JSP_PARAM}" type="password" placeholder="Password input">
                    </label>
                </div>
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.email"/>
                        <input class="input" name="${CommonAppConstants.USER_EMAIL_JSP_PARAM}" type="text" placeholder="Text input">
                    </label>
                </div>
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.phoneNumber"/>
                        <input class="input" name="${CommonAppConstants.USER_PHONE_NUMBER_JSP_PARAM}" type="text" placeholder="Text input">
                    </label>
                </div>
                <div class="control">
                    <label class="label">
                        <fmt:message key="user.address"/>
                        <input class="input" name="${CommonAppConstants.USER_ADDRESS_JSP_PARAM}" type="text" placeholder="Text input">
                    </label>
                </div>
            </div>
            <div class="field is-grouped">
                <div class="control">
                    <fmt:message var="register_label" key="button.person.register"/>
                    <input class="button is-primary" type="submit" value="${register_label}">
                </div>
            </div>
        </div>
    </form>
</div>
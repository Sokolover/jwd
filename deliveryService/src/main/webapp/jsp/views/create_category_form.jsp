<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 24.07.2020
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
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

    <div class="title is-3">
        <fmt:message var="createCategory" key="app.form.create.category"/>
        <c:out value="${createCategory}"/>
    </div>

    <form action="${pageContext.request.contextPath}/" method="post">
        <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
               value="${CommandType.CREATE_DISH_CATEGORY_FORM_SUBMIT}">
        <div class="">
            <div class="control field">
                <label class="label">
                    <input class="input" name="${CommonAppConstants.CATEGORY_NAME_JSP_PARAM}"
                           type="text">
                </label>
            </div>
        </div>
        <div class="field control marginTop">
            <fmt:message var="createLabel" key="button.category.create"/>
            <input class="button is-light secondary" type="submit" value="${createLabel}">
        </div>
    </form>
</div>
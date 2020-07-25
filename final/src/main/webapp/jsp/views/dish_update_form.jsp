<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 23.07.2020
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="container feedbackPageContainer">

    <h5 class="title is-5">
        <c:if test="${not empty error}">
            <p class="is-danger"><c:out value="${error}"/></p>
        </c:if>
    </h5>

    <jsp:useBean id="dish" scope="request" type="by.training.sokolov.entity.dish.model.Dish"/>
    <div class="feedbackContainer">
        <form class="feedbackArea" action="${pageContext.request.contextPath}/" method="post"
              enctype="multipart/form-data">
            <input type="hidden"
                   name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                   value="${CommandType.UPDATE_DISH_FORM_SUBMIT}">
            <input type="hidden"
                   name="${CommonAppConstants.DISH_ID_JSP_PARAM}"
                   value="${dish.id}">
            <div class="">
                <div class="">
                    <div class="control field">
                        <label class="label">
                            <fmt:message key="dish.name"/>
                            <input class="input" name="${CommonAppConstants.DISH_NAME_JSP_PARAM}" type="text"
                                   placeholder="Name input" value="${dish.name}">
                        </label>
                    </div>
                    <div class="control field">
                        <div class="label">
                            <fmt:message key="dish.cost"/>
                            <input class="input" name="${CommonAppConstants.DISH_COST_JSP_PARAM}" type="text"
                                   placeholder="Cost input">
                        </div>
                    </div>
                    <div class="control field">
                        <div class="label">
                            <fmt:message key="dish.description"/>
                            <textarea class="textarea" name="${CommonAppConstants.DISH_DESCRIPTION_JSP_PARAM}"
                                      placeholder="Description input" rows="10"></textarea>
                        </div>
                    </div>
                    <div class="file field">
                        <label class="file-label">
                            <input class="file-input" type="file"
                                   name="${CommonAppConstants.DISH_PICTURE_JSP_PARAM}"/>
                            <span class="file-cta">
                            <span class="file-label"><fmt:message key="app.choose.dish.picture"/></span>
                        </span>
                        </label>
                    </div>
                    <label class="label">
                        <fmt:message key="dish.category"/>
                        <div class="select field">
                            <select name="${CommonAppConstants.DISH_CATEGORY_NAME_JSP_PARAM}">
                                <jsp:useBean id="categoryList" scope="request" type="java.util.List"/>
                                <c:forEach items="${categoryList}" var="category">
                                    <option value="${category.categoryName}">${category.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </label>
                </div>

                <div class="control field">
                    <fmt:message var="createLabel" key="button.dish.create"/>
                    <input class="button is-light secondary marginTop" type="submit" value="${createLabel}">
                </div>
            </div>
        </form>

        <jsp:include page="card_dish.jsp"/>
    </div>

</div>

<%--<div class="field">--%>
<%--    <div class="column is-one-third">--%>
<%--        <div class="control">--%>
<%--            <label class="label">--%>
<%--                <fmt:message key="dish.name"/>--%>
<%--                <input class="input" name="${CommonAppConstants.DISH_NAME_JSP_ATTRIBUTE}" type="text"--%>
<%--                       placeholder="Name input">--%>
<%--            </label>--%>
<%--        </div>--%>
<%--        <div class="control">--%>
<%--            <label class="label">--%>
<%--                <fmt:message key="dish.cost"/>--%>
<%--                <input class="input" name="${CommonAppConstants.DISH_COST_JSP_ATTRIBUTE}" type="text"--%>
<%--                       placeholder="Cost input">--%>
<%--            </label>--%>
<%--        </div>--%>
<%--        <label class="label">--%>
<%--            <fmt:message key="dish.description"/>--%>
<%--            <textarea class="textarea" name="${CommonAppConstants.DISH_DESCRIPTION_JSP_ATTRIBUTE}"--%>
<%--                      placeholder="Description input" rows="10"></textarea>--%>
<%--        </label>--%>
<%--        <label class="label">--%>
<%--            <fmt:message key="dish.picture"/>--%>
<%--            <input type="file" name="${CommonAppConstants.DISH_PICTURE_JSP_ATTRIBUTE}"/>--%>
<%--        </label>--%>
<%--        <label class="label">--%>
<%--            <fmt:message key="dish.category"/>--%>
<%--            <select name="${CommonAppConstants.DISH_CATEGORY_JSP_ATTRIBUTE}">--%>
<%--                <jsp:useBean id="categoryList" scope="request" type="java.util.List"/>--%>
<%--                <c:forEach items="${categoryList}" var="category">--%>
<%--                    <option value="${category.categoryName}">${category.categoryName}</option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>
<%--        </label>--%>
<%--    </div>--%>
<%--    <div class="field is-grouped">--%>
<%--        <div class="control">--%>
<%--            <fmt:message var="updateLabel" key="button.dish.update"/>--%>
<%--            <input class="button is-primary" type="submit" value="${updateLabel}">--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

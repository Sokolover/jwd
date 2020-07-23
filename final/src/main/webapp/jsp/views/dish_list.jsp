<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 23.06.2020
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="dishPage">

    <%--    <p class="menu-label">--%>
    <%--        <fmt:message key="app.group.dishes"/>--%>
    <%--    </p>--%>

    <jsp:include page="dish_category.jsp"/>

    <div class="dishContainer">
        <jsp:useBean id="dishes" scope="request" type="java.util.List"/>
        <c:forEach items="${dishes}" var="dish">
            <div class="cardContainer">
                <div class="card">
                    <div class="card-image">
                        <figure class="image is-4by3">
                            <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture"/>
                        </figure>
                    </div>
                    <div class="card-content">
                        <p class="card-header-title">
                            <c:out value="${dish.name}"/>
                        </p>
                        <div class="box">
                            <c:out value="${dish.description}"/>
                        </div>
                        <div class="">
                            <label for="${dish.cost}">
                                <fmt:message key="dish.cost"/>
                            </label>
                            <fmt:message var="currency" key="symbol.currency"/>
                            <c:out value=": ${dish.cost} ${currency}"/>
                        </div>
                        <jsp:useBean id="userLoggedIn" scope="request" type="java.lang.Boolean"/>
                        <c:if test="${userLoggedIn}">

                            <form action="${pageContext.request.contextPath}/order_basket" method="post">
                                <input type="hidden" name="${CommonAppConstants.DISH_ID_JSP_PARAM}"
                                       value="${dish.id}">
                                <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                                       value="${CommandType.ORDER_ITEM_ADD}">
                                <div class="betweenContainer alignItemsCenter">
                                    <label class="label dishAmount">
                                        <fmt:message key="order.item.dishAmount"/>
                                        <div class="control">
                                            <input class="input"
                                                   name="${CommonAppConstants.ORDER_DISH_AMOUNT_JSP_PARAM}"
                                                   value="1"
                                                   type="number"
                                                   step="1"
                                                   min="1"
                                                   max="10">
                                        </div>
                                    </label>

                                    <div class="control">
                                        <fmt:message var="add_label" key="button.dish.add"/>
                                        <input class="button is-light secondary" type="submit" value="${add_label}">
                                    </div>
                                </div>
                            </form>

                            <div class="betweenContainer">
                                <form action="${pageContext.request.contextPath}/menu" method="post">
                                    <div class="control is-centered alignItemsCenter">
                                        <label class="label">
                                            <input type="hidden" name="${CommonAppConstants.DISH_ID_JSP_PARAM}"
                                                   value="${dish.id}">
                                            <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                                                   value="${CommandType.DISH_FEEDBACK_WRITE}">
                                            <fmt:message var="writeFeedback" key="button.feedback.write"/>
                                            <input class="button is-light is-small is-rounded" type="submit"
                                                   value="${writeFeedback}">
                                        </label>
                                    </div>
                                </form>

                                <form action="${pageContext.request.contextPath}/menu" method="post">
                                    <div class="control is-centered">
                                        <label class="label">
                                            <input type="hidden" name="${CommonAppConstants.DISH_ID_JSP_PARAM}"
                                                   value="${dish.id}">
                                            <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                                                   value="${CommandType.UPDATE_DISH_FORM_DISPLAY}">
                                            <fmt:message var="updateDish" key="button.dish.update"/>
                                            <input class="button is-light is-small is-rounded" type="submit"
                                                   value="${updateDish}">
                                        </label>
                                    </div>
                                </form>
                            </div>

                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
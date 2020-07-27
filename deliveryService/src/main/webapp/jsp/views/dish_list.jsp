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
<%@ page import="by.training.sokolov.command.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="dishPage">

    <jsp:include page="dish_category.jsp"/>

    <c:if test="${not empty message}">
        <h5 class="title is-5">
            <c:out value="${message}"/>
        </h5>
    </c:if>

    <div class="dishContainer">
        <jsp:useBean id="dishes" scope="request" type="java.util.List"/>
        <c:forEach items="${dishes}" var="dish">

            <div class="card cardContainer">

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
                    <div class="control field">
                        <label class="" for="${dish.cost}">
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
                                   value="${CommandType.ADD_ITEM_TO_ORDER}">

                            <div class="betweenContainer alignItemsCenter">
                                <div class="dishAmount">
                                    <label class="">
                                        <fmt:message key="order.item.dishAmount"/>
                                    </label>
                                    <div class="control field">
                                        <input class="input"
                                               name="${CommonAppConstants.ORDER_DISH_AMOUNT_JSP_PARAM}"
                                               value="1"
                                               type="number"
                                               step="1"
                                               min="1"
                                               max="10">
                                    </div>
                                </div>

                                <div class="control field">
                                    <fmt:message var="addLabel" key="button.dish.add"/>
                                    <input class="button is-light secondary" type="submit" value="${addLabel}">
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

                            <jsp:useBean id="securityContext" scope="application"
                                         class="by.training.sokolov.context.SecurityContext"/>
                            <c:if test="${securityContext.canExecute(CommandType.UPDATE_DISH_FORM_DISPLAY, sessionId)}">
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
                            </c:if>

                            <c:if test="${securityContext.canExecute(CommandType.DELETE_DISH_FROM_MENU, sessionId)}">
                                <form action="${pageContext.request.contextPath}/menu" method="post">
                                    <div class="control is-centered">
                                        <label class="label">
                                            <input type="hidden" name="${CommonAppConstants.DISH_ID_JSP_PARAM}"
                                                   value="${dish.id}">
                                            <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                                                   value="${CommandType.DELETE_DISH_FROM_MENU}">
                                            <fmt:message var="delete" key="button.dish.delete"/>
                                            <input class="button is-light is-small is-rounded" type="submit"
                                                   value="${delete}">
                                        </label>
                                    </div>
                                </form>
                            </c:if>

                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <jsp:include page="pagination.jsp"/>

</div>
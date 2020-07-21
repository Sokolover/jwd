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

<aside class="menu">

    <p class="menu-label">
        <fmt:message key="app.group.dishes"/>
    </p>

    <ul class="menu-list">
        <jsp:useBean id="dishes" scope="request" type="java.util.List"/>
        <c:forEach items="${dishes}" var="dish">
            <form action="${pageContext.request.contextPath}/order_basket" method="post">
                <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                       value="${CommandType.ORDER_ITEM_ADD}">
                <li>
                    <br>
                    <br>
                    <input type="hidden" name="dish.id" value="${dish.id}">
                    <ul>
                        <li>
                            <c:out value="${dish.name}"/>
                        </li>
                        <br>
                        <li>
                            <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture" width="400"
                                 height="400"/>
                        </li>
                        <br>
                        <li>
                            <label for="${dish.cost}">
                                <fmt:message key="dish.cost"/>
                            </label>
                            <fmt:message var="currency" key="symbol.currency"/>
                            <c:out value=": ${dish.cost} ${currency}"/>
                        </li>
                        <li>
                            <div class="column is-two-thirds">
                                <label for="${dish.description}">
                                    <fmt:message key="dish.description"/>
                                </label>
                                <div class="box">
                                    <c:out value="${dish.description}"/>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <jsp:useBean id="userLoggedIn" scope="request" type="java.lang.Boolean"/>
                    <c:if test="${userLoggedIn}">
                        <div class="column is-one-fifth">
                            <label class="label">
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
                        </div>
                        <div class="control">
                            <fmt:message var="add_label" key="button.dish.add"/>
                            <input class="button is-primary" type="submit" value="${add_label}">
                        </div>
                    </c:if>
                </li>
            </form>
            <c:if test="${userLoggedIn}">
                <form action="${pageContext.request.contextPath}/menu" method="post">
                    <div class="control">
                        <label class="label">
                            <input type="hidden" name="${CommonAppConstants.DISH_ID_JSP_PARAM}" value="${dish.id}">
                            <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                                   value="${CommandType.DISH_FEEDBACK_WRITE}">
                            <fmt:message var="write_feedback" key="button.feedback.write"/>
                            <input class="button is-primary" type="submit" value="${write_feedback}">
                        </label>
                    </div>
                </form>
            </c:if>
        </c:forEach>
    </ul>

</aside>
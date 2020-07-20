<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 29.06.2020
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>

<aside class="menu">

    <p class="menu-label">
        <fmt:message key="app.group.orderItems"/>
    </p>

    <ul class="menu-list">
        <jsp:useBean id="itemList" scope="request" type="java.util.List"/>
        <c:forEach items="${itemList}" var="orderItem">
            <form action="${pageContext.request.contextPath}/order_basket" method="post">
                <input type="hidden" name="_command" value="${CommandType.DELETE_DISH_FROM_ORDER}">
                <li>
                    <br>
                    <br>
                    <input type="hidden" name="orderItem.id" value="${orderItem.id}">
                    <ul>
                        <li>
                            <c:out value="${orderItem.dish.name}"/>
                        </li>
                        <li>
                            <img src="data:image/jpg;base64,${orderItem.dish.picture}" alt="no dish picture" width="400"
                                 height="400"/>
                        </li>
                        <li>
                            <label for="${orderItem.dish.cost}">
                                <fmt:message key="dish.cost"/>
                            </label>
                            <c:out value=": ${orderItem.dish.cost}"/>
                            <fmt:message var="currency" key="symbol.currency"/>
                            <c:out value="${currency}"/>
                        </li>
                        <li>
                            <div class="column is-two-thirds">
                                <label for="${orderItem.dish.description}">
                                    <fmt:message key="dish.description"/>
                                </label>
                                <div class="box">
                                    <c:out value="${orderItem.dish.description}"/>
                                </div>
                            </div>
                        </li>
                        <li>
                            <label for="${orderItem.dishAmount}">
                                <fmt:message key="order.item.dishAmount"/>
                            </label>
                            <c:out value=": ${orderItem.dishAmount}"/>
                        </li>
                        <li>
                            <label for="${orderItem.itemCost}">
                                <fmt:message key="order.item.cost"/>
                            </label>
                            <c:out value=": ${orderItem.itemCost} ${currency}"/>
                        </li>
                    </ul>
                    <div class="control">
                        <fmt:message var="delete_label" key="button.order.item.delete"/>
                        <input class="button is-primary" type="submit" value="${delete_label}">
                    </div>
                </li>
            </form>
        </c:forEach>
    </ul>
</aside>

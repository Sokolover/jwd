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

<aside class="menu">

    <p class="menu-label">
        <fmt:message key="links.group.dishes"/>
    </p>

    <ul class="menu-list">
        <jsp:useBean id="dishes" scope="request" type="java.util.List"/>
        <c:forEach items="${dishes}" var="dish">
            <form action="${pageContext.request.contextPath}/order_basket" method="post">
                <input type="hidden" name="_command" value="${CommandType.ORDER_ITEM_ADD}">
                <li>
                    <input type="hidden" name="dish.id" value="${dish.id}">
                    <ul>
                        <li>
                            <c:out value="${dish.name}"/>
                                <%--                            <input type="hidden" name="dish.name" value="${dish.name}">--%>
                        </li>
                        <li>
                            <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture"/>
                                <%--                            <input type="hidden" name="dish.picture" value="${dish.picture}">--%>
                        </li>
                        <li>
                            <label for="${dish.cost}">
                                <fmt:message key="dish.cost"/>
                            </label>
                            <c:out value="${dish.cost}"/>
                                <%--                            <input type="hidden" name="dish.cost" value="${dish.cost}">--%>
                        </li>
                        <li>
                            <label for="${dish.description}">
                                <fmt:message key="dish.description"/>
                            </label>
                            <c:out value="${dish.description}"/>
                                <%--                            <input type="hidden" name="dish.description" value="${dish.description}">--%>
                        </li>
                    </ul>

                    <jsp:useBean id="userLoggedIn" scope="request" type="java.lang.Boolean"/>
                    <c:if test="${userLoggedIn}">
                        <label class="label">
                            <fmt:message key="order.menu.amount"/>
                            <div class="control">
                                    <%--                            <input class="input" name="order.dish.amount" type="text" placeholder="input amount">--%>
                                <input class="input" name="order.dish.amount" value="1" type="number" step="1" min="1"
                                       max="10" autocomplete="on">
                            </div>
                        </label>

                        <div class="control">
                            <fmt:message var="add_label" key="links.dish.add"/>
                            <input class="button is-primary" type="submit" value="${add_label}">
                        </div>
                    </c:if>
                </li>
            </form>
        </c:forEach>
    </ul>

</aside>
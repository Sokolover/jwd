<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 05.07.2020
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>


<div class="container">

    <%--таблица блюд--%>
    <table class="table is-striped">
        <thead>
        <tr>
            <th>№</th>
            <th>dish name</th>
            <th>dish cost</th>
            <th>dish amount</th>
            <th>item cost</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="i" value="1"/>
        <jsp:useBean id="itemList" scope="request" type="java.util.List"/>
        <c:forEach items="${itemList}" var="item">
            <tr>
                <td><c:out value="${i}"/>
                <td><c:out value="${item.dish.name}"/>
                <td><c:out value="${item.dish.cost}"/>
                <td><c:out value="${item.dishAmount}"/>
                <td><c:out value="${item.itemCost}"/>
            </tr>
            <c:set var="i" value="${i + 1}"/>
        </c:forEach>
        </tbody>
    </table>

    <%--сумма к оплате--%>
    <%--    <label class="label">--%>
    <%--        <fmt:message key="order.cost"/>--%>
    <jsp:useBean id="totalCost" scope="request" type="java.math.BigDecimal"/>
    <label class="label">
        <fmt:message key="order.cost"/>
        <c:set var="totalCost" value="${totalCost}"/>
        <c:out value="${totalCost}"/>
    </label>
    <%--    </label>--%>

    <form action="${pageContext.request.contextPath}/order_checkout" method="post">
        <input type="hidden" name="_command" value="${CommandType.CHECKOUT_ORDER_FORM_SUBMIT}">
        <div class="field">
            <%--ввод контактной информации--%>
            <fmt:message key="app.contact.info"/>
            <%--            <h4 class="title"><c:out value="app.contact.info"/></h4>--%>
            <label class="label">
                <fmt:message key="user.name"/>
                <div class="control">
                    <input class="input" name="user.name" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="label">
                <fmt:message key="user.phoneNumber"/>
                <div class="control">
                    <input class="input" name="user.phoneNumber" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="checkbox">
                <input type="checkbox" name="default.user.name" value="users">
                send user name
            </label>
            <label class="checkbox">
                <input type="checkbox" name="default.user.phoneNumber" value="users">
                send user phone number
            </label>
            <%--ввод времени доставки--%>
            <label class="label">
                <fmt:message key="order.timeOfDelivery"/>
                <%--            <h4 class="title"><c:out value="order.timeOfDelivery"/></h4>--%>
                <%--                попытка 1--%>
                <%--            <label class="label">--%>
                <%--                <fmt:message key="order.timeOfDelivery"/>--%>
                <%--                <div class="control">--%>
                <%--                    <input class="input" name="order.timeOfDelivery" type="text" placeholder="Text input">--%>
                <%--                </div>--%>
                <%--            </label>--%>
                <%--                попытка 2--%>
                <%--            <div class="dropdown">--%>
                <%--                <div class="dropdown-trigger">--%>
                <%--                    <button class="button" aria-haspopup="true" aria-controls="dropdown-menu" type="button">--%>
                <%--                        <span>Delivery time</span>--%>
                <%--                        &lt;%&ndash;                        <span class="icon is-small">&ndash;%&gt;--%>
                <%--                        &lt;%&ndash;                            <i class="fas fa-angle-down" aria-hidden="true"></i>&ndash;%&gt;--%>
                <%--                        &lt;%&ndash;                        </span>&ndash;%&gt;--%>
                <%--                    </button>--%>
                <%--                </div>--%>
                <%--                <div class="dropdown-menu" id="dropdown-menu" role="menu">--%>
                <%--                    <div class="dropdown-content">--%>
                <%--                        <button class="dropdown-item is-focused" name="order.timeOfDelivery" type="button"--%>
                <%--                                value="12:00">--%>
                <%--                        </button>--%>
                <%--                        <button class="dropdown-item is-focused" name="order.timeOfDelivery" type="button"--%>
                <%--                                value="15:00">--%>
                <%--                        </button>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <%--            </div>--%>
<%--                fixme сделать заказы на следующий день если на этот уже нет--%>
<%--                 или обработать исключение что на сегодня заказывать мольше нельзя--%>
                <select name="order.timeOfDelivery">
                    <jsp:useBean id="timeList" scope="request" type="java.util.List"/>
                    <c:forEach items="${timeList}" var="time">
                        <option value="${time}">${time}</option>
                    </c:forEach>
                </select>
            </label>
            <%--ввод адреса--%>
            <fmt:message key="app.delivery.address"/>
            <%--            <h4 class="title"><c:out value="app.delivery.address"/></h4>--%>
            <label class="label">
                <fmt:message key="order.address.locality"/>
                <div class="control">
                    <input class="input" name="order.address.locality" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="label">
                <fmt:message key="order.address.street"/>
                <div class="control">
                    <input class="input" name="order.address.street" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="label">
                <fmt:message key="order.address.buildingNumber"/>
                <div class="control">
                    <input class="input" name="order.address.buildingNumber" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="label">
                <fmt:message key="order.address.flatNumber"/>
                <div class="control">
                    <input class="input" name="order.address.flatNumber" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="label">
                <fmt:message key="order.address.porch"/>
                <div class="control">
                    <input class="input" name="order.address.porch" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="label">
                <fmt:message key="order.address.floor"/>
                <div class="control">
                    <input class="input" name="order.address.floor" type="text" placeholder="Text input">
                </div>
            </label>
            <label class="checkbox">
                <input type="checkbox" name="default.order.address" value="users">
                send user address
            </label>
        </div>
        <div class="field is-grouped">
            <div class="control">
                <fmt:message var="checkout_label" key="links.checkout"/>
                <input class="button is-primary" type="submit" value="${checkout_label}">
            </div>
        </div>
    </form>

</div>


<%--кнопка оформить--%>

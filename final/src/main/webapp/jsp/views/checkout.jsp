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
<%@ taglib prefix="f" uri="/WEB-INF/tld/format/timeFormat.tld" %>

<div class="container">

    <div class="columns">

        <%--dish table--%>
        <div class="column is-half">
            <fmt:message var="orderList" key="order.item.list"/>
            <h5 class="title is-5">
                <c:out value="${orderList}"/>
            </h5>
            <fmt:message var="dishName" key="dish.name"/>
            <fmt:message var="dishCost" key="dish.cost"/>
            <fmt:message var="dishAmount" key="order.item.dishAmount"/>
            <fmt:message var="itemCost" key="order.item.cost"/>

            <table class="table is-striped">
                <thead>
                <tr>
                    <th>№</th>
                    <th><c:out value="${dishName}"/></th>
                    <th><c:out value="${dishCost}"/></th>
                    <th><c:out value="${dishAmount}"/></th>
                    <th><c:out value="${itemCost}"/></th>
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
            <%--total cost--%>
            <jsp:useBean id="totalCost" scope="request" type="java.math.BigDecimal"/>
            <c:set var="totalCost" value="${totalCost}"/>
            <fmt:message var="orderCostString" key="order.cost"/>
            <fmt:message var="currency" key="symbol.currency"/>
            <h5 class="title is-5">
                <c:out value="${orderCostString}: ${totalCost} ${currency}"/>
            </h5>
        </div>

        <div class="column is-one-quarter">
            <%--input contact info--%>
            <form action="${pageContext.request.contextPath}/order_checkout" method="post">
                <input type="hidden" name="_command" value="${CommandType.CHECKOUT_ORDER_FORM_SUBMIT}">
                <div class="field">
                    <fmt:message var="contact_info" key="order.contactInfo"/>
                    <h5 class="title is-5">
                        <c:out value="${contact_info}"/>
                    </h5>
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

                    <fmt:message var="sendUserName" key="lable.checkbox.sendUserName"/>
                    <label class="checkbox">
                        <input type="checkbox" name="default.user.name" value="user_s">
                        <c:out value="${sendUserName}"/>
                    </label>

                    <fmt:message var="sendUserPhoneNumber" key="lable.checkbox.sendUserPhoneNumber"/>
                    <label class="checkbox">
                        <input type="checkbox" name="default.user.phoneNumber" value="user_s">
                        <c:out value="${sendUserPhoneNumber}"/>
                    </label>

                    <br>
                    <br>

                    <%--input time of delivery--%>
                    <fmt:message var="timeOfDelivery" key="order.timeOfDelivery"/>
                    <h5 class="title is-5">
                        <c:out value="${timeOfDelivery}"/>
                    </h5>
                    <label class="label">
                        <select name="order.timeOfDelivery">
                            <jsp:useBean id="timeList" scope="request" type="java.util.List"/>
                            <c:forEach items="${timeList}" var="time">
                                <option value="${time}">
                                        ${f:formatLocalDateTime(time, 'MM.dd HH:mm')}
                                </option>
                            </c:forEach>
                        </select>
                    </label>

                    <br>

                    <fmt:message var="deliveryAddress" key="order.deliveryAddress"/>
                    <h5 class="title is-5">
                        <c:out value="${deliveryAddress}"/>
                    </h5>

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
                            <input class="input" name="order.address.buildingNumber" type="text"
                                   placeholder="Text input">
                        </div>
                    </label>

                    <label class="label">
                        <fmt:message key="order.address.flatNumber"/>
                        <div class="control">
                            <input class="input" name="order.address.flatNumber" type="text"
                                   placeholder="Text input">
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

                    <fmt:message var="sendUserAddress" key="lable.checkbox.sendUserAddress"/>
                    <label class="checkbox">
                        <input type="checkbox" name="default.order.address" value="user_s">
                        <c:out value="${sendUserAddress}"/>
                    </label>
                </div>

                <div class="field is-grouped">
                    <div class="control">
                        <fmt:message var="checkoutLabel" key="button.checkout"/>
                        <input class="button is-primary" type="submit" value="${checkoutLabel}">
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>




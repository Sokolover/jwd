<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 23.07.2020
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="dishContainer">
    <div class="cardContainer">
        <jsp:useBean id="dish" scope="request" type="by.training.sokolov.entity.dish.model.Dish"/>
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
            </div>
        </div>
    </div>
</div>


<%--<div class="column is-half">--%>
<%--    <div class="card-content">--%>
<%--        <ul>--%>
<%--            <li>--%>
<%--                <c:out value="${dish.name}"/>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture" width="400"--%>
<%--                     height="400"/>--%>
<%--            </li>--%>
<%--            <li>--%>
<%--                <div class="column is-two-thirds">--%>
<%--                    <label for="${dish.description}">--%>
<%--                        <fmt:message key="dish.description"/>--%>
<%--                    </label>--%>
<%--                    <div class="box">--%>
<%--                        <c:out value="${dish.description}"/>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--            <div class="">--%>
<%--                <label for="${dish.cost}">--%>
<%--                    <fmt:message key="dish.cost"/>--%>
<%--                </label>--%>
<%--                <fmt:message var="currency" key="symbol.currency"/>--%>
<%--                <c:out value=": ${dish.cost} ${currency}"/>--%>
<%--            </div>--%>
<%--        </ul>--%>
<%--    </div>--%>
<%--</div>--%>

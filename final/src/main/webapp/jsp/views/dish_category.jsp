<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 23.06.2020
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="categoryContainer">

    <p class="menu-label">
        <fmt:message key="app.group.category"/>
    </p>

    <div class="categoryAddContainer">
    <form action="" method="GET" class="">
<%--        todo сделать checked чекбоксов в выбранных категориях (написать сравнение ЗНАЧЕНИЙ строк на jsp)--%>
<%--        <c:forEach items="${selectedCategories}" var="selectedCategory">--%>
<%--            <c:forEach items="${categoryList}" var="category">--%>
<%--                <c:when test="${category eq selectedCategory}">--%>
<%--                    <input type="checkbox" checked name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"--%>
<%--                           value="${category.categoryName}"/>--%>
<%--                    ${category.categoryName}--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <input type="checkbox" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"--%>
<%--                           value="${category.categoryName}"/>--%>
<%--                    ${category.categoryName}--%>
<%--                </c:otherwise>--%>
<%--            </c:forEach>--%>
<%--        </c:forEach>--%>

        <c:forEach items="${categoryList}" var="category">
            <input type="checkbox" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"
                   value="${category.categoryName}"/>
            ${category.categoryName}
        </c:forEach>

        <fmt:message var="ok" key="button.category.ok"/>
        <input class="button is-light secondary" type="submit" value="${ok}"/>

    </form>

    <fmt:message var="all" key="button.category.reset"/>
    <form action="" method="get">
        <input type="hidden" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"
               value="all">
        <input class="button is-light secondary" type="submit" value="${all}"/>
    </form>
    </div>
</div>

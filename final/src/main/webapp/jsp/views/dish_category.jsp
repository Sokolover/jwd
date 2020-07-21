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

<aside class="menu">

    <p class="menu-label">
        <fmt:message key="app.group.category"/>
    </p>

    <ul class="menu-list">

        <form action="" method="GET">
            <jsp:useBean id="categoryList" scope="request" type="java.util.List"/>
            <c:forEach items="${categoryList}" var="category">
                <li>
                    <input type="checkbox" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"
                           value="${category.categoryName}"/>
                        ${category.categoryName}
                </li>
            </c:forEach>

            <br>
            <br>

            <fmt:message var="ok" key="button.category.ok"/>
            <input class="button is-primary" type="submit" value="${ok}"/>

            <fmt:message var="reset" key="button.category.reset"/>
            <input class="button is-primary" type="reset" value="${reset}"/>

        </form>

        <fmt:message var="all" key="button.category.all"/>
        <form action="" method="get">
            <input type="hidden" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"
                   value="all">
            <input class="button is-primary" type="submit" value="${all}"/>
        </form>

    </ul>
</aside>

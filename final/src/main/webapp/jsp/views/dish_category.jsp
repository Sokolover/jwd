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
        <form action="${pageContext.request.contextPath}/menu" method="get">
            <%--        <form action="" method="get">--%>

            <%--                    todo сделать checked чекбоксов в выбранных категориях (написать сравнение ЗНАЧЕНИЙ строк на jsp)--%>
            <%--            <jsp:useBean id="selectedCategories" scope="request" type="java.util.List"/>--%>
            <%--            <jsp:useBean id="categoryList" scope="request" type="java.util.List"/>--%>
            <%--            <jsp:useBean id="flag" type="java.lang.Boolean"/>--%>
            <%--            <c:choose>--%>
            <%--                <c:when test="${not empty selectedCategories}">--%>
            <%--                    <c:forEach items="${categoryList}" var="category">--%>
            <%--                        <jsp:setProperty name="flag" property="false"/>--%>
            <%--                        <c:forEach items="${selectedCategories}" var="selectedCategory">--%>
            <%--                            <c:if test="${category eq selectedCategory}">--%>
            <%--                                <jsp:setProperty name="flag" property="true"/>--%>
            <%--                            </c:if>--%>
            <%--                        </c:forEach>--%>
            <%--                        <c:choose>--%>
            <%--                            <c:when test="${flag}">--%>
            <%--                                <input type="checkbox" checked name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"--%>
            <%--                                       value="${category.categoryName}"/>--%>
            <%--                                ${category.categoryName}--%>
            <%--                            </c:when>--%>
            <%--                            <c:otherwise>--%>
            <%--                                <input type="checkbox" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"--%>
            <%--                                       value="${category.categoryName}"/>--%>
            <%--                                ${category.categoryName}--%>
            <%--                            </c:otherwise>--%>
            <%--                        </c:choose>--%>
            <%--                    </c:forEach>--%>
            <%--                </c:when>--%>
            <%--&lt;%&ndash;                <c:otherwise>&ndash;%&gt;--%>
            <%--&lt;%&ndash;                    <c:forEach items="${categoryList}" var="category">&ndash;%&gt;--%>
            <%--&lt;%&ndash;                        <input type="checkbox" name="${CommonAppConstants.QUERY_CATEGORY_PARAM}"&ndash;%&gt;--%>
            <%--&lt;%&ndash;                               value="${category.categoryName}"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;                        ${category.categoryName}&ndash;%&gt;--%>
            <%--&lt;%&ndash;                    </c:forEach>&ndash;%&gt;--%>
            <%--&lt;%&ndash;                </c:otherwise>&ndash;%&gt;--%>
            <%--            </c:choose>--%>

            <c:forEach items="${categoryList}" var="category">
                <input type="checkbox" name="${CommonAppConstants.QUERY_PARAM_CATEGORY}"
                       value="${category.categoryName}"/>
                ${category.categoryName}
            </c:forEach>

            <fmt:message var="ok" key="button.category.ok"/>
            <input class="button is-light secondary" type="submit" value="${ok}"/>

        </form>

        <form action="${pageContext.request.contextPath}/menu" method="get">
            <input type="hidden" name="${CommonAppConstants.QUERY_PARAM_CATEGORY}"
                   value="all">
            <fmt:message var="all" key="button.category.reset"/>
            <input class="button is-light secondary" type="submit" value="${all}"/>
        </form>

    </div>
</div>

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

<aside class="menu">

    <ul class="menu-list">
        <jsp:useBean id="categoryList" scope="request" type="java.util.List"/>
        <form action="" method="GET">
            <c:forEach items="${categoryList}" var="category">
                <li>
                    <input type="checkbox" name="_category" value="${category.categoryName}"/>
                        ${category.categoryName}
                </li>
            </c:forEach>
            <input type="submit" value="OK"/>
            <input type="reset" value="Reset"/>
        </form>
    </ul>

</aside>

<%--<c:set var="count" value="0" scope="page"/>--%>
<%--<a href="?${pageContext.request.queryString}&_category${count}=<c:out value="${category.categoryName}"/>">--%>
<%--    ${category.categoryName}--%>
<%--</a>--%>
<%--<c:set var="count" value="${count + 1}" scope="page"/>--%>

<%--<form name="travel" action="http://ab-w.net/info.php" method="get">--%>
<%--    <input type="checkbox" name="transport" value="airplane" checked="checked"/> Самолет<br/>--%>
<%--    <input type="checkbox" name="transport" value="train"/> Поезд<br/>--%>
<%--    <input type="checkbox" name="transport" value="car"/> Автомобиль<br/>--%>
<%--    <input type="checkbox" name="transport" value="bus"/> Автобус<br/>--%>
<%--    <input type="submit" value="OK"/>--%>
<%--    <input type="reset" value="Reset"/>--%>
<%--</form>--%>
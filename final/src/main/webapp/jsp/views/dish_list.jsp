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

<aside class="menu">

    <p class="menu-label">
        <fmt:message key="links.group.dishes"/>
    </p>

    <ul class="menu-list">
        <jsp:useBean id="dishList" scope="request" type="java.util.List"/>
        <c:forEach items="${dishList}" var="dish">
            <form action="${pageContext.request.contextPath}/" method="post">
                <input type="hidden" name="_command" value="${CommandType.INDEX}">
                <li>
                    <ul>
                        <li>
                            <c:out value="${dish.name}"/>
                        </li>
                        <li>
                            <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture"/>
                        </li>
                        <li>
                            <label for="${dish.cost}">
                                <fmt:message key="dish.cost"/>
                            </label>
                            <c:out value="${dish.cost}"/>
                        </li>
                        <li>
                            <label for="${dish.description}">
                                <fmt:message key="dish.description"/>
                            </label>
                            <c:out value="${dish.description}"/>
                        </li>
                    </ul>

                    <label class="label">
                        <fmt:message key="order.menu.amount"/>
                        <div class="control">
                            <input class="input" name="order.menu.amount" type="text" placeholder="input amount">
                        </div>
                    </label>

                    <div class="control">
                        <fmt:message var="add_label" key="links.dish.add"/>
                        <input class="button is-primary" type="submit" value="${add_label}">
                    </div>
                </li>
            </form>
        </c:forEach>
    </ul>

</aside>
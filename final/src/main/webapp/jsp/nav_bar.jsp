<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.sokolov.command.CommandType" %>

<aside class="menu">
    <p class="menu-label">
        <fmt:message key="links.group.navigation"/>
    </p>
    <jsp:useBean id="securityContext" scope="application" class="by.training.sokolov.SecurityContext"/>
    <ul class="menu-list">
        <jsp:useBean id="flag" scope="request" type="java.lang.Boolean"/>
        <%--        <jsp:useBean id="sessionId" scope="request" type="java.lang.String"/>--%>
        <c:choose>
            <c:when test="${flag}">
                <%--                todo сделать чтобы работал метод securityContext.canExecute, придумать как передать в него sessionId из jsp--%>
                <%--                <c:if test="${securityContext.canExecute(CommandType.VIEW_USER_LIST)}">--%>
                <%--                    <li>--%>
                <%--                        <a href="?_command=${CommandType.VIEW_USER_LIST}"><fmt:message key="links.person.list"/></a>--%>
                <%--                    </li>--%>
                <%--                </c:if>--%>
                <li>
                    <a href="?_command=${CommandType.LOGOUT}"><fmt:message key="links.person.logout"/></a>
                </li>
                <li>
                    <a href="?_command=${CommandType.DISH_MENU_DISPLAY}"><fmt:message
                            key="links.dish.menu"/></a>
                </li>
                <li>
                    <a href="?_command=${CommandType.BASKET_DISPLAY}"><fmt:message key="links.basket.display"/></a>
                </li>
                <li>
                    <a href="?_command=${CommandType.CREATE_ORDER}"><fmt:message key="links.order.create"/></a>
                </li>
                <li>
                    <a href="?_command=${CommandType.BASKET_DISPLAY}"><fmt:message key="links.order.display"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="?_command=${CommandType.LOGIN_DISPLAY}"><fmt:message key="links.person.login"/></a>
                </li>
                <li>
                    <a href="?_command=${CommandType.DISH_MENU_DISPLAY}"><fmt:message
                            key="links.dish.menu"/></a>
                </li>
                <li>
                    <a href="?_command=${CommandType.REGISTER_DISPLAY}"><fmt:message key="links.register"/></a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</aside>
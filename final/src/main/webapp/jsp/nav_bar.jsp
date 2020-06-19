<%--
  Created by IntelliJ IDEA.
  User: nbu
  Date: 2019-02-11
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.sokolov.command.CommandType" %>

<aside class="menu">
    <p class="menu-label">
        <fmt:message key="links.group.general"/>
    </p>
    <jsp:useBean id="securityContext" scope="application" class="by.training.sokolov.SecurityContext"/>
    <ul class="menu-list">
        <c:if test="${securityContext.canExecute(CommandType.VIEW_USER_LIST)}">
            <li>
                <a href="?_command=${CommandType.VIEW_USER_LIST}"><fmt:message key="links.person.list"/></a>
            </li>
        </c:if>
        <c:if test="${securityContext.canExecute(CommandType.CREATE_USER)}">
            <li>
                <a href="?_command=${CommandType.CREATE_USER}"><fmt:message key="links.person.create"/></a>
            </li>
        </c:if>
        <c:choose>
            <c:when test="${securityContext.loggedIn}">
                <li>
                    <a href="?_command=${CommandType.LOGOUT}"><fmt:message key="links.person.logout"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="?_command=${CommandType.LOGIN_DISPLAY}"><fmt:message key="links.person.login"/></a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</aside>
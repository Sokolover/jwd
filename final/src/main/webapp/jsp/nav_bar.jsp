<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<aside class="menu">

    <%--    <p class="menu-label">--%>
    <%--        <fmt:message key="app.group.navigation"/>--%>
    <%--    </p>--%>

    <jsp:useBean id="securityContext" scope="application" class="by.training.sokolov.core.context.SecurityContext"/>
    <div class="navbar-brand">
        <jsp:useBean id="userLoggedIn" scope="request" type="java.lang.Boolean"/>
        <c:choose>
            <c:when test="${userLoggedIn}">
                <div class="navbar-start">
                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.MENU_SERVLET_SWITCH}"><fmt:message
                            key="links.dish.menu"/></a>

                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.ORDER_BASKET_SERVLET_SWITCH}"><fmt:message
                            key="links.basket.display"/></a>


                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.CREATE_ORDER}"><fmt:message
                            key="links.order.create"/></a>


                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.ORDER_CHECKOUT_SERVLET_SWITCH}"><fmt:message
                            key="links.order.checkout"/></a>

                </div>
                <div class="navbar-end"><a class="navbar-item button is-light secondary"
                                           href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.LOGOUT}"><fmt:message
                        key="links.person.logout"/></a>
                </div>

                <c:if test="${securityContext.canExecute(CommandType.CREATE_DISH_FORM_DISPLAY, sessionId)}">

                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.CREATE_DISH_FORM_DISPLAY}"><fmt:message
                            key="links.dish.create"/></a>

                </c:if>
            </c:when>
            <c:otherwise>
                <div class="navbar-start">
                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.MENU_SERVLET_SWITCH}"><fmt:message
                            key="links.dish.menu"/></a>

                </div>
                <div class="navbar-end">
                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.REGISTER_SERVLET_SWITCH}"><fmt:message
                            key="links.person.register"/></a>


                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_COMMAND_PARAM}=${CommandType.LOGIN_SERVLET_SWITCH}"><fmt:message
                            key="links.person.login"/></a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</aside>
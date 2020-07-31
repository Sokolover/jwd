<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.sokolov.command.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<aside class="menu">

    <jsp:useBean id="securityContext" scope="application" class="by.training.sokolov.context.SecurityContext"/>
    <div class="navbar-brand">
        <c:choose>
            <c:when test="${userLoggedIn}">
                <div class="navbar-start navBarContainer">
                    <div class="navbar navBarBackground">

                        <a class="navbar-item button is-light secondary"
                           href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.MENU_SERVLET_SWITCH}"><fmt:message
                                key="links.dish.menu"/></a>

                            <%--                        <form action="${pageContext.request.contextPath}/menu" method="get">--%>
                            <%--                            <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"--%>
                            <%--                                   value="${CommandType.DISH_MENU_DISPLAY}">--%>
                            <%--                            <input type="hidden" name="currentPage" value="1">--%>
                            <%--                            <button class="navbar-item button is-light secondary" type="submit" class="btn btn-primary">--%>
                            <%--                                <fmt:message key="links.dish.menu"/>--%>
                            <%--                            </button>--%>
                            <%--                        </form>--%>

                        <a class="navbar-item button is-light secondary"
                           href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.ORDER_BASKET_SERVLET_SWITCH}"><fmt:message
                                key="links.basket.display"/></a>

                        <a class="navbar-item button is-light secondary"
                           href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.CREATE_ORDER}"><fmt:message
                                key="links.order.create"/></a>

                        <a class="navbar-item button is-light secondary"
                           href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.DELETE_ORDER}"><fmt:message
                                key="links.order.delete"/></a>

                        <a class="navbar-item button is-light secondary"
                           href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.ORDER_CHECKOUT_SERVLET_SWITCH}"><fmt:message
                                key="links.order.checkout"/></a>
                    </div>

                    <div class="navbar navBarBackground marginTop">
                        <c:if test="${securityContext.canExecute(CommandType.CREATE_DISH_CATEGORY_FORM_DISPLAY, sessionId)}">
                            <a class="navbar-item button is-light secondary"
                               href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.CREATE_DISH_CATEGORY_FORM_DISPLAY}"><fmt:message
                                    key="links.category.create"/></a>
                        </c:if>

                        <c:if test="${securityContext.canExecute(CommandType.CREATE_DISH_FORM_DISPLAY, sessionId)}">
                            <a class="navbar-item button is-light secondary"
                               href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.CREATE_DISH_FORM_DISPLAY}"><fmt:message
                                    key="links.dish.create"/></a>
                        </c:if>
                    </div>
                </div>

                <div class="navbar-end"><a class="navbar-item button is-light secondary"
                                           href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.LOGOUT}"><fmt:message
                        key="links.person.logout"/></a>
                </div>

            </c:when>
            <c:otherwise>
                <div class="navbar-start">
                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.MENU_SERVLET_SWITCH}"><fmt:message
                            key="links.dish.menu"/></a>

                </div>
                <div class="navbar-end">
                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.REGISTER_SERVLET_SWITCH}"><fmt:message
                            key="links.person.register"/></a>


                    <a class="navbar-item button is-light secondary"
                       href="?${CommonAppConstants.QUERY_PARAM_COMMAND}=${CommandType.LOGIN_SERVLET_SWITCH}"><fmt:message
                            key="links.person.login"/></a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</aside>
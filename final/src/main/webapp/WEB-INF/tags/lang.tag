<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar">
    <div class="navbar-menu">
        <div class="navbar-end">
            <c:forTokens items="en,ru" delims="," var="lang">
                <span class="navbar-item">
                    <a class="button is-light is-inverted" href="?lang=${lang}">
                        <span><fmt:message key="links.lang.${lang}"/></span>
                    </a>
                </span>
            </c:forTokens>
        </div>
    </div>
</nav>

<%--<c:choose>--%>
<%--    <c:when test="${not empty requestScope.get('lang')}">--%>
<%--        <fmt:setLocale value="${requestScope.get('lang')}"/>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <fmt:setLocale value="${cookie['lang'].value}"/>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>

<%--<nav class="navbar">--%>
<%--    <div class="navbar-menu">--%>
<%--        <div class="navbar-end">--%>
<%--            <c:choose>--%>
<%--                <c:when test="${not empty pageContext.request.queryString}">--%>
<%--                    <c:set value="${pageContext.request.queryString}" var="query"/>--%>
<%--                    <c:if test="${query.contains('lang')}">--%>
<%--                        <c:set var="startIndex" value="${query.indexOf('lang') - 1}"/>--%>
<%--                        <c:set var="query" value="${query.replace('lang='.concat(requestScope['lang']), '')}"/>--%>
<%--                    </c:if>--%>
<%--                    <c:set var="url"--%>
<%--                           value="${pageContext.request.contextPath}?${query}&lang="/>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <c:set var="url" value="${pageContext.request.contextPath}?lang="/>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>

<%--            <c:forTokens items="en,ru,de" delims="," var="lang">--%>
<%--                <span class="navbar-item">--%>
<%--                    <a class="button is-light is-inverted" href="${url}?lang=${lang}">--%>
<%--                        <span><fmt:message key="links.lang.${lang}"/></span>--%>
<%--                    </a>--%>
<%--                </span>--%>
<%--            </c:forTokens>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</nav>--%>

<%--<c:choose>--%>
<%--    <c:when test="${not empty requestScope.get('lang')}">--%>
<%--        <fmt:setLocale value="${requestScope.get('lang')}"/>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <fmt:setLocale value="${cookie['lang'].value}"/>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>



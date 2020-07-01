<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="lang" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bulma.css">
</head>
<body>

<c:choose>
    <c:when test="${not empty requestScope.get('lang')}">
        <fmt:setLocale value="${requestScope.get('lang')}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="/i18n/ApplicationMessages" scope="application"/>
<section class="hero is-dark">
    <div class="hero-head">
        <lang:lang/>
    </div>
    <div class="hero-body">
        <div class="container">
            <h1 class="title">
                <fmt:message key="app.title"/>
            </h1>
            <h2 class="subtitle">
                <fmt:message key="app.subtitle"/>
            </h2>
        </div>
    </div>
</section>
<section class="is-fullheight is-medium">
    <div class="columns">
        <div class="column is-one-quarter">
            <jsp:include page="nav_bar.jsp"/>
        </div>
        <div class="column is-two-quarters">
            <jsp:include page="views/${viewName}.jsp"/>
        </div>
        <div class="column is-one-quarter">
            <jsp:include page="views/${category}.jsp"/>
        </div>
    </div>
</section>
<section class="footer">
    <div class="content has-text-centered">
        <p>
            <fmt:message key="app.footer"/>
        </p>
    </div>
</section>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 13.07.2020
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty message}">
    <fmt:message var="infoLable" key="app.message.info"/>
    <%--    <div class="container justifyCenter marginTop message is-info">--%>
    <div class="messageContainer marginTop message is-info">
        <div class="message-header">
            <p><c:out value="${infoLable}"/></p>
        </div>
        <div class="message-body">
            <c:out value="${message}"/>
        </div>
    </div>
</c:if>
<%--        <h5 class="title is-5">--%>
<%--            <p><c:out value="${message}"/></p>--%>
<%--        </h5>--%>

<c:if test="${not empty error}">
    <fmt:message var="errorLable" key="app.message.error"/>
    <div class="message is-danger">
            <%--    <div class="justifyCenter marginTop message is-danger">--%>
        <div class="message-header">
            <p><c:out value="${errorLable}"/></p>
        </div>
        <div class="message-body">
            <c:out value="${error}"/>
        </div>
    </div>
</c:if>
<%--    <div class="container justifyCenter marginTop">--%>
<%--        <h5 class="title is-5">--%>
<%--            <p class="is-danger"><c:out value="${error}"/></p>--%>
<%--        </h5>--%>
<%--    </div>--%>
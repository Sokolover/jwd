<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="content">
        <table class="table is-striped is-hoverable is-narrow is-fullwidth">
            <thead>
            <fmt:message key="user.name" var="name"/>
            <fmt:message key="user.email" var="email"/>
            <fmt:message key="user.phoneNumber" var="phoneNumber"/>
            <fmt:message key="user.active" var="active"/>
            <th><abbr title="${name}">${name}</abbr></th>
            <th><abbr title="${email}">${email}</abbr></th>
            <th><abbr title="${phoneNumber}">${phoneNumber}</abbr></th>
            <th><abbr title="${active}">${active}</abbr></th>
            </thead>
            <tbody>
            <jsp:useBean id="userList" scope="request" type="java.util.List"/>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                    <td><c:out value="${user.active}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
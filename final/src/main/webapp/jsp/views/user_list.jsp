<%--
  Created by IntelliJ IDEA.
  User: nbu
  Date: 2019-02-11
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.bunis.um.command.CommandType" %>
<div class="container">
    <div class="content">
        <table class="table is-striped is-hoverable is-narrow is-fullwidth">
            <thead>
            <fmt:message key="user.loginName" var="loginName"/>
            <fmt:message key="user.firstName" var="firstName"/>
            <fmt:message key="user.lastName" var="lastName"/>
            <fmt:message key="user.email" var="email"/>
            <th><abbr title="${loginName}">${loginName}</abbr></th>
            <th><abbr title="${firstName}">${firstName}</abbr></th>
            <th><abbr title="${lastName}">${lastName}</abbr></th>
            <th><abbr title="${email}">${email}</abbr></th>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>
                        <a href="?_command=${CommandType.VIEW_USER_DETAILS}&id=${user.id}">
                            <c:out value="${user.loginName}"/>
                        </a>
                    </td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
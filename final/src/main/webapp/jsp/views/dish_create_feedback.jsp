<%--
  Created by IntelliJ IDEA.
  User: Sokolover
  Date: 08.07.2020
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ page import="by.training.sokolov.command.constants.CommandType" %>
<%@ page import="by.training.sokolov.core.constants.CommonAppConstants" %>

<div class="container">
    <jsp:useBean id="dish" scope="request" type="by.training.sokolov.entity.dish.model.Dish"/>
    <div class="columns">
        <div class="column is-half">
            <div class="card">
                <form action="${pageContext.request.contextPath}/menu" method="post">
                    <input type="hidden" name="${CommonAppConstants.QUERY_COMMAND_PARAM}"
                           value="${CommandType.DISH_FEEDBACK_SUBMIT}">
                    <div class="card-content">
                        <label>
                            <c:set var="minRating" value="1"/>
                            <c:set var="maxRating" value="5"/>
                            <fmt:message key="feedback.rating"/>
                            <div class="control">
                                <c:forEach var="i" begin="${minRating}" end="${maxRating - 1}" step="1">
                                    <label class="radio">
                                        <input type="radio" name="${CommonAppConstants.FEEDBACK_RATING_JSP_PARAM}" value="${i}">
                                        <c:out value="${i}"/>
                                    </label>
                                </c:forEach>
                                <label class="radio">
                                    <input type="radio" name="${CommonAppConstants.FEEDBACK_RATING_JSP_PARAM}" value="${maxRating}" checked>
                                    <c:out value="${maxRating}"/>
                                </label>
                            </div>
                        </label>
                        <label>
                            <fmt:message key="feedback.text"/>
                            <textarea class="textarea" name="${CommonAppConstants.FEEDBACK_TEXT_JSP_PARAM}" rows="10"></textarea>
                        </label>
                    </div>
                    <footer class="card-footer">
                        <input type="hidden" name="${CommonAppConstants.DISH_ID_JSP_PARAM}" value="${dish.id}">
                        <fmt:message var="send_feedback" key="button.feedback.send"/>
                        <input class="button is-primary" type="submit" value="${send_feedback}">
                    </footer>
                </form>
            </div>
        </div>
        <div class="column is-half">
            <div class="card-content">
                <ul>
                    <li>
                        <c:out value="${dish.name}"/>
                    </li>
                    <li>
                        <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture" width="400"
                             height="400"/>
                    </li>
                    <li>
                        <div class="column is-two-thirds">
                            <label for="${dish.description}">
                                <fmt:message key="dish.description"/>
                            </label>
                            <div class="box">
                                <c:out value="${dish.description}"/>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>


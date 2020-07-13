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

<div class="container">
    <jsp:useBean id="dish" scope="request" type="by.training.sokolov.entity.dish.model.Dish"/>
    <div class="columns">
        <div class="column is-half">
            <div class="card">
                <form action="${pageContext.request.contextPath}/menu" method="post">
                    <input type="hidden" name="_command" value="${CommandType.DISH_FEEDBACK_SUBMIT}">
                    <div class="card-content">
                        <label>
                            <fmt:message key="feedback.text"/>
                            <textarea class="textarea" name="feedback.text" placeholder="10 lines of textarea"
                                      rows="10"></textarea>
                        </label>
                        <label>
                            <fmt:message key="feedback.rating"/>
                            <div class="control">
                                <label class="radio">
                                    <input type="radio" name="feedback.rating" value="5" checked>
                                    5
                                </label>
                                <label class="radio">
                                    <input type="radio" name="feedback.rating" value="4">
                                    4
                                </label>
                                <label class="radio">
                                    <input type="radio" name="feedback.rating" value="3">
                                    3
                                </label>
                                <label class="radio">
                                    <input type="radio" name="feedback.rating" value="2">
                                    2
                                </label>
                                <label class="radio">
                                    <input type="radio" name="feedback.rating" value="1">
                                    1
                                </label>
                            </div>
                        </label>
                    </div>
                    <footer class="card-footer">
                        <input type="hidden" name="dish.id" value="${dish.id}">
                        <fmt:message var="send_feedback" key="feedback.send"/>
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
                        <img src="data:image/jpg;base64,${dish.picture}" alt="no dish picture"/>
                    </li>
                    <li>
                        <label for="${dish.cost}">
                            <fmt:message key="dish.cost"/>
                        </label>
                        <c:out value="${dish.cost}"/>
                    </li>
                    <li>
                        <div class="column is-half">
                            <div class="box">
                                <label for="${dish.description}">
                                    <fmt:message key="dish.description"/>
                                </label>
                                <c:out value="${dish.description}"/>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>


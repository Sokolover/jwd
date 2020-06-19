<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <title>FoodDelivery-service</title>
</head>
<body>

<h2>Welcome to FoodDelivery-service</h2>

<table border="1">
    <thead>
    <caption>Dish table</caption>
    <tr>
        <th>id</th>
        <th>Name</th>
        <th>Cost</th>
        <th>Description</th>
        <th>CategoryId</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dishes}" var="dish">
        <tr>
            <td>${dish.getId()}</td>
            <td>${dish.getName()}</td>
            <td>${dish.getCost()}</td>
            <td>${dish.getDescription()}</td>
            <td>${dish.getCategoryId()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<p>
    ${first_dish}
    <caption>First record:</caption>
<table border="1">
    <tbody>
    <tr>
        <td>${first_dish.getId()}</td>
        <td>${first_dish.getName()}</td>
        <td>${first_dish.getCost()}</td>
        <td>${first_dish.getDescription()}</td>
        <td>${first_dish.getCategoryId()}</td>
    </tr>
    </tbody>
</table>
</p>

<p>
    new dish id: ${new_dish_id}
</p>

</body>
</html>






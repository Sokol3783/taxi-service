<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="context_path" scope="page" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${param.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="./styles/bootstrap-reboot.css">
    <link rel="stylesheet" href="./styles/bootstrap.css">
    <link rel="stylesheet" href="./styles/style.css">
    <script id="search-js" defer="" src="https://api.mapbox.com/search-js/v1.0.0-beta.14/web.js"></script>
    <script src="./js/mapbox_scripts.js"></script>
    <script src="./js/bootstrap.bundle.js"></script>
    <title> Taxi </title>
</head>
<body class="container vh-100 background">
<nav class="navbar navbar-expand-md navbar-light background-orange justify-content-between">
    <div class="container-fluid">
        <div class="dropdown">
            <button class="btn btn-warning dropdown-toggle" id="content-Language" type="button"
                    data-bs-toggle="dropdown" aria-expanded="false">
                <fmt:message key="Language"/>
            </button>
            <ul class="dropdown-menu dropdown-menu-orange">
                <li><a class="dropdown-item" href="${context_path}/order?lang=en"><fmt:message key="en"/></a></li>
                <li><a class="dropdown-item" href="${context_path}/order?lang=ru"><fmt:message key="ru"/></a></li>
            </ul>
        </div>
    </div>

    <div>
            <span><button type="button" onclick="location.href='${context_path}/logout';"
                          class="btn btn-info add-new"><i></i><fmt:message key="logout"/></button></span>
    </div>
</nav>
<div class="container g-3 m-2">
    <div class="justify-content-center text-center m-lg-5"><h2 class="alert-light"><fmt:message
            key="car_time_arive"/></h2></div>
    <div class="mb-3 row">
        <div class="col-2">
            <span class="input-group-text"><fmt:message key="Cost"/></span>
        </div>
        <div class="col-2">
            <input type="number" readonly class="form-control-plaintext input-group-text" name="cost" id="cost"
                   <c:if test="${not empty sessionScope.cost}">value="${sessionScope.cost}"</c:if>>
        </div>
        <div class="col-2">
            <span class="input-group-text"><fmt:message key="discount"/></span>
        </div>
        <div class="col-2">
            <input type="number" readonly class="form-control-plaintext input-group-text" name="percentDiscount"
                   id="percentDiscount"
                   <c:if test="${not empty sessionScope.percentDiscount}">value="${sessionScope.percentDiscount}"</c:if>>
        </div>
        <div class="col-2">
            <span class="input-group-text"><fmt:message key="km"/></span>
        </div>
        <div class="col-2">
            <input type="number" readonly class="form-control-plaintext input-group-text" name="distance"
                   id="distance"
                   <c:if test="${not empty sessionScope.distance}">value="${sessionScope.distance}"</c:if>>
        </div>
    </div>
</div>
<div class="container">
    <div class="justify-content-lg-start">
        <h2><fmt:message key="CarsOrdered"/></h2>
        <c:forEach var="car" items="${sessionScope.cars}">
            <p><fmt:message key="Car"/> ${car.carName} <fmt:message key="number"/> ${car.number}</p>
        </c:forEach>
    </div>
    <form action="user" method="get">
        <div class="col-12">
            <button name="act"
                    value="findCar" type="submit" class="btn btn-primary background-orange-button">
                <fmt:message key="backToMenu"/>
            </button>
        </div>
    </form>
</div>
</body>
</html>
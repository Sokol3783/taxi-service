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
<nav class="navbar navbar-expand-md navbar-light background-orange">
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
</nav>
<div class="container">
    <div class="justify-content-center mb-lg-5"><fmt:message key="car_time_arive"/></div>
    <div class="justify-content-lg-start">
        <h2><fmt:message key="CarsOrdered"/></h2>
        <c:forEach var="car" items="${sessionScope.cars}">
            <!-- <p><fmt:message key="Car"/> + " " + car.carName + <fmt:message key="number"/> + car.number</p> -->
        </c:forEach>
    </div>
    <div class="">кнопка возврат к юзерам</div>
</div>
</body>
</html>
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
                <li><a class="dropdown-item" href="${context_path}/user?lang=en"><fmt:message key="en"/></a></li>
                <li><a class="dropdown-item" href="${context_path}/user?lang=ru"><fmt:message key="ru"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<form class="container p-3 mb-3" action="user-action" method="post">
    <div class="row">
        <div class="col-9 mb-3">
            <select class="form-select form-select" aria-label=".form-select example" name="car-category"
                    id="car-category"
                    <c:if test="${not empty sessionScope.carCategory}">value="${sessionScope.carCategory}"</c:if>>
                <option selected><fmt:message key="ChoiceCategory"/></option>
                <option value="ECONOMY"><fmt:message key="ECONOMY"/></option>
                <option value="STANDARD"><fmt:message key="STANDARD"/></option>
                <option value="BUSYNESS"><fmt:message key="BUSYNESS"/></option>
            </select>
        </div>
        <div class="col-3 mb-3 ">
            <fmt:message key="number_passengers" var="numberPassengers"/>
            <input type="number" class="form-control text-center" name="passengers" id="passengers"
                   placeholder="${numberPassengers}" aria-label="number of passengers"
            <c:choose>
                    <c:when test="${not empty sessionScope.passengers}">
                        value = "${sessionScope.passengers}"
                    </c:when>
            <c:otherwise>
                   value="1"
            </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="col-12 justify-content-start mb-2">
        <button name="act" value="findCar" type="submit" class="btn btn-primary background-orange-button">
            <fmt:message key="find_car"/></button>
    </div>
</form>
<form action="user-action" method="post">
    <div class="container mb-3">
        <div class="row">
            <div class="col-md-3">
                <span class="input-group-text"><fmt:message key="Cost"/></span>
            </div>
            <div class="col-md-2">
                <input type="number" readonly class="form-control-plaintext input-group-text" id="cost" value="">
            </div>
            <div class="col-md-3">
                <span class="input-group-text"><fmt:message key="discount"/></span>
            </div>
            <div class="col-md-2">
                <input type="number" readonly class="form-control-plaintext input-group-text" id="percentDiscount"
                       value="">
            </div>
            <div class="col-md-2">
                <span class="input-group-text"><fmt:message key="km"/></span>
                <input type="number" readonly class="form-control-plaintext input-group-text" id="distance"
                       value="">
            </div>
        </div>
    </div>
    <div class="container g-3 m-2 row">
        <div class="mb-3 row">
            <label for="addressDeparture" class="col-sm-2 col-form-label"><fmt:message
                    key="address_departure"/></label>
            <div class="col-10">
                <mapbox-address-autofill class="departure">
                    <input type="text" class="address form-control" id="addressDeparture"
                           autocomplete="shipping street-address">
                    <input type="hidden" class="coordinates departure">
                </mapbox-address-autofill>
            </div>
        </div>
        <div class="mb-3 row">
            <label for="destination" class="col-sm-2 col-form-label"><fmt:message
                    key="destination"/></label>
            <div class="col-10">
                <mapbox-address-autofill class="destination">
                    <input type="text" class="address form-control" id="destination"
                           autocomplete="billing street-address">
                    <input type="hidden" class="coordinates destination">
                </mapbox-address-autofill>
            </div>
        </div>
        <div class="col-12">
            <button onClick="validateFreeCar" name="act"
                    value="createOrder" type="submit" class="btn btn-primary background-orange-button">
                <fmt:message key="create_order"/>
            </button>
        </div>
    </div>
</form>
</body>
</html>
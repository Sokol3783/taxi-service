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
                <li><a class="dropdown-item" href="${context_path}/user?lang=en"><fmt:message key="en"/></a></li>
                <li><a class="dropdown-item" href="${context_path}/user?lang=ru"><fmt:message key="ru"/></a></li>
            </ul>
        </div>
        <div>
            <span><button type="button" onclick="location.href='${context_path}/logout';"
                          class="btn btn-info add-new"><i></i><fmt:message key="logout"/></button></span>
        </div>
    </div>
</nav>
<form class="container p-3 mb-3" action="user-action" method="post">
    <c:if test="${not empty sessionScope.CarError}">
        <div class="col-12 justify-content-center mb-5">
            <h3 class="text-center alert-danger" id="exampleModalLongTitle"><fmt:message
                    key="${sessionScope.CarError}"/></h3>
        </div>
        <c:remove var="CarError" scope="session"/>
    </c:if>
    <div class="row">
        <div class="col-9 mb-3">
            <select class="form-select form-select" aria-label=".form-select example" name="car-category"
                    id="car-category" onchange="countCost()">
                <option <c:if test="${empty sessionScope.carCategory}">selected="selected"</c:if>><fmt:message
                        key="ChoiceCategory"/></option>
                <option
                        <c:if test="${sessionScope.carCategory == 'ECONOMY'}">selected="selected"</c:if>
                        value="ECONOMY">
                    <fmt:message key="ECONOMY"/></option>
                <option
                        <c:if test="${sessionScope.carCategory == 'STANDARD'}">selected="selected"</c:if>
                        value="STANDARD">
                    <fmt:message key="STANDARD"/></option>
                <option
                        <c:if test="${sessionScope.carCategory == 'BUSYNESS'}">selected="selected"</c:if>
                        value="BUSYNESS">
                    <fmt:message key="BUSYNESS"/></option>
            </select>
            <input type="hidden" id="prices" value="${sessionScope.prices}">
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
    <div class="container">
        <div class="row mb-3">
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
    <div class="container g-3 m-2">
        <div class="mb-3 row">
            <label for="addressDeparture" class="col-sm-2 col-form-label"><fmt:message
                    key="address_departure"/></label>
            <div class="col-10">
                <mapbox-address-autofill class="departure">
                    <input type="text" class="address form-control" name="addressDeparture" id="addressDeparture"
                           autocomplete="shipping street-address" onchange="countCost()"
                           <c:if test="${not empty sessionScope.addressDeparture}">value="${sessionScope.addressDeparture}"</c:if>>
                    <input type="hidden" name="coordinatesDeparture" class="coordinates departure"
                           <c:if test="${not empty sessionScope.coordinatesDeparture}">value="${sessionScope.coordinatesDeparture}"</c:if>>
                </mapbox-address-autofill>
            </div>
        </div>
        <div class="mb-3 row">
            <label for="destination" class="col-sm-2 col-form-label"><fmt:message
                    key="destination"/></label>
            <div class="col-10">
                <mapbox-address-autofill class="destination">
                    <input type="text" class="address form-control" name="destination" id="destination"
                           <c:if test="${not empty sessionScope.destination}">value="${sessionScope.destination}"</c:if>
                           autocomplete="billing street-address" onchange="countCost()">
                    <input type="hidden" name="coordinatesDestination" class="coordinates destination"
                           <c:if test="${not empty sessionScope.coordinatesDestination}">value="${sessionScope.coordinatesDestination}"</c:if>>
                </mapbox-address-autofill>
            </div>
        </div>
        <div class="col-12">
            <button name="act"
                    value="findCar" type="submit" class="btn btn-primary background-orange-button">
                <fmt:message key="find_car"/>
            </button>
            <c:if test="${sessionScope.alternative==true}">
                <button name="act"
                        value="otherCategory" type="submit" class="btn btn-primary background-orange-button">
                    <fmt:message key="create_order_several_cars"/>
                </button>
                <button name="act"
                        value="severalCars" type="submit" class="btn btn-primary background-orange-button">
                    <fmt:message key="create_order_another_category"/>
                </button>
            </c:if>
            <button name="act"
                    value="createOrder" type="submit" class="btn btn-primary background-orange-button">
                <fmt:message key="create_order"/>
            </button>
        </div>
    </div>
</form>
</body>
</html>
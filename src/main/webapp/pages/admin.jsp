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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" href="./styles/style.css">
    <script src="./js/mapbox_scripts.js"></script>
    <script src="./js/order-report.js"></script>
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
                <li><a class="dropdown-item" href="${context_path}/admin?lang=en"><fmt:message key="en"/></a></li>
                <li><a class="dropdown-item" href="${context_path}/admin?lang=ru"><fmt:message key="ru"/></a></li>
            </ul>
        </div>
    </div>

    <div>
            <span><button type="button" onclick="location.href='${context_path}/logout';"
                          class="btn btn-info add-new"><i></i><fmt:message key="logout"/></button></span>
    </div>
</nav>
<div class="container">
    <div class="row col-lg-12 justify-content-between">
        <div class="col-lg-4 client-filter">
            <div></div>
        </div>
        <div class="col-lg-6">
            <div class="col-lg-3">
                <span class="input-group-text" id="basic-addon2"><fmt:message key="startPeriod"/></span>
                <input type="date" class="form-control" name="startPeriod" aria-label="Username"
                       <c:if test="${not empty sessionScope.startPeriod}">value="${sessionScope.startPeriod}"
                       </c:if>aria-describedby="basic-addon2">
            </div>
            <div class="col-lg-3">
                <span class="input-group-text" id="basic-addon3"><fmt:message key="endPeriod"/></span>
                <input type="date" class="form-control" name="endPeriod" aria-label="Username"
                       <c:if test="${not empty sessionScope.endPeriod}">value="${sessionScope.endPeriod}"</c:if>
                       aria-describedby="basic-addon3">
            </div>
        </div>
    </div>
</div>
<table class="table table-success table-striped" id="order-report">
    <nav aria-label="...">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item active" aria-current="page">
                <a class="page-link" href="#">2</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col"><fmt:message key="client"/></th>
        <th scope="col"><fmt:message key="cost"/></th>
        <th scope="col"><fmt:message key="distance"/></th>
        <th scope="col"><fmt:message key="date"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="line" items="${sessionScope.report}">
        <tr>
            <th scope="row">${car.numberLine}</th>
            <td>${car.client}</td>
            <td>${car.cost}</td>
            <td>${car.distance}</td>
            <td>${car.date}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

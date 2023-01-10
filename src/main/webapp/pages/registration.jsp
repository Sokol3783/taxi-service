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
    <title> Taxi </title>
</head>
<body class="container vh-100 background">
<nav class="navbar navbar-expand-md navbar-light background-orange">
    <div class="container-fluid">
        <div class="dropdown">
            <button class="btn btn-warning dropdown-toggle" type="button" data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="Language"/>
            </button>
            <ul class="dropdown-menu dropdown-menu-orange">
                <li><a class="dropdown-item" href="${context_path}/user?lang=en"><fmt:message key="en"/></a></li>
                <li><a class="dropdown-item" href="${context_path}/user?lang=ru"><fmt:message key="ru"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<form id="register-form" name="register-form" action="registration" method="post" onsubmit="return validateForm()">
    <div class="register-container m-5">
        <div class="input-group mb-3">
            <fmt:message key="first_name" var="firstName"/>
            <input type="text" class="form-control" placeholder="${firstName}" name="first_name" aria-label="Username"
                   aria-describedby="basic-addon1">
        </div>
        <div class="input-group mb-3">
            <fmt:message key="last_name" var="lastName"/>
            <input type="text" class="form-control" placeholder="${lastName}" name="last_name" aria-label="Username"
                   aria-describedby="basic-addon1">
        </div>
        <div class="input-group mb-3">
            <fmt:message key="email" var="mail"/>
            <input type="e-mail" class="form-control" placeholder="${mail}" name="email" aria-label="Username"
                   aria-describedby="basic-addon1">
        </div>
        <div class="input-group mb-3">
            <fmt:message key="birthday" var="date_birth"/>
            <input type="date" class="form-control" placeholder="${date_birth}" name="birthday" aria-label="Username"
                   aria-describedby="basic-addon1">
        </div>
        <div class="input-group mb-3">
            <fmt:message key="mobile_phone" var="mobilePhone"/>
            <input type="text" class="form-control" placeholder="${mobilePhone}" name="phone" aria-label="Username"
                   aria-describedby="basic-addon1">
        </div>
        <div class="input-group mb-3">
            <fmt:message key="password" var="_password"/>
            <input type="password" class="form-control" placeholder="${_password}" name="password" id=="password"
                   aria-label="Username" aria-describedby="basic-addon1">
        </div>
        <div class="input-group mb-3">
            <fmt:message key="repeat_password" var="repeatPassword"/>
            <input type="password" class="form-control" placeholder="${repeatPassword}" name="password_check"
                   id=="password_check" aria-label="Username" aria-describedby="basic-addon1">
        </div>
        <div class="input-group btn-group">
            <input type="radio" class="btn-check" name="role" id="client" autocomplete="off" value="user" checked>
            <label class="btn btn-outline-success lbl-taxi" for="client"> <fmt:message key="client"/> </label>
            <input type="radio" class="btn-check" name="role" id="driver" value="driver" autocomplete="off">
            <label class="btn btn-outline-driver lbl-taxi" for="driver"> <fmt:message key="driver"/> </label>
        </div>
    </div>
    <div class="input-group mb-3">
        <button id="register-submit" type="submit" class="btn btn-primary background-orange-button"><fmt:message
                key="_submit"/></button>
    </div>
</form>
<script src="./js/bootstrap.bundle.js"></script>
<script src="./js/validate.registration.js"></script>
</body>
</html>

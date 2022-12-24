<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="context_path" scope="page" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${param.lang}"/>
<c:set var="lang" scope="session" value="${param.lang}"/>
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
            <button class="btn btn-warning dropdown-toggle" id="content-Language" type="button"
                    data-bs-toggle="dropdown" aria-expanded="false">
                <fmt:message key="Language"/>
            </button>
            <ul class="dropdown-menu dropdown-menu-orange">
                <li><a class="dropdown-item" href="${context_path}/index.jsp?lang=en"><fmt:message key="en"/></a></li>
                <li><a class="dropdown-item" href="${context_path}/index.jsp?lang=ru"><fmt:message key="ru"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<form action="login" method="post">
    <div class="text-center"><img src="./images/icon.jpg" class=".img-thumbnail img-thumbnail-height" alt="logo"></div>
    <div class="mb-3">
        <label for="login" class="form-label"><fmt:message key="login"/></label>
        <input type="text" class="form-control" id="login" name="login"
               <c:if test="${not empty sessionScope.login}">value="${sessionScope.login}"</c:if>>
        <div class="form-text"><fmt:message key="login_logo"/></div>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label"><fmt:message key="_password"/></label>
        <input type="password" class="form-control" id="password" name="password">
    </div>
    <div class="row container-xl justify-content-between">
        <div class="col">
            <button type="submit" class="btn btn-primary background-orange-button"><fmt:message key="_submit"/></button>
        </div>
        <div class="col text-end">
            <a href="registration" class="fa fa-registered text-color" aria-hidden="true"><fmt:message
                    key="new_acc"/></a>
        </div>
    </div>
</form>
<script src="./js/bootstrap.bundle.js"></script>
</body>
</html>
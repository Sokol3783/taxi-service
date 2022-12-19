<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />

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
                  <button class="btn btn-warning dropdown-toggle" id="content-Language" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                      English
                  </button>
                  <ul class="dropdown-menu dropdown-menu-orange">
                    <li><fmt:setLocale value="ru"/>ru </li>
                    <li><fmt:setLocale value="en"/>en</li>
                  </ul>
          </nav>
          <form class="container p-3 mb-3" action="user-action" method="post" >
            <div class="row">
                <div class="col-9 mb-3">
                  <select class="form-select form-select" aria-label=".form-select example" name="car-category" id="car-category">
                  <option selected>Select prefer taxi category</option>
                  <option value="ECONOMY">ECONOMY</option>
                  <option value="STANDARD">STANDARD</option>
                  <option value="BUSYNESS">BUSYNESS</option>
                  </select>
                </div>
                <div class="col-3 mb-3 ">
                    <input type="number" class="form-control text-center"  name="passengers" value="1" id="passengers" placeholder="number of passengers" aria-label="number of passengers">
                </div>
                <div class="col-12">
                  <button name="act" value= "findCar" type="submit" class="btn btn-primary background-orange-button">Find car</button>
                </div>
            </div>
          </form>
          <form class="container p-3 mb-3" action="user-action" method="post">
            <div class="container g-3 m-3 col-12 row justify-content-between">
              <div class="container row col-6">
                  <div class="col-2"> 
                    <span class="input-group-text">Cost</span>
                  </div>
                  <div class="col-4"> 
                    <input type="number" readonly class="form-control-plaintext input-group-text" id="percentDiscount" value="25">
                  </div>
               </div>
              <div class="container row col-6 justify-content-end"> 
                  <div class="col-3"> 
                    <span class="input-group-text">% discount</span>
                  </div>
                  <div class="col-3 jus"> 
                    <input type="number" readonly class="form-control-plaintext input-group-text" id="percentDiscount" value="25">
                  </div>
                </div>
            </div>
            <div class="container g-3 m-2 row">
              <div class="mb-3 row">
                <label for="addressDeparture" class="col-sm-2 col-form-label">address departure</label>
                <div class="col-10">
                  <input type="password" class="form-control" id="addressDeparture">
                </div>
              </div>
              <div class="mb-3 row">
                <label for="destination" class="col-sm-2 col-form-label">destination</label>
                <div class="col-10">
                  <input type="password" class="form-control" id="destination">
                </div>
              </div>
              <div class="col-12">
                    <button name="act" value= "createOrder" type="submit" class="btn btn-primary background-orange-button">Create order</button>
              </div>
          </div>
      <script src="./js/bootstrap.bundle.js"></script>
    </body>
</html>
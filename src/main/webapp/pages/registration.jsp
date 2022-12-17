<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
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
                      <button class="btn btn-warning dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                          Language
                      </button>
                      <ul class="dropdown-menu dropdown-menu-orange">
                        <li><a class="dropdown-item" href="#">ru-RU</a></li>
                        <li><a class="dropdown-item" href="#">en-EN</a></li>
                      </ul>
              </nav>
          <form class="needs-validation" action="registration" method="post" >
            <div class="register-container m-5">
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="First name" name="first_name" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Last name" name="last_name" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="e-mail" class="form-control" placeholder="e-mail" name="email"  aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="date" class="form-control" placeholder="Birthday" name="birthday" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Mobile phone" name="phone" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="password" class="form-control" placeholder="Password" name="password" id=="password" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="password" class="form-control" placeholder="Repeat the password" name="password_check" id=="password_check"  aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group btn-group">
                <input type="radio" class="btn-check" name="role" id="client" autocomplete="off" value="user" checked>
                <label class="btn btn-outline-success lbl-taxi" for="client"> Client </label>
                <input type="radio" class="btn-check" name="role" id="driver" value="driver" autocomplete="off">
                <label class="btn btn-outline-driver lbl-taxi" for="driver"> Driver </label>
              </div>
            </div>
              <div class="input-group mb-3">
                <button type="submit" class="btn btn-primary background-orange-button">Submit</button>
              </div>
          </form>
          <script src="./js/bootstrap.bundle.js"></script>
        </body>
</html>

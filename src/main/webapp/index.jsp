<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                  <button class="btn btn-warning dropdown-toggle" id="content-Language" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                      English
                  </button>
                  <ul class="dropdown-menu dropdown-menu-orange">
                    <li><a class="dropdown-item" href="#">ru</a></li>
                    <li><a class="dropdown-item" href="#">en</a></li>
                  </ul>
          </nav>
      <form action="login" method="post">
        <div class="text-center"> <img src="./images/icon.jpg" class=".img-thumbnail img-thumbnail-height" alt="logo"> </div>
        <div class="mb-3">
          <label for="login" class="form-label">Your mobile number or login</label>
          <input type="text" class="form-control" id="login">
          <div id="emailHelp" class="form-text">We'll never share your contacts with anyone else.</div>
        </div>
        <div class="mb-3">
          <label for="inputPassword" class="form-label">Password</label>
          <input type="password" class="form-control" id="inputPassword">
        </div>
        <div class="mb-3 form-check">
          <input type="checkbox" class="form-check-input" id="exampleCheck1">
          <label class="form-check-label" for="exampleCheck1">Check me out</label>
        </div>
        <div class="row container-xl justify-content-between">
          <div class="col">
            <button type="submit" class="btn btn-primary background-orange-button">Submit</button>
          </div>
          <div class="col text-end">
            <a  href="#" class="fa fa-registered text-color" aria-hidden="true">Create new account</a>
          </div>
        </div>
      </form>
      <script src="./js/bootstrap.bundle.js"></script>
    </body>
</html>

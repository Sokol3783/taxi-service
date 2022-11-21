<!DOCTYPE html>
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
      <form>
        <div class="input-group mb-3">
          <span class="input-group-text" id="basic-addon1">@</span>
          <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
        </div>
        <div class="col"> 
          <button type="submit" class="btn btn-primary background-orange-button">Submit</button>
        </div>
      </form>
      <script src="./js/bootstrap.bundle.js"></script>
    </body>
</html>

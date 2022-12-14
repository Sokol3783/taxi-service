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
            <div class="register-container m-5 needs-validation">
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="First name" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Second name" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="e-mail" class="form-control" placeholder="e-mail" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="date" class="form-control" placeholder="Birth date" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Mobile phone" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="password" class="form-control" placeholder="Password" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group mb-3">
                <input type="password" class="form-control" placeholder="Repeat the password" aria-label="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group btn-group">
                <input type="radio" class="btn-check" name="options-outlined" id="client" autocomplete="off" checked>
                <label class="btn btn-outline-success lbl-taxi" for="client"> Client </label>

                <input type="radio" class="btn-check" name="options-outlined" id="driver" autocomplete="off">
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

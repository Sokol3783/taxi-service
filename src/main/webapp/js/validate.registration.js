function validateForm() {
  let form = document.forms["register-form"];
  let lang = document.getElementsByTagName("html")[0].getAttribute("lang");

  if (lang == 'ru') {
      let fname = form["first_name"].value;
      if (fname == "") {
        alert("Заполните имя");
        return false;
      }
      let sname = form["last_name"].value;
      if (sname == "") {
        alert("Заполните фамилию");
        return false;
      }
      let mail = form["email"].value;
      if (mail == "") {
        alert("Введите эл. почту");
        return false;
      }
     let phone = form["phone"].value;
     if (phone == "") {
       alert("Введите номер телефона");
       return false;
     }
     let address = form["address"].value;
      if (address == "") {
        alert("Введите домашний адрес");
        return false;
      }
     let pass = form["password"].value;
     if (pass == "") {
       alert("Введите пароль");
       return false;
     }
     let rep_pass = form["password_check"].value;
      if (rep_pass == "") {
        alert("Повторите пароль");
        return false;
      }
      if (pass != rep_pass) {
        alert("Пароли не совпадают");
        return false;
      }

  } else {
      let fname = form["first_name"].value;
      if (fname == "") {
        alert("Enter a first name");
        return false;
      }
      let sname = form["last_name"].value;
      if (sname == "") {
        alert("Enter a last name");
        return false;
      }
      let mail = form["email"].value;
      if (mail == "") {
        alert("Enter a email address");
        return false;
      }
    let phone = form["phone"].value;
    if (phone == "") {
       alert("Enter a valid phone number");
       return false;
     }
     let address = form["address"].value;
     if (address == "") {
        alert("Enter home address");
        return false;
      }
     let pass = form["password"].value;
     if (pass == "") {
       alert("Enter password");
       return false;
     }
     let rep_pass = form["password_check"].value;
      if (rep_pass == "") {
        alert("Repeat password");
        return false;
      }
      if (pass != rep_pass) {
        alert("Passwords do not match");
        return false;
      }
  }
}
package org.example.controllers.servlets;


import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.AppURL;
import org.example.controllers.managers.UserManager;
import org.example.exceptions.DAOException;
import org.example.models.User;
import org.example.models.taxienum.UserRole;
import org.example.util.LocalDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "registration", urlPatterns = "/registration")
public class Registration extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(Registration.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    forward(AppURL.REGISTRATION_JSP, request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    User user = createUser(request);
    if (nonNull(user)) {
      UserManager manager = new UserManager();
      try {
        manager.create(user);
      } catch (SQLException e) {
        log.error(DAOException.USER_NOT_CREATE, e);
        sendRedirect(response, request.getHeader("referer"));
      }

    }
  }

  private User createUser(HttpServletRequest request) {
    if (validatePassword(request)) {
      User user = User.builder().firstName(request.getParameter("first_name"))
          .secondName(request.getParameter("last_name"))
          .email(request.getParameter("email"))
          .phone(request.getParameter("phone"))
          .password(request.getParameter("password"))
          .role(UserRole.getRole(request.getParameter("role")))
          .birthDate(LocalDateConverter.convertToEntityAttribute(
              request.getParameter("birthday")))
          .build();
      return user;
    }
    return null;
  }

  private boolean validatePassword(HttpServletRequest request) {
    String password = request.getParameter("password");
    String passwordRepeat = request.getParameter("password_check");
    return password.compareTo(passwordRepeat) == 0;
  }

}

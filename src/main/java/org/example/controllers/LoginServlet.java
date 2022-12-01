package org.example.controllers;

import static java.util.Objects.nonNull;
import static org.example.models.taxienum.UserRole.ADMIN;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.models.taxienum.UserRole;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    final HttpServletRequest req = request;
    final HttpServletResponse res = response;

    final String login = req.getParameter("LOGIN");
    final String password = req.getParameter("PASSWORD");

    final HttpSession session = req.getSession();

    //Logged user.
    if (nonNull(session) &&
        nonNull(session.getAttribute("LOGIN")) &&
        nonNull(session.getAttribute("TOKEN"))) {

      final UserRole role = (UserRole) session.getAttribute("ROLE");

      moveToMenu(req, res, role);


    } else if (isExistUser(req)) {

      final UserRole role = UserRole.getRoleByLoginPassword(login, password);

      req.getSession().setAttribute("PASSWORD", password);
      req.getSession().setAttribute("LOGIN", login);
      req.getSession().setAttribute("", role);

      moveToMenu(req, res, role);

    } else {
      moveToMenu(req, res, null);
    }
  }

  /**
   * Move user to menu by his role
   */
  private void moveToMenu(final HttpServletRequest req,
      final HttpServletResponse res,
      final UserRole role) {

    switch (role) {
      case ADMIN:
        forward(ADMIN, req, res);
        break;
      case USER:
        forward(USER, req, res);
        break;
      case DRIVER:
        forward(NURSE, req, res);
        break;
      default:
        forward(INDEX, req, res);
    }
  }

  private boolean isExistUser(final ServletRequest request) {
    final String login = request.getParameter(LOGIN);
    final String password = request.getParameter(PASSWORD);
    try {
      return service.isExistUser(login, password);
    } catch (Exception e) {
      return false;
    }
  }

}

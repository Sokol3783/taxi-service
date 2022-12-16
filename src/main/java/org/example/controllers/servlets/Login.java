package org.example.controllers.servlets;

import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.AppUrl;
import org.example.controllers.managers.UserManager;
import org.example.exceptions.DAOException;
import org.example.models.User;

@WebServlet(name = "login", urlPatterns = "/login")
public class Login extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    final String login = request.getParameter("login");
    final String password = request.getParameter("password");

    final HttpSession session = request.getSession();

    //Logged user.
    if (nonNull(session) &&
        nonNull(session.getAttribute("USER")) &&
        nonNull(session.getAttribute("TOKEN"))) {

      if (isValidToken((String) session.getAttribute("TOKEN"))) {
        moveToMenu((User) session.getAttribute("USER"), request, response);
      }

    } else {
      final User user = findUser(login, password);
      if (nonNull(user)) {
        request.getSession().setAttribute("TOKEN", "");
        request.getSession().setAttribute("USER", user);
        moveToMenu(user, request, response);
      } else {
        moveToMenu(null, request, response);
      }
    }
  }

  private User findUser(String login, String password) throws DAOException {
    UserManager manager = new UserManager();
    return manager.findUserLoginPassword(login, password);
  }

  private boolean isValidToken(String token) {
    return true;
  }

  /**
   * Move user to menu by his role
   */
  private void moveToMenu(User user, final HttpServletRequest request,
      final HttpServletResponse response) {
    if (nonNull(user)) {
      forward(UserManager.getRoleURL(user), request, response);
    } else {
      forward(AppUrl.INDEX, request, response);
    }
  }

}

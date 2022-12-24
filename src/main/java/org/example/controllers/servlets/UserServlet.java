package org.example.controllers.servlets;

import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.AppURL;

@WebServlet(name = "user", urlPatterns = AppURL.USER_SERVLET)
public class UserServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    moveToMenu(request, response);
  }

  private void moveToMenu(HttpServletRequest request,
      HttpServletResponse response) throws ServletException {
    final HttpSession session = request.getSession();
    if (nonNull(session.getAttribute("USER"))) {
      forward(AppURL.USER_JSP, request, response);
    } else {
      sendRedirect(response, request.getContextPath());
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    moveToMenu(request, response);
  }

}

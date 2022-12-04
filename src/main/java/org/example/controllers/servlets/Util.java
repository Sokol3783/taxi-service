package org.example.controllers.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

  private static final Logger log = LoggerFactory.getLogger(Util.class);

  public static void sendRedirect(HttpServletResponse resp, String url) {
    try {
      resp.sendRedirect(url);
    } catch (IOException e) {
      /*
      String message = format(REDIRECT_MESSAGE, url);
      log.error(message);
      throw new RedirectException(message);
      */
    }
  }

  public static void forward(String url, HttpServletRequest request, HttpServletResponse response) {
    try {
      request.getRequestDispatcher(url).forward(request, response);
    } catch (ServletException | IOException e) {
     /* String message = format(FORWARDING_EXCEPTION, url);
      log.error(message);
      throw new ForwardServletException(message, e);*/
    }
  }

  public static String getValueFromRequest(HttpServletRequest request, HttpSession session,
      String parameter) {
    return request.getParameter(parameter) != null ? request.getParameter(parameter) :
        (String) session.getAttribute(parameter);
  }

}

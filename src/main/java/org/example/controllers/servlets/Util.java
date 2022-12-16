package org.example.controllers.servlets;


import static java.lang.String.format;
import static org.example.exceptions.ForwardException.FORWARDING_EXCEPTION;
import static org.example.exceptions.RedirectException.REDIRECT_EMPTY_URL;
import static org.example.exceptions.RedirectException.REDIRECT_MESSAGE;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.AppUrl;
import org.example.exceptions.ForwardException;
import org.example.exceptions.RedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

  private static final Logger log = LoggerFactory.getLogger(Util.class);

  public static void sendRedirect(HttpServletResponse resp,
      String url) {

    if (url.length() > 0) {
      try {
        resp.sendRedirect(url);
      } catch (IOException e) {
        String message = format(REDIRECT_MESSAGE, url);
        log.error(message);
        throw new RedirectException(message);
      }
    } else {
      log.error(REDIRECT_EMPTY_URL);
      sendRedirect(resp, AppUrl.NOT_FOUND);
    }
  }

  public static void forward(String url, HttpServletRequest request, HttpServletResponse response) {
    if (url.length() > 0) {
      try {
        request.getRequestDispatcher(url).forward(request, response);
      } catch (ServletException | IOException e) {
        String message = format(FORWARDING_EXCEPTION, url);
        log.error(message);
        throw new ForwardException(message, e);
      }
    } else {
      log.error(REDIRECT_EMPTY_URL);
      forward(AppUrl.NOT_FOUND, request, response);
    }
  }

  public static String getValueFromRequest(HttpServletRequest request, HttpSession session,
      String parameter) {
    return request.getParameter(parameter) != null ? request.getParameter(parameter) :
        (String) session.getAttribute(parameter);
  }

}

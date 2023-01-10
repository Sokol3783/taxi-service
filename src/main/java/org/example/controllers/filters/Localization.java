package org.example.controllers.filters;


import static org.example.controllers.managers.Properties.LANG;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Localization implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpSession session = req.getSession();
    String currentLang = (String) session.getAttribute(LANG);
    String newLang = req.getParameter(LANG);
    if (currentLang == null) {
      currentLang = "ru";
    }
    if (newLang != null && !newLang.equals(currentLang)) {
      session.setAttribute(LANG, newLang);
    } else {
      session.setAttribute(LANG, session.getAttribute(LANG));
    }
    filterChain.doFilter(request, response);
  }
}



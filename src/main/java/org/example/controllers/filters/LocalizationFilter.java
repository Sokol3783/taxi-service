package org.example.controllers.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.example.controllers.managers.PropertiesManager.LANG;

public class LocalizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String currentLang = (String) session.getAttribute(LANG);
        String newLang = req.getParameter(LANG);
        if (currentLang == null) {
            //TODO
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



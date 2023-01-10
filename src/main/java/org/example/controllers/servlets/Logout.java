package org.example.controllers.servlets;

import org.example.AppURL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.example.controllers.servlets.Util.sendRedirect;

@WebServlet(name = "logout", urlPatterns = AppURL.LOGOUT_SERVLET)
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
    }
}

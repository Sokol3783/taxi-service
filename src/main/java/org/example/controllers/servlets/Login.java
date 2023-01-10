package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.controllers.managers.UserManager;
import org.example.exceptions.DAOException;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;

@WebServlet(name = "login", urlPatterns = "/login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute("USER");
        session.removeAttribute("TOKEN");

        moveToMenu(null, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        final HttpSession session = request.getSession();

        //Logged user.
        if (nonNull(session) &&
                nonNull(session.getAttribute("USER")) &&
                nonNull(session.getAttribute("TOKEN"))) {

            if (isValidToken((String) session.getAttribute("TOKEN"))) {
                moveToMenu((User) session.getAttribute("USER"), request, response);
            } else {
                session.setAttribute("login", login);
                moveToMenu(null, request, response);
            }

        } else {
            final User user = findUser(login, password);
            if (nonNull(user)) {
                request.getSession().setAttribute("TOKEN", "");
                request.getSession().setAttribute("USER", user);
                moveToMenu(user, request, response);
            } else {
                session.setAttribute("login", login);
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
                            final HttpServletResponse response) throws ServletException {
        String path = request.getContextPath();
        if (nonNull(user)) {
            sendRedirect(response, path + UserManager.getRoleURL(user));
        } else {
            forward(AppURL.INDEX_JSP, request, response);
        }
    }

}

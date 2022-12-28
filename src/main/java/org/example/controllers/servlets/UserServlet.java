package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.controllers.managers.SalesManagement;
import org.example.models.Discount;
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
            final SalesManagement instance = SalesManagement.getInstance();
            setSalePropertiesToSession(instance, session);
            forward(AppURL.USER_JSP, request, response);
        } else {
            sendRedirect(response, request.getContextPath());
        }
    }

    private void setSalePropertiesToSession(SalesManagement instance, HttpSession session) {
        User user = (User) session.getAttribute("USER");
        Discount discount = instance.getDiscountByUser(user);
        session.setAttribute("discount", discount);
        session.setAttribute("percentDiscount", discount.getPercent());
        session.setAttribute("alternative", false);
        String map = instance.getPrices().toString();
        session.setAttribute("prices", map.replaceAll("[{} ]", ""));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        moveToMenu(request, response);
    }

}

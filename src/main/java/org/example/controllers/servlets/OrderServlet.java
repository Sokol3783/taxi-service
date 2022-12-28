package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.controllers.managers.OrderManager;
import org.example.controllers.managers.UserManager;
import org.example.models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;

@WebServlet(name = "order", urlPatterns = AppURL.ORDER_SERVLET)
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        User user = (User) req.getSession().getAttribute("USER");
        if (nonNull(user)) {
            forward(UserManager.getRoleURL(user), req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
        /*createOrder(req, resp);*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        User user = (User) req.getSession().getAttribute("USER");
        if (nonNull(user)) {
            createOrder(req);
            forward(AppURL.ORDER_JSP, req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
    }

    private void createOrder(HttpServletRequest req) {
        OrderManager manager = new OrderManager();
        Order order = manager.create(buildOrder(req));
        Fleet fleet = Fleet.getInstance();
        fleet.setCarsOnRoute(order.getCars());
        removeOrderFromSession(req);
    }

    private void removeOrderFromSession(HttpServletRequest req) {
        String[] params = {"carCategory, passengers, cost,distance, addressDeparture,coordinatesDeparture,destination,coordinatesDestination"};
        HttpSession session = req.getSession();
        for (String param : params) {
            session.removeAttribute(param);
        }
    }

    private Order buildOrder(HttpServletRequest req) {
        final HttpSession session = req.getSession();
        return Order.builder().cars((List<Car>) session.getAttribute("cars"))
                .client((User) session.getAttribute("USER"))
                .cost(Long.parseLong(session.getAttribute("cost").toString()))
                .addressDeparture((String) session.getAttribute("addressDeparture"))
                .destination((String) session.getAttribute("destination"))
                .percentDiscount(((Discount) session.getAttribute("discount")).getPercent())
                .distance(Long.parseLong(session.getAttribute("distance").toString()))
                .createAt(LocalDateTime.now()).build();
    }

}

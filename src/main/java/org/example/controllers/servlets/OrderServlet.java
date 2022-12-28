package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.controllers.managers.OrderManager;
import org.example.models.Car;
import org.example.models.Fleet;
import org.example.models.Order;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;

@WebServlet(name = "order", urlPatterns = AppURL.ORDER_SERVLET)
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("USER");
        if (nonNull(user)) {
            forward(AppURL.USER_SERVLET, req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
        /*createOrder(req, resp);*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("USER");
        if (nonNull(user)) {
            createOrder(req, resp);
            forward(AppURL.ORDER_JSP, req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp) {
        OrderManager manager = new OrderManager();
        Order order = manager.create(buildOrder(req, resp));
        Fleet fleet = Fleet.getInstance();
        fleet.setCarsOnRoute(order.getCars());
    }

    private Order buildOrder(HttpServletRequest req, HttpServletResponse resp) {
        final HttpSession session = req.getSession();
        return Order.builder().cars((List<Car>) session.getAttribute("cars"))
                .client((User) session.getAttribute("USER"))
                .cost(Long.valueOf(session.getAttribute("cost").toString()))
                .addressDeparture((String) session.getAttribute("addressDeparture"))
                .destination((String) session.getAttribute("destination"))
                .discount(Integer.parseInt(session.getAttribute("discount").toString()))
                .distance(Long.valueOf(session.getAttribute("distance").toString()))
                .createAt(LocalDateTime.now()).build();
    }

}

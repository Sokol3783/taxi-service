package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.controllers.managers.OrderManager;
import org.example.models.Car;
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
            createOrder(req, resp);
            forward(AppURL.ORDER_JSP, req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp) {
        OrderManager manager = new OrderManager();
        manager.create(buildOrder(req, resp));
    }

    private Order buildOrder(HttpServletRequest req, HttpServletResponse resp) {
        final HttpSession session = req.getSession();
        return Order.builder().cars((List<Car>) session.getAttribute("cars"))
                .client((User) session.getAttribute("USER"))
                .cost((Long) session.getAttribute("cost"))
                .addressDeparture((String) session.getAttribute("addressDeparture"))
                .destination((String) session.getAttribute("destination"))
                .discount((int) session.getAttribute("discount"))
                .distance((Long) session.getAttribute("distance"))
                .createAt(LocalDateTime.now()).build();
    }

}

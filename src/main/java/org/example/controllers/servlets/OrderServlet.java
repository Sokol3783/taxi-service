package org.example.controllers.servlets;

import org.example.AppURL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "order", urlPatterns = AppURL.ORDER_SERVLET)
public class OrderServlet extends HttpServlet {
/*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        User user = (User) req.getSession().getAttribute("USER");
        if (nonNull(user)) {
            forward(UserManager.getRoleURL(user), req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
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
        String[] params = {"carCategory", "passengers", "addressDeparture", "coordinatesDeparture", "destination", "coordinatesDestination"};
        HttpSession session = req.getSession();
        for (String param : params) {
            session.removeAttribute(param);
        }
    }

    private Order buildOrder(HttpServletRequest req) {
        final HttpSession session = req.getSession();
        List<Car> cars = (List<Car>) session.getAttribute("cars");
        Discount discount = (Discount) session.getAttribute("discount");
        return Order.builder().cars(cars)
                .client((User) session.getAttribute("USER"))
                .cost(countCost(cars, discount, session))
                .addressDeparture((String) session.getAttribute("addressDeparture"))
                .destination((String) session.getAttribute("destination"))
                .percentDiscount(discount.getPercent())
                .distance(Long.parseLong(session.getAttribute("distance").toString()))
                .createAt(LocalDateTime.now()).build();
    }

    private long countCost(List<Car> cars, Discount discount, HttpSession session) {
        Map<CarCategory, Integer> prices = SalesManagement.getInstance().getPrices();
        long distance = Long.parseLong(session.getAttribute("distance").toString());
        long cost = 0;
        for (Car car : cars) {
            long costWithoutDiscount = prices.get(car.getCategory()) * distance;
            long sumDiscount = costWithoutDiscount / 100 * discount.getPercent();
            cost += costWithoutDiscount - sumDiscount;
        }
        session.setAttribute("cost", cost);
        return cost;
    }

}

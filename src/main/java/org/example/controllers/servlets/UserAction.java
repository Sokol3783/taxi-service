package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.models.Car;
import org.example.models.Fleet;
import org.example.models.taxienum.CarCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.AppURL.ORDER_SERVLET;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;
import static org.example.models.taxienum.CarCategory.getCategory;

@WebServlet(name = "user-action", urlPatterns = AppURL.USER_ACTION_SERVLET)
public class UserAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String act = request.getParameter("act");
        setOrderValues(request);
        if (act != null) {
            switch (act) {
                case "findCar" -> findCar(request, response);
                case "otherCategory" -> findCarExceptPreferCategory(request, response);
                case "severalCars" -> findSeveralCars(request, response);
                case "createOrder" -> moveToOrder(request, response);
            }

        } else {
            forward(AppURL.USER_JSP, request, response);
        }
    }

    private void setOrderValues(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("carCategory", request.getParameter("car-category"));
        session.setAttribute("passengers", request.getParameter("passengers"));
        session.setAttribute("cost", request.getParameter("cost"));
        session.setAttribute("percentDiscount", request.getParameter("percentDiscount"));
        session.setAttribute("distance", request.getParameter("distance"));
        session.setAttribute("addressDeparture", request.getParameter("addressDeparture"));
        session.setAttribute("coordinatesDeparture", request.getParameter("coordinatesDeparture"));
        session.setAttribute("destination", request.getParameter("destination"));
        session.setAttribute("coordinatesDestination", request.getParameter("coordinatesDestination"));
    }

    private void findCarExceptPreferCategory(HttpServletRequest request,
                                             HttpServletResponse response) throws ServletException {
        CarCategory category = getCategory(request.getParameter("car-category"));
        int passengers = Integer.parseInt(request.getParameter("passengers"));

        List<Car> cars = findFreeCarExceptCategory(category, passengers);
        if (cars.isEmpty()) {
            request.getSession().setAttribute("carError", "noFreeCarAnotherCategory");
            forward(AppURL.USER_JSP, request, response);
            return;
        }
        request.getSession().setAttribute("cars", cars);
        sendRedirect(response, request.getContextPath() + ORDER_SERVLET);
    }

    private List<Car> findFreeCarExceptCategory(CarCategory category, int passengers) {
        Fleet fleet = Fleet.getInstance();
        List<Car> cars = new ArrayList<>();
        Optional<Car> freeCar = fleet.findFreeCarExceptCategory(category, passengers);
        freeCar.ifPresent(cars::add);
        return cars;
    }

    private void findSeveralCars(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        CarCategory category = getCategory(request.getParameter("car-category"));
        int passengers = Integer.parseInt(request.getParameter("passengers"));
        Fleet fleet = Fleet.getInstance();
        if (passengers > 0) {
            List<Car> cars = fleet.findFreeSeveralCarsByCategory(category, passengers);
            if (cars.isEmpty()) {
                request.getSession().setAttribute("carError", "noFreeCarsInCategory");
                forward(AppURL.USER_JSP, request, response);
                return;
            }
            request.getSession().setAttribute("cars", cars);
            sendRedirect(response, request.getContextPath() + ORDER_SERVLET);
        }
    }

    private void findCar(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int passengers = Integer.parseInt(request.getParameter("passengers"));
        CarCategory category = getCategory(request.getParameter("car-category"));
        List<Car> cars = findFreeCarByCategory(category, passengers);
        int count = cars != null ? cars.stream().mapToInt(Car::getCapacity).sum() : 0;
        if (count >= passengers) {
            request.getSession().setAttribute("cars", cars);
        } else {
            request.getSession().setAttribute("alternative", true);
            request.getSession().setAttribute("carError", "noFreeCarCategory");
        }
        moveToUser(request, response);
    }

    private void moveToUser(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        forward(AppURL.USER_JSP, request, response);
    }


    private void moveToOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int passengers = Integer.parseInt(request.getParameter("passengers"));
        List<Car> cars = (List<Car>) request.getSession().getAttribute("cars");
        int count = cars != null ? cars.stream().mapToInt(Car::getCapacity).sum() : 0;
        if (count >= passengers) {
            forward(ORDER_SERVLET, request, response);
        } else {
            request.getSession().setAttribute("carError", "noCarsSelected");
            moveToUser(request, response);
        }
    }

    private List<Car> findFreeCarByCategory(CarCategory category, int passengers) {
        Fleet fleet = Fleet.getInstance();
        List<Car> cars = new ArrayList<>();
        Optional<Car> freeCar = fleet.findFreeCarByCategory(category, passengers);
        freeCar.ifPresent(cars::add);
        return cars;
    }
}

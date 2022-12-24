package org.example.controllers.servlets;

import static java.lang.System.out;
import static org.example.controllers.servlets.Util.forward;
import static org.example.models.taxienum.CarCategory.getCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.AppURL;
import org.example.models.Car;
import org.example.models.Fleet;
import org.example.models.taxienum.CarCategory;

@WebServlet(name = "user-action", urlPatterns = AppURL.USER_ACTION_SERVLET)
public class UserAction extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    String act = request.getParameter("act");
    setCarValues(request);
    if (act != null) {
      switch (act) {
        case "findCar" -> findCarPreferCategory(request, response);
        case "differentCategory" -> findCarExceptPreferCategory(request, response);
        case "severalCars" -> findSeveralCars(request, response);
        case "createOrder" -> createOrder(request, response);
      }
    }
  }

  private void setCarValues(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.setAttribute("carCategory", request.getParameter("car-category"));
    session.setAttribute("passengers", request.getParameter("passengers"));
  }

  private void findCarExceptPreferCategory(HttpServletRequest request,
      HttpServletResponse response) throws ServletException {
    CarCategory category = getCategory(request.getParameter("car-category"));
    int passengers = Integer.parseInt(request.getParameter("passengers"));
    if (passengers > 0) {
      List<Car> cars = findFreeCarExceptCategory(category, passengers);
      if (cars.isEmpty()) {
        request.getSession().setAttribute("AlternateCategoryCapacity", false);
        return;
      }
      request.getSession().setAttribute("Cars", cars);
    }
    forward(request.getHeader("referer"), request, response);
  }

  private List<Car> findFreeCarExceptCategory(CarCategory category, int passengers) {
    Fleet fleet = Fleet.getInstance();
    List<Car> cars = new ArrayList<>();
    Optional<Car> freeCar = fleet.findFreeCarExceptCategory(category, passengers);
    if (freeCar.isPresent()) {
      cars.add(freeCar.get());
    }
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
        request.getSession().setAttribute("AlternateCategoryCapacity", false);
        return;
      }
      request.getSession().setAttribute("Cars", cars);
    }
    forward(request.getHeader("referer"), request, response);
  }

  private void createOrder(HttpServletRequest request, HttpServletResponse response) {
    out.println("create order");

  }

  private void findCarPreferCategory(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    CarCategory category = getCategory(request.getParameter("car-category"));
    int passengers = Integer.parseInt(request.getParameter("passengers"));
    if (passengers > 0) {
      List<Car> cars = findFreeCarByCategory(category, passengers);
      if (cars.isEmpty()) {
        request.getSession().setAttribute("alternativeSolution", true);
        return;
      }
      request.getSession().setAttribute("Cars", cars);
    }
    forward(request.getHeader("referer"), request, response);
  }

  private List<Car> findFreeCarByCategory(CarCategory category, int passengers) {
    Fleet fleet = Fleet.getInstance();
    List<Car> cars = new ArrayList<>();
    Optional<Car> freeCar = fleet.findFreeCarByCategory(category, passengers);
    if (freeCar.isPresent()) {
      cars.add(freeCar.get());
    }
    return cars;
  }
}

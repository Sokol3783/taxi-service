package org.example.controllers.servlets;

import static java.lang.System.out;
import static org.example.controllers.servlets.Util.sendRedirect;
import static org.example.models.taxienum.CarCategory.getCategory;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.models.Car;
import org.example.models.taxienum.CarCategory;

@WebServlet(name = "userAction", urlPatterns = "/user-action")
public class UserAction extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    String act = request.getParameter("act");

    if (act != null) {
      switch (act) {
        case "findCar" -> findCar(request, response);
        case "createOrder" -> createOrder(request, response);
      }
    }
  }

  private void createOrder(HttpServletRequest request, HttpServletResponse response) {
    out.println("create order");
  }

  private void findCar(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    CarCategory category = getCategory(request.getParameter("car-category"));
    int passengers = Integer.parseInt(request.getParameter("passengers"));
    if (passengers > 0) {
      List<Car> cars = findFreeCarByCategory(category, passengers);
    } else {
      sendRedirect(response, request.getHeader("referer"));
    }
  }

  private List<Car> findFreeCarByCategory(CarCategory category, int passengers) {
    List<Car> cars = new ArrayList<>();

    return cars;
  }

  private List<Car> findFreeCar(CarCategory category, int passengers) {
    List<Car> cars = new ArrayList<>();

    return cars;
  }
}

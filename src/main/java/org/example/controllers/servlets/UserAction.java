package org.example.controllers.servlets;

import static java.lang.System.out;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "user", urlPatterns = "/user")
public class UserAction extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
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

  private void findCar(HttpServletRequest request, HttpServletResponse response) {
    out.println("find car");
  }
}

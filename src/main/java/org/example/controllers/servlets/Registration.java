package org.example.controllers.servlets;


import static org.example.controllers.servlets.Util.forward;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.AppUrl;

@WebServlet(name = "registration", urlPatterns = "/registration")
public class Registration extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    forward(AppUrl.REGISTRATION, request, response);
  }

}

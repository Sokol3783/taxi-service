package org.example.controllers.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "errorHandler",
        urlPatterns = {"/errorHandler"})
public class ErrorHandler extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        // Analyze the servlet exception
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.write(
                "<html><head>\n"
                        + "       <meta charset=\"utf-8\">\n"
                        + "       <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "       <link rel=\"stylesheet\" href=\"./styles/bootstrap-reboot.css\">\n"
                        + "       <link rel=\"stylesheet\" href=\"./styles/bootstrap.css\">\n"
                        + "       <link rel=\"stylesheet\" href=\"./styles/style.css\">\n"
                        + "       <title> Taxi </title>\n"
                        + "    </head><body class=\"container vh-100 background\">");
        if (statusCode != 500) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
            out.write("<strong>Requested URI</strong>:" + requestUri);
        } else {
            out.write("<h3>Exception Details</h3>");
            out.write("<ul><li>Servlet Name:" + servletName + "</li>");
            out.write("<li>Exception Name:" + throwable.getClass().getName() + "</li>");
            out.write("<li>Requested URI:" + requestUri + "</li>");
            out.write("<li>Exception Message:" + throwable.getMessage() + "</li>");
            out.write("</ul>");
        }

        out.write("<br><br>");
        out.write("<a href=" + request.getContextPath() + "/login" + ">Home Page</a>");
        out.write("</body></html>");
    }

}

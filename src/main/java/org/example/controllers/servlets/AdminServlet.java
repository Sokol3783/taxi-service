package org.example.controllers.servlets;

import org.example.AppURL;
import org.example.controllers.managers.ReportManager;
import org.example.controllers.managers.UserManager;
import org.example.models.User;
import org.example.models.taxienum.UserRole;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;
import static org.example.controllers.servlets.Util.forward;
import static org.example.controllers.servlets.Util.sendRedirect;

@WebServlet(name = "admin", urlPatterns = AppURL.ADMIN_SERVLET)
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        moveToAdmin(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        moveToAdmin(req, resp);
    }

    private void moveToAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        User user = (User) req.getSession().getAttribute("USER");
        if (nonNull(user)) {
            if (user.getRole() == UserRole.ADMIN) {
                fillDefaultPeriod(req);
                req.getSession().setAttribute("report", getReport(req));
            }
            forward(UserManager.getRoleURL(user), req, resp);
        } else {
            sendRedirect(resp, req.getContextPath() + AppURL.LOGIN_SERVLET);
        }
    }

    private JSONArray getReport(HttpServletRequest req) {
        ReportManager report = ReportManager.getInstance();
        User filteredUser = (User) req.getSession().getAttribute("filterUser");
        LocalDate start = getLocaleDate(req, "startPeriod");
        LocalDate end = getLocaleDate(req, "endPeriod");
        if (nonNull(filteredUser)) {
            return report.getOrderByPeriod(start, end);
        } else {
            return report.getOrderByPeriodAndClient(start, end, filteredUser);
        }
    }

    private void fillDefaultPeriod(HttpServletRequest req) {
        LocalDate startPeriod = getLocaleDate(req, "startPeriod");
        LocalDate endPeriod = getLocaleDate(req, "endPeriod");
        LocalDateTime start = LocalDate.now().atStartOfDay();
        if (!nonNull(startPeriod)) {
            req.setAttribute("startPeriod", start);
        }
        if (!nonNull(endPeriod)) {
            req.setAttribute("endPeriod", start.plusDays(1));
        }
    }

    private LocalDate getLocaleDate(HttpServletRequest req, String attributeName) {
        return (LocalDate) req.getAttribute(attributeName);
    }

}

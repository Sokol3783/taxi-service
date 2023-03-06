package org.example.controllers.servlets;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "registration", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
/*
    private static final Logger log = LoggerFactory.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        forward(AppURL.REGISTRATION_JSP, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        User user = createUser(request);
        if (nonNull(user)) {
            UserManager manager = new UserManager();
            try {
                manager.create(user);
            } catch (SQLException e) {
                log.error(LOGIN_ERROR, e);
                sendRedirect(response, request.getHeader("referer"));
            }

        }
    }

    private User createUser(HttpServletRequest request) {
        if (validatePassword(request)) {
            User user = User.builder().firstName(request.getParameter("first_name"))
                    .secondName(request.getParameter("last_name"))
                    .email(request.getParameter("email"))
                    .phone(request.getParameter("phone"))
                    .password(request.getParameter("password"))
                    .role(UserRole.getRole(request.getParameter("role")))
                    .birthDate(LocalDateConverter.convertToEntityAttribute(
                            request.getParameter("birthday")))
                    .build();
            return user;
        }
        return null;
    }

    private boolean validatePassword(HttpServletRequest request) {
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("password_check");
        return password.compareTo(passwordRepeat) == 0;
    }
    
 */

}

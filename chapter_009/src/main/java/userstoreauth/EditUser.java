package userstoreauth;
/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * doGet - Edit personal data. Takes user from session attribute.
 */

import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUser extends HttpServlet {

    private static final UserStoreTestVer2 USERSTORE = new UserStoreTestVer2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute("login");
        UserVer2 user = USERSTORE.getByLogin(login);
        req.setAttribute("user", user);
        req.getRequestDispatcher(Auth.EDIT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        UserVer2 user = USERSTORE.getByLogin(login);
        if (!password.isEmpty()) {
            user.setPassword(password);
        }
        if (!name.isEmpty()) {
            user.setName(name);
        }
        if (!email.isEmpty()) {
            user.setEmail(email);
        }
        USERSTORE.update(user);

        if (req.getParameter("role") != null) {
            String role = req.getParameter("role");
            user.setRole(role);
            USERSTORE.update(user);
            resp.sendRedirect(String.format("%s/auth/admin", req.getContextPath()));
            return;
        }
        resp.sendRedirect(String.format("%s/auth", req.getContextPath()));
    }
}

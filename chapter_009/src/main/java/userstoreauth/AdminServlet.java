package userstoreauth;

import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * doGet - show all users with edit/delete buttons and add new.
 * doPost - add new user. All data validation (incl. not null) - in JSP.
 */
public class AdminServlet extends HttpServlet {

    private static final UserStoreTestVer2 USERSTORE = new UserStoreTestVer2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", USERSTORE.getAll());
        req.getRequestDispatcher(Auth.ADMIN_ALL_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        if(login == null || login.isEmpty()) {
            req.getRequestDispatcher(Auth.ADD_ERR_JSP).forward(req, resp);
            return;
        }
        if (USERSTORE.getByLogin(login) == null) {
            USERSTORE.addUser(new UserVer2(login, password, name, email, Timestamp.valueOf(LocalDateTime.now()), role));
            resp.sendRedirect(String.format("%s/auth/admin", req.getContextPath()));
        } else {
            req.getRequestDispatcher(Auth.ADD_ERR_JSP).forward(req, resp);
        }
    }
}

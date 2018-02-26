package userstoreauth;

import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Show information about all users from db.
 */
public class ShowUsersAuth extends HttpServlet {

    private static final UserStoreTestVer2 USERSTORE = new UserStoreTestVer2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", USERSTORE.getAll());
        req.getRequestDispatcher(Auth.USER_ALL_JSP).forward(req, resp);
    }
}

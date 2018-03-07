package userstoreauth.servlets;

import userstoreauth.Const;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreMb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Show information about all users from db.
 */
public class ShowUsers extends HttpServlet {

    private static final UserStoreMb USERSTORE = new UserStoreMb();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", USERSTORE.getAll());
        UserVer2 user = (UserVer2) req.getSession().getAttribute(Const.CURRENT_USER);
        if (user != null && Const.ADMIN.equals(user.getRole())) {
            req.setAttribute("countries", USERSTORE.getAllCountries());
            req.getRequestDispatcher(Const.ADMIN_ALL_JSP).forward(req, resp);
            return;
        }
        req.getRequestDispatcher(Const.USER_ALL_JSP).forward(req, resp);
    }
}

package userstoreauth.servlets;

import userstoreauth.Const;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreMb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * doGet - check, if login already exist (for \WEB-INF\auth\UsersViewAdmin.jsp).
 * doPost - add user to db.
 */
public class AddUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserStoreMb userStoreMb = (UserStoreMb) req.getServletContext().getAttribute("userStoreMb");
        boolean valid = userStoreMb.getByLogin(req.getParameter("login")) == null;
        if (valid) {
            resp.getWriter().write("valid");
        } else {
            resp.getWriter().write("invalid");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserStoreMb userStoreMb = (UserStoreMb) req.getServletContext().getAttribute("userStoreMb");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String role = req.getParameter("role");
        if(login == null || login.isEmpty()) {
            req.getRequestDispatcher(Const.ADD_ERR_JSP).forward(req, resp);
            return;
        }
        if (userStoreMb.getByLogin(login) == null) {
            userStoreMb.addUser(new UserVer2(login, password, name, email, country, city, Timestamp.valueOf(LocalDateTime.now()), role));
            resp.sendRedirect(String.format("%s/auth", req.getContextPath()));
        } else {
            req.getRequestDispatcher(Const.ADD_ERR_JSP).forward(req, resp);
        }
    }
}

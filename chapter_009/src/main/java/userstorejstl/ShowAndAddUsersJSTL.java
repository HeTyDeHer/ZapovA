package userstorejstl;

import common.User;
import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 1. Реализовать MVC JSTL [#2516]
 * Servlet to get data from database.
 * Get: If parameter "login" exists, update user with this login. If not, just show all users.
 * Post: Add new User.
 */
public class ShowAndAddUsersJSTL extends HttpServlet {

    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", USERSTORE.getAll());
        req.getRequestDispatcher("/WEB-INF/jstltest/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        if(login == null || login.isEmpty()) {
            req.getRequestDispatcher("/WEB-INF/jstltest/AddError.jsp").forward(req, resp);
            return;
        }
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        if (USERSTORE.getByLogin(login) == null) {
            USERSTORE.addUser(new User(login, name, email, Timestamp.valueOf(LocalDateTime.now())));
            resp.sendRedirect(req.getContextPath());
        } else {
            req.getRequestDispatcher("/WEB-INF/jstltest/AddError.jsp").forward(req, resp);
        }
    }

}

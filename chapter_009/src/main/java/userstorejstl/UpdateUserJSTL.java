package userstorejstl;

import common.User;
import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Реализовать MVC JSTL [#2516]
 * Servlet to update users data.
 */
public class UpdateUserJSTL extends HttpServlet {
    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User user = USERSTORE.getByLogin(login);
        if (!name.isEmpty()) {
            user.setName(name);
        }
        if (!email.isEmpty()) {
            user.setEmail(email);
        }
        USERSTORE.update(user);
        resp.sendRedirect(req.getContextPath());
    }
}


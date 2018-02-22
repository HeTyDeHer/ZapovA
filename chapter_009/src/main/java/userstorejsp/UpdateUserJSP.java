package userstorejsp;

import common.User;
import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *1. Перенести все виды из предыдущего задания на JSP [#2515]
 * Servlet to update users data.
 */
public class UpdateUserJSP extends HttpServlet {
    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/updatejsp/index.jsp").forward(req, resp);
    }

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
        resp.sendRedirect(String.format("%s/indexold.jsp", req.getContextPath()));
    }
}


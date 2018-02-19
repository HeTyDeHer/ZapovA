package userstorejsp;

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
 * 1. Перенести все виды из предыдущего задания на JSP [#2515]
 * Servlet to get data from database.
 */
public class ShowAndAddUsersJSP extends HttpServlet {

    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(String.format("%s/index.jsp", req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        if (USERSTORE.getByLogin(login) == null) {
            USERSTORE.addUser(new User(login, name, email, Timestamp.valueOf(LocalDateTime.now())));
            resp.sendRedirect(String.format("%s/index.jsp", req.getContextPath()));
        } else {
            resp.sendRedirect(String.format("%s/showjsp/adderr.jsp", req.getContextPath()));
        }
    }

}

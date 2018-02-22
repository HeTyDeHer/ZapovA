package userstorejsp;

import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Перенести все виды из предыдущего задания на JSP [#2515]
 * Servlet to delete users from database.
 */
public class DeleteUserJSP extends HttpServlet {
    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        USERSTORE.delete(login);
        resp.sendRedirect(String.format("%s/indexold.jsp", req.getContextPath()));
    }
}

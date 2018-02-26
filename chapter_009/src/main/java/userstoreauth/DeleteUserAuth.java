package userstoreauth;

import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Реализовать MVC JSTL [#2516]
 * Servlet to delete users from database. Admin access rights.
 */
public class DeleteUserAuth extends HttpServlet {

    private static final UserStoreTestVer2 USERSTORE = new UserStoreTestVer2();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        USERSTORE.delete(login);
        resp.sendRedirect(String.format("%s/auth/admin", req.getContextPath()));
    }
}

package servlets;

import service.dao.models.User;
import service.rep.UserRep;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * doGet - additional redirect to main page.
 */

public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        UserRep userRep = new UserRep();
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null) {
            userRep.deleteSession(user);
        }
        req.getSession().invalidate();
        Cookie cookie = new Cookie("savedSession", "");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);
    }
}

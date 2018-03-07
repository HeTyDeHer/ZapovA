package userstoreauth.servlets;

import userstoreauth.Const;
import userstoreauth.model.UserVer2;
import userstoreauth.service.ManageSessions;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Log Out.
 * Delete session attribute "current user" and user cookies.
 */
public class LogOut extends HttpServlet {

    private static final ManageSessions SESSIONS = new ManageSessions();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserVer2 user = (UserVer2) req.getSession().getAttribute(Const.CURRENT_USER);
        if (user != null) {
            SESSIONS.deleteSessions(user.getLogin());
        }
        req.getSession().invalidate();
        this.destroyCookie(resp, Const.SAVE);
        resp.sendRedirect(String.format("%s/auth", req.getContextPath()));
    }

    private void destroyCookie (HttpServletResponse resp, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);
    }

}

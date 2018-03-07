package userstoreauth.servlets;

import userstoreauth.Const;
import userstoreauth.model.UserVer2;
import userstoreauth.service.ManageSessions;
import userstoreauth.service.UserStoreMb;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Login servlet.
 * doPost: if login/password correct, saves current user to session attribute 'current user'.
 * if "remember" checkbox was activated, sends cookies with id.
 * if user role is admin, redirects to admin page.
 */
public class Login extends HttpServlet {

    private static final UserStoreMb USERSTORE = new UserStoreMb();
    private static final ManageSessions SESSIONS = new ManageSessions();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Const.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserVer2 user = USERSTORE.getByLogin(req.getParameter("login"));
        if (this.loginAndPasswordIsValid(req, resp, user)) {
            req.getSession().setAttribute(Const.CURRENT_USER, user);
            if ("true".equals(req.getParameter("remember"))) {
                this.rememberUser(resp, user);
            }
            resp.sendRedirect(String.format("%s/auth", req.getContextPath()));
        }

    }


    private boolean loginAndPasswordIsValid(HttpServletRequest req, HttpServletResponse resp, UserVer2 user) throws ServletException, IOException {
        boolean result = true;
        String password = req.getParameter("password");
        if (user == null) {
            req.setAttribute("error", "wrong login!");
            req.getRequestDispatcher(Const.LOGIN_JSP).forward(req, resp);
            result = false;
        } else if (!password.equals(user.getPassword())) {
            req.setAttribute("error", "wrong login or password!");
            req.getRequestDispatcher(Const.LOGIN_JSP).forward(req, resp);
            result = false;
        }
        return result;
    }

    private void rememberUser (HttpServletResponse resp, UserVer2 user) {
        String uuid = UUID.randomUUID().toString();
        SESSIONS.deleteSessions(user.getLogin());
        SESSIONS.rememberSession(uuid, user);
        Cookie cookie = new Cookie(Const.SAVE, uuid);
        cookie.setPath("/");
        cookie.setMaxAge(365*24*60*60);
        resp.addCookie(cookie);
    }
}

package servlets;

import com.google.gson.Gson;
import service.dao.models.User;
import service.rep.UserRep;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * doGet - check, if current user is logged
 * doPost - login user.
 */

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null) {
            resp.getWriter().write(user.getLogin());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRep userRep = new UserRep();
        User user = userRep.readByLogin(req.getParameter("login"));
        if (user != null && user.getPassword().equals(req.getParameter("password"))) {
            req.getSession().setAttribute("currentUser", user);
            if ("true".equals(req.getParameter("remember"))) {
                this.rememberUser(resp, userRep, user);
            }
            String response = new Gson().toJson(user);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(response);
        }
    }

    private void rememberUser (HttpServletResponse resp, UserRep userRep, User user) {
        String uuid = UUID.randomUUID().toString();
        userRep.deleteSession(user);
        userRep.saveSession(user, uuid);
        Cookie cookie = new Cookie("savedSession", uuid);
        cookie.setPath("/");
        cookie.setMaxAge(365*24*60*60);
        resp.addCookie(cookie);
    }
}

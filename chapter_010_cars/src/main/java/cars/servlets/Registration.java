package cars.servlets;

import cars.dao.HibernateDAO;
import cars.models.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Register new user.
 * doGet - check, if login is valid (not empty, nor busy).
 * doPost - register.
 */
public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean valid = false;
        String login = req.getParameter("login");
        if (login != null && !login.isEmpty()) {
            login = login.toLowerCase();
            HibernateDAO<Integer, User> hibernateDAO = new HibernateDAO<>();
            Map<String, Object> hm = new HashMap<>();
            hm.put("login", login);
            if (hibernateDAO.read(User.class, hm).isEmpty()) {
                valid = true;
            }
        }
        String result = new Gson().toJson(valid);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String login = req.getParameter("login").toLowerCase();
        String password = req.getParameter("password");
        HibernateDAO<Integer, User> hibernateDAO = new HibernateDAO<>();
        User user = new User(login, password);
        id = hibernateDAO.create(user);
        if (id != -1) {
            req.getSession().setAttribute("user", user);
        }
        String result = new Gson().toJson(id);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}

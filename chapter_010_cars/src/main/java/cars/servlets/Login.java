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
import java.util.List;
import java.util.Map;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Login.
 * doGet - check, if user already logged.
 * doPost - login.
 */
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = "false";
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            response = user.getLogin();
        }
        String result = new Gson().toJson(response);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = "false";
        HibernateDAO<Integer, User> hibernateDAO = new HibernateDAO<>();
        Map<String, Object> params = new HashMap<>();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
            login = login.toLowerCase();
            params.put("login", login);
            params.put("password", password);
            List<User> user = hibernateDAO.read(User.class, params);
            if (!user.isEmpty()) {
                response = user.get(0).getLogin();
                req.getSession().setAttribute("user", user.get(0));
            }
        }
        String result = new Gson().toJson(response);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}

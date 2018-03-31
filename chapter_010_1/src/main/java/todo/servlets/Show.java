package todo.servlets;

import com.google.gson.Gson;
import todo.service.DBConnection;
import todo.service.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Создать TO DO list [#3786].
 * Show all items.
 */
public class Show extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = new Gson().toJson(new ItemDao().readAll());
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    @Override
    public void destroy() {
        DBConnection.close();
    }
}

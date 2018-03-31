package todo.servlets;

import com.google.gson.Gson;
import todo.model.Item;
import todo.service.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * Создать TO DO list [#3786].
 * Add new Item. Returns item id.
 */
public class Add extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int result = -1;
        String description = req.getParameter("description");
        if (null != description) {
            int id = new ItemDao().create(new Item(description, Timestamp.valueOf(LocalDateTime.now()), false));
            if (-1 != id) {
                result = id;
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(result));
    }
}

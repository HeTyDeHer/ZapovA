package cars.servlets;

import cars.dao.HibernateDAO;
import cars.models.Make;
import cars.models.Model;
import cars.models.serializers.MakeSerializer;
import cars.models.serializers.ModelSerializer;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Show info about models (from database).
 */
public class ShowData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String response;
        switch(action) {
            case "make": response = this.getAllMakes(req);
                        break;
            case "model": response = this.getModelsOfMake(req);
                break;
            default: response = "false";
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(response);
    }


    private String getAllMakes(HttpServletRequest req) {
        HibernateDAO<Integer, Make> hibernateDAO = new HibernateDAO<>();
        HashMap<String, Object> hm = new HashMap<>();
        return new GsonBuilder()
                .registerTypeAdapter(Make.class, new MakeSerializer())
                .create()
                .toJson(hibernateDAO.read(Make.class, hm));
    }

    private String getModelsOfMake(HttpServletRequest req) {
        Integer make_id = Integer.valueOf(req.getParameter("make_id"));
        HibernateDAO<Integer, Model> hibernateDAO = new HibernateDAO<>();
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("make_id", make_id);
        return new GsonBuilder()
                .registerTypeAdapter(Model.class, new ModelSerializer())
                .create()
                .toJson(hibernateDAO.read(Model.class, hm));

    }
}

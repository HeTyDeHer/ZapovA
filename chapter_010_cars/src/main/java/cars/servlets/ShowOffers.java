package cars.servlets;

import cars.dao.HibernateDAO;
import cars.models.CarOffer;
import cars.models.User;
import cars.models.serializers.CarOfferSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
 * Show offers.
 * If "for_user", show only offers of current user.
 */
public class ShowOffers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getContextPath());
        HibernateDAO<Integer, CarOffer> carOfferDAO = new HibernateDAO<>();
        Map<String, Object> hm = new HashMap<>();
        if ("true".equals(req.getParameter("for_user"))) {
            User user = (User) req.getSession().getAttribute("user");
            if (user != null) {
                hm.put("owner", user.getId());
            }
        }
        List<CarOffer> carOfferList = carOfferDAO.read(CarOffer.class, hm);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CarOffer.class, new CarOfferSerializer())
                .create();
        String result = gson.toJson(carOfferList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

}

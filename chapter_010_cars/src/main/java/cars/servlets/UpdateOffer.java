package cars.servlets;

import cars.dao.HibernateDAO;
import cars.models.CarOffer;
import cars.models.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Update offer.
 * current version - only change status (sold).
 */
public class UpdateOffer extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        boolean result = false;
        if (user != null && req.getParameter("offer_id") != null && req.getParameter("sold") != null) {
            int carofferId = Integer.valueOf(req.getParameter("offer_id"));
            boolean sold = Boolean.parseBoolean(req.getParameter("sold"));
            HibernateDAO<Integer, CarOffer> offer = new HibernateDAO<>();
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("id", carofferId);
            List<CarOffer> carOfferList = offer.read(CarOffer.class, hm);
            if (!carOfferList.isEmpty() && carOfferList.get(0).getOffer_owner().equals(user)) {
                CarOffer carOffer = carOfferList.get(0);
                carOffer.setSold(sold);
                offer.update(carOffer);
                result = true;
            }
        }
        String response = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(response);
    }
}

package cars.servlets;

import cars.dao.CarOfferDAO;
import cars.dao.HibernateDAO;
import cars.models.CarOffer;
import cars.models.User;
import cars.models.serializers.CarOfferSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Request parameters:
 *      "for_user", show only offers of current user,
 *      "from_date" show from now - parameter "days",
 *      "by_make" show only parameter "make",
 *      default show all.
 */
public class ShowOffers extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ShowOffers.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        List<CarOffer> carOfferList;
        switch (action) {
            case "for_user": carOfferList = this.getForUser(req);
                break;
            case "by_date" : carOfferList = this.getFromDate(req);
                break;
            case "by_photo" : carOfferList = this.getWithPhoto();
                break;
            case "by_make" : carOfferList = this.getByMake(req);
                break;
            default: carOfferList =  new CarOfferDAO().read(CarOffer.class, new HashMap<>());
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CarOffer.class, new CarOfferSerializer())
                .create();
        String response = gson.toJson(carOfferList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(response);
    }

    /**
     * Get carOffers of current user.
     * @param req HttpServletRequest
     * @return List, carOffers of current user.
     */
    private List<CarOffer> getForUser(HttpServletRequest req) {
        HibernateDAO<Integer, CarOffer> carOfferDAO = new HibernateDAO<>();
        Map<String, Object> hm = new HashMap<>();
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            hm.put("owner", user.getId());
        }
        return carOfferDAO.read(CarOffer.class, hm);
    }

    /**
     * Get offers for last N days (req parameter days).
     * @param req HttpServletRequest.
     * @return List offers.
     */
    private List<CarOffer> getFromDate(HttpServletRequest req) {
        CarOfferDAO carOfferDAO = new CarOfferDAO();
        int days;
        try {
            days = Integer.parseInt(req.getParameter("parameter"));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            days = Integer.MAX_VALUE;
        }
        return carOfferDAO.readLastDays(days);
    }

    /**
     * Get offers with photo.
     * @return List <offers with photo>.
     */
    private List<CarOffer> getWithPhoto() {
        List<CarOffer> carOfferList = new CarOfferDAO().read(CarOffer.class, new HashMap<>());
        carOfferList.removeIf(carOffer -> carOffer.getImages().isEmpty());
        return carOfferList;
    }


    /**
     * Get offers byMake (req parameter make_id).
     * @param req httpServletRequest
     * @return List offers.
     */
    private List<CarOffer> getByMake(HttpServletRequest req) {
        Integer makeId = -1;
        try {
            makeId = Integer.parseInt(req.getParameter("parameter"));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return new CarOfferDAO().readByMake(makeId);
    }


}

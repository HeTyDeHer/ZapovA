package servlets;

import com.google.gson.Gson;
import service.dao.AddressDAO;
import service.dao.AddressDAOImpl;
import service.dao.models.Music;
import service.dao.models.Role;
import service.dao.models.User;
import service.rep.CitiesRep;
import service.rep.MusicRep;
import service.rep.RoleRep;
import service.rep.UserRep;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Show data by request.
 * doPost - return data by filter,
 * doGet - return all data by type.
 */

public class ShowAll extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> filteredUsers = new ArrayList<>();
        if (req.getParameter("filter") != null) {
            switch (req.getParameter("filter")) {
                case "music": {
                    filteredUsers = this.getAllUsersOfMusic(req);
                    break;
                }
                case "role": {
                    UserRep userRep = new UserRep();
                    filteredUsers = userRep.readAllUsersByRole(new Role(req.getParameter("role"), null));
                    break;
                }
                case "address": {
                    int city_id = Integer.valueOf(req.getParameter("city"));
                    String street = req.getParameter("street");
                    int home = Integer.valueOf(req.getParameter("home"));
                    int apart = Integer.valueOf(req.getParameter("apart"));
                    UserRep userRep = new UserRep();
                    AddressDAO addressDAO = new AddressDAOImpl();
                    filteredUsers.add(userRep.readByAddress(addressDAO.readAddressModel(city_id, street, home, apart)));
                    break;
                }
            }
        }
        if (!filteredUsers.isEmpty()) {
            String result = new Gson().toJson(filteredUsers);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        }
    }

    /**
     * Get all users by music type from request.
     * @param req HttpServletRequest
     * @return list of users.
     */
    private List<User> getAllUsersOfMusic(HttpServletRequest req) {
        MusicRep musicRep = new MusicRep();
        UserRep userRep = new UserRep();
        List<Music> reqMusic = new ArrayList<>();
        for (Music music : musicRep.readAll()) {
            if(req.getParameter(music.getGenre()) != null) {
                reqMusic.add(music);
            }
        }
        List<User> result = new ArrayList<>();
        if (!reqMusic.isEmpty()) {
            result = userRep.readAllUsersOfMusic(reqMusic);
        }
        return result;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        if (req.getParameter("get") != null) {
            switch (req.getParameter("get")) {
                case "user": {
                    UserRep userRep = new UserRep();
                    result = new Gson().toJson(userRep.readAll());
                    break;
                }
                case "music": {
                    MusicRep musicRep = new MusicRep();
                    result = new Gson().toJson(musicRep.readAll());
                    break;
                }
                case "role": {
                    RoleRep roleRep = new RoleRep();
                    result = new Gson().toJson(roleRep.readAll());
                    break;
                }
                case "country": {
                    CitiesRep citiesRep = new CitiesRep();
                    result = new Gson().toJson(citiesRep.readAllCountries());
                    break;
                }
                case "city": {
                    String country = req.getParameter("country");
                    CitiesRep citiesRep = new CitiesRep();
                    result = new Gson().toJson(citiesRep.readAllCitiesOfCOuntry(country));
                    break;
                }
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}

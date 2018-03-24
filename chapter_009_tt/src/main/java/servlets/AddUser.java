package servlets;

import com.google.gson.Gson;
import service.dao.AddressDAO;
import service.dao.AddressDAOImpl;
import service.dao.models.Address;
import service.dao.models.Music;
import service.dao.models.Role;
import service.dao.models.User;
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
 * doGet - check if login arleady exists.
 * doPOst - add new User.
 */
public class AddUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRep userRep = new UserRep();
        String login = req.getParameter("login");
        User user = userRep.readByLogin(login);
        String result;
        if (user == null) {
            result = "valid";
        } else {
            result = "invalid";
        }
        result = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRep userRep = new UserRep();
        User user = this.createUser(req);
        int id = userRep.create(user);
        String result = new Gson().toJson(id);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);

    }


    private User createUser(HttpServletRequest req) {
        MusicRep musicRep = new MusicRep();
        RoleRep roleRep = new RoleRep();
        AddressDAO addressDAO = new AddressDAOImpl();
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        int home = Integer.parseInt(req.getParameter("home"));
        int apart = Integer.parseInt(req.getParameter("apart"));
        String userRole = req.getParameter("role");
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        Address address = new Address(country, city, street, home, apart, user);
        address.setId(addressDAO.create(address));
        user.setAddress(address);
        Role role = roleRep.read(userRole);
        user.setRole(role);
        List<Music> userMusic = new ArrayList<>();
        for (Music music : musicRep.readAll()) {
            if (req.getParameter(music.getGenre()) != null) {
                userMusic.add(music);
            }
        }
        user.setMusic(userMusic);
        return user;
    }
}

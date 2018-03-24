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
 * doGet - get User by ID.
 * doPost - edit User.
 */


public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        UserRep userRep = new UserRep();
        User user = userRep.read(id);
        String result = new Gson().toJson(user);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRep userRep = new UserRep();
        AddressDAO addressDAO = new AddressDAOImpl();
        User user = this.createUser(req);
        if (userRep.update(user)) {
            addressDAO.update(user.getAddress());
        }
        resp.sendRedirect(String.format("%s/admin", req.getContextPath()));


    }
    private User createUser(HttpServletRequest req) {
        MusicRep musicRep = new MusicRep();
        RoleRep roleRep = new RoleRep();
        int id = Integer.parseInt(req.getParameter("id"));
        int addressId = Integer.parseInt(req.getParameter("addressId"));
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
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        Address address = new Address(country, city, street, home, apart, user);
        address.setId(addressId);
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

package userstoreauth.servlets;

import com.google.gson.Gson;
import userstoreauth.service.UserStoreMb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Get all cities of country from db.
 * For UsersViewAdmin.jsp and EditUser.jsp.
 */
public class GetCitiesOfCountry extends HttpServlet {
    private final UserStoreMb us = new UserStoreMb();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> cities = us.getCitiesOfCountry(req.getParameter("country"));
        String json = new Gson().toJson(cities);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}

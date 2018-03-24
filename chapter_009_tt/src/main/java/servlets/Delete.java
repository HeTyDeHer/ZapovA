package servlets;

import com.google.gson.Gson;
import service.rep.UserRep;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Delete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRep userRep = new UserRep();
        String result;
        if (req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            userRep.delete(id);
            result = "true";
        } else {
            result = "false";
        }
        result = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}

package userstore;

import common.User;
import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class UserStoreServlet extends HttpServlet {
    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("<html>" +
                " <head>" +
                "  <meta charset='utf-8'>" +
                "  <title>TEST</title>" +
                " </head>" +
                " <body align='center'>" +
                " <form name='test' method='post'>" +
                "<p>login</p>" +
                "<p><input type='text'  name='login' required/></p>"+
                "<p>name</p>" +
                "<p><input type='text' name='name'/></p>"+
                "<p>email</p>" +
                "<p><input type='text' name='email'/></p>"+
                "<input type='submit'  name='submit' value='Add User'/>"+
                "<input type='submit'  name='submit' value='Edit User'/>"+
                "<input type='submit'  name='submit' value='Delete'/>"+
                " </form>" +
                " </body>" +
                "</html>");

        List<User> users = USERSTORE.getAll();
        for (User u : users) {
            resp.getWriter().write(u.toString() + "<br/>");
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("submit").equals("Edit User")) {
            this.doPut(req, resp);
        } else if (req.getParameter("submit").equals("Delete")) {
            this.doDelete(req, resp);
        } else {
            String login = req.getParameter("login");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String responce;
            if (USERSTORE.getByLogin(login) == null) {
                USERSTORE.addUser(new User(login, name, email, Timestamp.valueOf(LocalDateTime.now())));
                responce = "added<br/>";
            } else {
                responce = "this login already exists<br/>";
            }
            resp.getWriter().write(this.makeResponce(responce));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User user = USERSTORE.getByLogin(login);
        String responce;
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            USERSTORE.update(user);
            responce = "edited<br/>";
        } else {
            responce = "this login already exists<br/>";
        }
        resp.getWriter().write(this.makeResponce(responce));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String responce;
        if (USERSTORE.getByLogin(login) != null) {
            USERSTORE.delete(login);
            responce = "deleted<br/>";
        } else {
            responce = "this login does not exist<br/>";
        }
        resp.getWriter().write(this.makeResponce(responce));
    }

    @Override
    public void destroy() {
//        USERSTORE.closeSession();
    }

    private String makeResponce(String responce) {
        return "<html>" +
                "<head>" +
                "<meta charset='utf-8'>" +
                "<title>TEST</title>" +
                "</head>" +
                "<body align='center'>" +
                responce +
                "<a href='userstore'>back</a> " +
                " </body>" +
                "</html>";
    }
}

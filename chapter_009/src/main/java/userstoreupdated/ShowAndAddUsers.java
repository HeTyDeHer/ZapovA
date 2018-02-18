package userstoreupdated;

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
/**
 * 1. Реализовать приложения для работы с пользователем. [#2513]
 * Servlet to get data from database.
 */
public class ShowAndAddUsers extends HttpServlet {

    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder response = new StringBuilder();
        response.append("<html>" +
                " <head>" +
                "  <meta charset='utf-8'>" +
                "  <title>Show All</title>" +
                " </head>" +
                " <body align='center'>" +
                "<table border='1' align='center' width=60%>");
        List<User> users = USERSTORE.getAll();
        for (User u : users) {
            response.append("<tr><td width='22%'>");
            response.append(u.getLogin());
            response.append("</td><td width='22%'>");
            response.append(u.getName());
            response.append("</td><td width='22%'>");
            response.append(u.getEmail());
            response.append("</td><td width='22%'>");
            response.append(u.getCreated());
            response.append("</td><td colspan='2' align='center'>" +
                    "<form action='update' method=get>" +
                    "<input type='submit'  name='submit' value='Edit'/>" +
                    "<input type='hidden'  name='login' value='" + u.getLogin() + "'/>" +
                    "<input type='hidden'  name='name' value='" + u.getName() + "'/>" +
                    "<input type='hidden'  name='email' value='" + u.getEmail() + "'/>" +
                    "</form>" +
                    "<form action='delete' method=post>" +
                    "<input type='submit'  name='submit' value='Delete'/>" +
                    "<input type='hidden'  name='login' value='" + u.getLogin() + "'/>" +
                    "</form>");
            response.append("</td></tr>");
        }
        response.append("<form method=post><tr>" +
                "<td><input type ='text' placeholder='login' name = 'login' required></td>" +
                "<td><input type ='text' placeholder='name' name = 'name' required></td>" +
                "<td><input type ='text' placeholder='email' name = 'email' required></td>" +
                "<td></td>" +
                "<td align='center'><input type ='submit' value='Add New'></td>" +
                "</tr></table>");
        response.append("</table><a href='/test'>main menu</a> </body></html>");
        resp.getWriter().write(response.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String response;
        if (USERSTORE.getByLogin(login) == null) {
            USERSTORE.addUser(new User(login, name, email, Timestamp.valueOf(LocalDateTime.now())));
            response = "added<br/>";
        } else {
            response = "this login already exists<br/>";
        }
        resp.getWriter().write(this.makeResponse(response));

    }

    private String makeResponse(String response) {
        return "<html>" +
                "<head>" +
                "<meta charset='utf-8'>" +
                "<title>Add User</title>" +
                "</head>" +
                "<body align='center'>" +
                response +
                "<input type='button' onclick='history.back();' value='Back'/>" +
                "<br/><a href='/test'>Main menu</a> " +
                " </body>" +
                "</html>";
    }



}

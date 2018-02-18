package userstoreupdated;

import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Реализовать приложения для работы с пользователем. [#2513]
 * Servlet to delete users from database.
 */
public class DeleteUser extends HttpServlet {
    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = "<html>" +
                " <head>" +
                "  <meta charset='utf-8'>" +
                "  <title>Update User</title>" +
                " </head>" +
                " <body align='center'>" +
                " <form name='edit' method='post'>" +
                "<p>login</p>" +
                "<p><input type='text'  name='login' required/></p>" +
                "<input type='submit'  name='submit' value='Delete'/>"+
                " </form>" +
                " </body>" +
                "</html>";
        resp.getWriter().write(response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String response;
        if (USERSTORE.getByLogin(login) != null) {
            USERSTORE.delete(login);
            response = "deleted<br/>";
        } else {
            response = "this login does not exist<br/>";
        }
        resp.getWriter().write(this.makeResponse(response));
    }

    private String makeResponse(String response) {
        return "<html>" +
                "<head>" +
                "<meta charset='utf-8'>" +
                "<title>Update User</title>" +
                "</head>" +
                "<body align='center'>" +
                response +
                "<form action='show'>" +
                "    <button type='submit'>Back</button>" +
                "</form>" +
                "<a href='/test'>Main menu</a> " +
                " </body>" +
                "</html>";
    }
}

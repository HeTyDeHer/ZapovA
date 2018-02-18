package userstoreupdated;

import common.User;
import common.UserStore;
import common.UserStoreJDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 1. Реализовать приложения для работы с пользователем. [#2513]
 * Servlet to update users data.
 */
public class UpdateUser extends HttpServlet {
    private static final UserStore USERSTORE = UserStoreJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login") == null ? "" : req.getParameter("login");
        String name = req.getParameter("name") == null ? "" : req.getParameter("name");
        String email = req.getParameter("email") == null ? "" : req.getParameter("email");
        StringBuilder response = new StringBuilder();
        response.append("<html>" +
                " <head>" +
                "  <meta charset='utf-8'>" +
                "  <title>Update User</title>" +
                " </head>" +
                " <body align='center'>" +
                " <form action='update' name='update' method=post>" +
                "<p><b>login</b></p>");
        if (!login.isEmpty()) {
            response.append(login);
            response.append("<p><input type='hidden' ");
        } else {
            response.append("<p><input type='text' ");
        }
        response.append("value='");
        response.append(login);
        response.append("' name='login' required/></p>" +
                "<p><b>name</b></p>" +
                "<p><input type='text' value='" + name + "' name='name' required/></p>" +
                "<p><b>email</b></p>" +
                "<p><input type='text' value='" + email + "' name='email' required/></p>" +
                "<input type='submit'  name='submit' value='Edit User'/>" +
                " </form>" +
                " </body>" +
                "</html>");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(response);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User user = USERSTORE.getByLogin(login);
        String response;
        if (user != null) {
            if (!name.isEmpty()) {
                user.setName(name);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            System.out.println(user);
            USERSTORE.update(user);
            response = "edited<br/>";
        } else {
            response = "this login doesn't exist<br/>";
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
                "<br/>" +
                "<a href='/test'>Main menu</a> " +
                " </body>" +
                "</html>";
    }
}

package servlets.filters;

import service.dao.models.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Check access permission for create/update/delete operations.
 */

public class Admin extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user != null && ("admin".equals(user.getRole().getRole()) || "moderator".equals(user.getRole().getRole())) )  {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/");

        }
    }
}

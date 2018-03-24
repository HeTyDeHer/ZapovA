package servlets.filters;

import service.dao.models.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirect admin from main page to /admin.
 */
public class AdminRedirect extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user != null && ("admin".equals(user.getRole().getRole()) || ("moderator".equals(user.getRole().getRole())))) {
            response.sendRedirect(String.format("%s/admin", request.getContextPath()));
        } else {
        chain.doFilter(request, response);
        }
    }
}

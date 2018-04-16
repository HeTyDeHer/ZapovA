package cars.servlets.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Only authorized users can add offers.
 */
public class AddOffer extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(String.format("%s/login", request.getContextPath()));
        } else {
            chain.doFilter(request, response);
        }
    }
}

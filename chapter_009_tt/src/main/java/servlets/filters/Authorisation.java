package servlets.filters;

import service.dao.models.User;
import service.rep.UserRep;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Get authorization from cookies.
 */
public class Authorisation extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserRep userRep = new UserRep();
        if (request.getSession().getAttribute("currentUser") == null) {
            String id = this.getCookieValue(request, "savedSession");
            if (!id.isEmpty()) {
                User user = userRep.getSession(id);
                request.getSession().setAttribute("currentUser", user);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Get Cookie value.
     * @param request HttpServletRequest
     * @param name name of cookie.
     * @return value of cookie, or null if none.
     */
    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        String value = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName()) && !cookie.getValue().isEmpty()) {
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }
}

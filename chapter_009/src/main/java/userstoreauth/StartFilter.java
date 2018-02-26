package userstoreauth;

import userstoreauth.model.UserVer2;
import userstoreauth.service.ManageSessions;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Filter for all pages. If user wasn't logged in, check for cookies.
 */
public class StartFilter implements Filter {

    private static final ManageSessions SESSIONS = new ManageSessions();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute(Auth.CURRENT_USER) == null) {
            String id = this.getCookieValue(request, Auth.SAVE);
            if (!id.isEmpty()) {
                UserVer2 user = SESSIONS.getUserBySessionID(id);
                request.getSession().setAttribute(Auth.CURRENT_USER, user);
            }
        }
        filterChain.doFilter(request, servletResponse);
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

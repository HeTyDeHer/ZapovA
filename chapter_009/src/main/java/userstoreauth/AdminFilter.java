package userstoreauth;

import userstoreauth.model.UserVer2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * /auth/admin & auth/delete directory filter. If access right is lower (or none) redirect to login page.
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        UserVer2 user = (UserVer2) session.getAttribute(Auth.CURRENT_USER);
        if (user != null && Auth.ADMIN.equals(user.getRole())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            request.setAttribute("error", "Insufficient access rights");
            request.getRequestDispatcher(Auth.LOGIN_JSP).forward(request, servletResponse);
        }
    }
}


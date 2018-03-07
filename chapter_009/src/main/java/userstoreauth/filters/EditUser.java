package userstoreauth.filters;

import userstoreauth.Const;
import userstoreauth.model.UserVer2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * /auth/edit Filter. User should be logged in.
 */
public class EditUser implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        UserVer2 user = (UserVer2) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            ((HttpServletResponse)servletResponse).sendRedirect(String.format("%s/auth/login", request.getContextPath()));
        } else {
            filterChain.doFilter(request, servletResponse);
        }
    }
}

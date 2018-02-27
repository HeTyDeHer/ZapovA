package userstoreauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AdminServletTest {

    @BeforeEach
    void setUp() {
        UserStoreTestVer2 us = new UserStoreTestVer2();
        us.deleteAll();
    }

    @Test
    void addUserThatNotExistShouldAddAndRedirectToAdmin() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("role")).thenReturn("user");
        when(request.getContextPath()).thenReturn("");
        adminServlet.doPost(request, response);
        UserStoreTestVer2 us = new UserStoreTestVer2();
        UserVer2 user = us.getByLogin("login");
        assertNotNull(user);
        verify(response).sendRedirect(String.format("%s/auth/admin", ""));
    }

    @Test
    void addUserLoginAlreadyExistsShouldRedirectToErrorPage() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("role")).thenReturn("user");
        when(request.getRequestDispatcher(Auth.ADD_ERR_JSP)).thenReturn(requestDispatcher);
        UserStoreTestVer2 us = new UserStoreTestVer2();
        us.addUser(new UserVer2("login", "password", "name", "email", Timestamp.valueOf(LocalDateTime.now()), "user"));
        adminServlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}
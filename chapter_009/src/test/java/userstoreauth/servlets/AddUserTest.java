package userstoreauth.servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.Const;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreMb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AddUserTest {

    @BeforeEach
    void setUp() {
        UserStoreMb us = new UserStoreMb();
        us.deleteAll();
    }

    @Test
    void addUserThatNotExistShouldAddAndRedirectToAdmin() throws ServletException, IOException {
        AddUser add = new AddUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        this.setParams(request, response);
        add.doPost(request, response);
        UserStoreMb us = new UserStoreMb();
        UserVer2 user = us.getByLogin("login");
        assertNotNull(user);
        verify(response).sendRedirect(String.format("%s/auth/admin", ""));
    }

    @Test
    void addUserLoginAlreadyExistsShouldRedirectToErrorPage() throws ServletException, IOException {
        AddUser add = new AddUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        this.setParams(request, response);
        when(request.getRequestDispatcher(Const.ADD_ERR_JSP)).thenReturn(requestDispatcher);
        UserStoreMb us = new UserStoreMb();
        us.addUser(new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user"));
        add.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    private void setParams(HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userStoreMb")).thenReturn(new UserStoreMb());
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("country")).thenReturn("Россия");
        when(request.getParameter("city")).thenReturn("Москва");
        when(request.getParameter("role")).thenReturn("user");
        when(request.getContextPath()).thenReturn("");
    }
}

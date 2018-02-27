package userstoreauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.model.UserVer2;
import userstoreauth.service.ManageSessions;
import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class LogOutTest {

    @BeforeEach
    void setUp() {
        UserStoreTestVer2 us = new UserStoreTestVer2();
        ManageSessions ms = new ManageSessions();
        us.deleteAll();
        UserVer2 user = new UserVer2("admin", "password", "name", "email", Timestamp.valueOf("2013-04-06 09:01:10"), "admin");
        us.addUser(user);
        ms.rememberSession("adminid", user);
    }

    @Test
    void whenLogOutShouldDeleteCookiesAndInvalidateSession() throws IOException, SQLException, ServletException {
        LogOut logOut = new LogOut();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(session.getAttribute(Auth.CURRENT_USER)).thenReturn(
                new UserVer2("admin", "password", "name", "email", Timestamp.valueOf("2013-04-06 09:01:10"), "admin"));
        logOut.doPost(request, response);
        verify(session).invalidate();
        verify(response).sendRedirect(String.format("%s/auth", request.getContextPath()));
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UserStoreTest", "postgres", "password")) {
            Statement statement = connection.createStatement();
            try (ResultSet rs = statement.executeQuery("SELECT * FROM cookies WHERE user_login = 'adminid'")) {
                assertFalse(rs.next());
            }
        }

    }
}
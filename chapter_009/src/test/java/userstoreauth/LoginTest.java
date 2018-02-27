package userstoreauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreTestVer2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LoginTest {

    @BeforeEach
    void setUp() {
        UserStoreTestVer2 us = new UserStoreTestVer2();
        us.deleteAll();
        us.addUser(new UserVer2("admin", "password", "name", "email", Timestamp.valueOf("2013-04-06 09:01:10"), "admin"));
        us.addUser(new UserVer2("user", "password", "name", "email", Timestamp.valueOf("2013-04-06 09:01:10"), "user"));
    }

    @Test
    void adminLoginCorrectRememberTrueShouldAddSessionAttrSaveCookieAndRedirToAdminPage() throws ServletException, IOException, SQLException {
        Login login = new Login();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("remember")).thenReturn("true");             // Не придумал, как тестировать responce.addCookies.
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        UserVer2 user = new UserVer2("admin", "password", "name", "email", Timestamp.valueOf("2013-04-06 09:01:10"), "admin");
        login.doPost(request, response);
        verify(session).setAttribute(Auth.CURRENT_USER, user);
        verify(response).sendRedirect(String.format("%s/auth/admin", ""));
        this.checkCookieTable("admin");
    }

    @Test
    void userLoginCorrectRememberTrueShouldAddSessionAttrSaveCookieAndRedirToMainPage() throws ServletException, IOException, SQLException {
        Login login = new Login();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("remember")).thenReturn("true");             // Не придумал, как тестировать responce.addCookies.
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        UserVer2 user = new UserVer2("user", "password", "name", "email", Timestamp.valueOf("2013-04-06 09:01:10"), "user");
        login.doPost(request, response);
        verify(session).setAttribute(Auth.CURRENT_USER, user);
        verify(response).sendRedirect(String.format("%s/auth", ""));
        this.checkCookieTable("user");
    }

    @Test
    void loginNotExistShouldAddErrorAndRefreshPage() throws ServletException, IOException {
        Login login = new Login();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("login")).thenReturn("wronglogin");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("remember")).thenReturn("false");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestDispatcher(Auth.LOGIN_JSP)).thenReturn(requestDispatcher);
        login.doPost(request, response);
        verify(request).setAttribute("error", "wrong login!");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void passwordIncorrectShouldAddErrorAndRefreshPage() throws ServletException, IOException {
        Login login = new Login();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("incorrectpassword");
        when(request.getParameter("remember")).thenReturn("false");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestDispatcher(Auth.LOGIN_JSP)).thenReturn(requestDispatcher);
        login.doPost(request, response);
        verify(request).setAttribute("error", "wrong login or password!");
        verify(requestDispatcher).forward(request, response);
    }

    private void checkCookieTable(String username) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UserStoreTest", "postgres", "password")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cookies WHERE user_login = ?");
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertFalse(rs.next());
            }
        }
    }
}
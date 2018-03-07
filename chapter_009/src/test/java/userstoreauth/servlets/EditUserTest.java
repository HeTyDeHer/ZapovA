package userstoreauth.servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreMb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditUserTest {

    @BeforeEach
    void setUp() {
        UserStoreMb us = new UserStoreMb();
        us.deleteAll();
    }

    @Test
    void editUser() throws ServletException, IOException {
        EditUser editUser = new EditUser();
        UserStoreMb us = new UserStoreMb();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password0");
        when(request.getParameter("name")).thenReturn("name0");
        when(request.getParameter("email")).thenReturn("email0");
        when(request.getParameter("role")).thenReturn("admin");
        when(request.getParameter("country")).thenReturn("Россия");
        when(request.getParameter("city")).thenReturn("Москва");
        UserVer2 user = new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user");
        us.addUser(user);
        assertEquals(user, us.getByLogin("login"));
        editUser.doPost(request, response);
        user.setPassword("password0");
        user.setName("name0");
        user.setEmail("email0");
        user.setRole("admin");
        assertEquals(user, us.getByLogin("login"));
    }
}

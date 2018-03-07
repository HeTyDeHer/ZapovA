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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteUserTest {

    @BeforeEach
    void setUp() {
        UserStoreMb us = new UserStoreMb();
        us.deleteAll();
    }

    @Test
    void deleteUser() throws ServletException, IOException {
        UserStoreMb us = new UserStoreMb();
        DeleteUser delete = new DeleteUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("login");
        us.addUser(new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user"));
        UserVer2 user = us.getByLogin("login");
        assertNotNull(user);
        delete.doPost(request, response);
        us.delete("login");
        user = us.getByLogin("login");
        assertNull(user);
    }
}
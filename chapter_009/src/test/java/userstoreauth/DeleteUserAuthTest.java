package userstoreauth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.model.UserVer2;
import userstoreauth.service.UserStoreTestVer2;

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

class DeleteUserAuthTest {

    @BeforeEach
    void setUp() {
        UserStoreTestVer2 us = new UserStoreTestVer2();
        us.deleteAll();
    }

    @Test
    void deleteUser() throws ServletException, IOException {
        UserStoreTestVer2 us = new UserStoreTestVer2();
        DeleteUserAuth delete = new DeleteUserAuth();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("login");
        us.addUser(new UserVer2("login", "password", "name", "email", Timestamp.valueOf(LocalDateTime.now()), "user"));
        UserVer2 user = us.getByLogin("login");
        assertNotNull(user);
        delete.doPost(request, response);
        us.delete("login");
        user = us.getByLogin("login");
        assertNull(user);
    }
}
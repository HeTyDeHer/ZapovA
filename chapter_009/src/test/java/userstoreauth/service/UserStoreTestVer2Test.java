package userstoreauth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userstoreauth.model.UserVer2;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserStoreTestVer2Test {

    @BeforeEach
    void setUp() {
        UserStoreMb us = new UserStoreMb();
        us.deleteAll();
    }

    @Test
    void addUserAndGetByLogin() {
        UserStoreMb us = new UserStoreMb();
        UserVer2 user = new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user");
        us.addUser(user);
        assertEquals(user, us.getByLogin(user.getLogin()));
    }

    @Test
    void getAll() {
        UserStoreMb us = new UserStoreMb();
        UserVer2 user1 = new UserVer2("login1", "password1", "name1", "email1", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user");
        UserVer2 user2 = new UserVer2("login2", "password2", "name2", "email2", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "admin");
        us.addUser(user1);
        us.addUser(user2);
        assertIterableEquals(Arrays.asList(user1, user2), us.getAll());
    }

    @Test
    void update() {
        UserStoreMb us = new UserStoreMb();
        UserVer2 user = new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user");
        us.addUser(user);
        user.setPassword("password0");
        user.setName("name0");
        user.setEmail("email0");
        user.setRole("admin");
        us.update(user);
        assertEquals(user, us.getByLogin("login"));
    }

    @Test
    void delete() {
        UserStoreMb us = new UserStoreMb();
        us.addUser(new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user"));
        UserVer2 user = us.getByLogin("login");
        assertNotNull(user);
        us.delete("login");
        user = us.getByLogin("login");
        assertNull(user);
    }

    @Test
    void deleteAllShouldOnDeleteCascade() throws SQLException {
        UserStoreMb us = new UserStoreMb();
        us.deleteAll();
        UserVer2 user = new UserVer2("login", "password", "name", "email", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "user");
        UserVer2 user1 = new UserVer2("login1", "password1", "name1", "email1", "Россия", "Москва", Timestamp.valueOf(LocalDateTime.now()), "admin");
        us.addUser(user);
        us.addUser(user1);
        assertEquals(2, us.getAll().size());
        ManageSessions ms = new ManageSessions();
        ms.rememberSession("1", user);
        ms.rememberSession("2", user1);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UserStoreTest", "postgres", "password")) {
            try (Statement statement1 = connection.createStatement();
                 Statement statement2 = connection.createStatement()){
                try (ResultSet rs1 = statement1.executeQuery("SELECT * FROM auth");
                     ResultSet rs2 = statement2.executeQuery("SELECT * FROM cookies")) {
                    assertTrue(rs1.next());
                    assertTrue(rs1.next());
                    assertFalse(rs1.next());
                    assertTrue(rs2.next());
                    assertTrue(rs2.next());
                    assertFalse(rs2.next());
                }
            }
        }
        us.deleteAll();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UserStoreTest", "postgres", "password")) {
            try (Statement statement1 = connection.createStatement();
                 Statement statement2 = connection.createStatement()){
                try (ResultSet rs1 = statement1.executeQuery("SELECT * FROM auth");
                     ResultSet rs2 = statement2.executeQuery("SELECT * FROM cookies")) {
                    assertFalse(rs1.next());
                    assertFalse(rs2.next());
                }
            }
        }
        assertEquals(0, us.getAll().size());
    }
}

package service.rep;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DBConnectionPool;
import service.dao.models.Role;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepTest {
    private RoleRep roleDAO = new RoleRep();

    @BeforeEach
    private void setUp() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
    }

    @AfterAll
    private static void close() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
        db.close();
    }

    @Test
    void createAndRead() {
        Role role = new Role();
        role.setRole("User");
        role.setDescription("User descripion");
        Role role2 = new Role();
        role2.setRole("Admin");
        role2.setDescription("Admin descripion");
        int id = roleDAO.create(role);
        int id2 = roleDAO.create(role2);
        assertNotEquals(-1, id);
        assertNotEquals(-1, id2);
        role.setId(id);
        role2.setId(id2);
        Role testRole = roleDAO.read("User");
        assertEquals(role, testRole);
        List<Role> expected = Arrays.asList(role, role2);
        assertArrayEquals(expected.toArray(), roleDAO.readAll().toArray());
    }

    @Test
    void update() {
        Role role = new Role();
        role.setRole("User");
        role.setDescription("User descripion");
        int id = roleDAO.create(role);
        assertNotEquals(-1, id);
        role.setId(id);
        role.setRole("Тест");
        role.setDescription("Тест");
        assertTrue(roleDAO.update(role));
        assertEquals(role, roleDAO.read("Тест"));
    }

    @Test
    void delete() {
        Role role = new Role();
        role.setRole("User");
        role.setDescription("User descripion");
        int id = roleDAO.create(role);
        assertNotEquals(-1, id);
        assertNotNull(roleDAO.read("User"));
        roleDAO.delete(id);
        assertNull(roleDAO.read("User"));
    }
}
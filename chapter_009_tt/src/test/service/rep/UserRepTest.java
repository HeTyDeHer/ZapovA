package service.rep;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DBConnectionPool;
import service.dao.AddressDAO;
import service.dao.AddressDAOImpl;
import service.dao.MusicDaoImpl;
import service.dao.RoleDaoImpl;
import service.dao.models.Address;
import service.dao.models.Music;
import service.dao.models.Role;
import service.dao.models.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepTest {
    private UserRep userRep = new UserRep();

    @BeforeEach
    void setUp() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
    }


    @AfterAll
    static void deleteAfter() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
        db.close();
    }

    @Test
    void create_ReadById_ReadByLogin_ReadByRole_ReadAll() {
        Address address = this.getTestAddress("Тест");
        Role role = this.getTestRole("user");
        List<Music> music = Arrays.asList(this.getTestMusic("Pop"), this.getTestMusic("Rock"));
        User user = this.getTestUser("login1", address, role, music);
        Address address2 = this.getTestAddress("Тест2");
        Role role2 = this.getTestRole("admin");
        List<Music> music2 = Arrays.asList(this.getTestMusic("Jazz"), this.getTestMusic("Trance"));
        User user2 = this.getTestUser("login2", address2, role2, music2);
        int id = user.getId();
        int id2 = user2.getId();
        assertNotEquals(-1, id);
        assertNotEquals(-1, id2);
        user.setId(id);
        user2.setId(id2);
        User testUser = userRep.read(id);
        assertEquals(user, testUser);
        testUser = userRep.readByLogin("login1");
        assertEquals(user, testUser);
        List<User> expected = Arrays.asList(user, user2);
        assertArrayEquals(expected.toArray(), userRep.readAll().toArray());
        expected = Collections.singletonList(user);
        assertArrayEquals(expected.toArray(), userRep.readAllUsersByRole(role).toArray());
        assertEquals(user, userRep.readByAddress(address));
    }

    @Test
    void update() {
        List<Music> music = Arrays.asList(this.getTestMusic("Pop"), this.getTestMusic("Rock"));
        User user = this.getTestUser("loginTest", this.getTestAddress("Тест"), this.getTestRole("user"), music);
        int id = user.getId();
        assertNotEquals(-1, id);
        user.setLogin("Editedlogin");
        user.setName("EditedName");
        user.getAddress().setStreet("editedStreet");
        assertTrue(userRep.update(user));
        assertEquals(user, userRep.read(id));
    }

    @Test
    void delete() {
        List<Music> music = Arrays.asList(this.getTestMusic("Pop"), this.getTestMusic("Rock"));
        User user = this.getTestUser("loginTest", this.getTestAddress("Тест"), this.getTestRole("user"), music);
        int id = user.getId();
        assertNotEquals(-1, id);
        assertNotNull(userRep.read(id));
        userRep.delete(id);
        assertNull(userRep.read(id));
    }

    @Test
    void readUserMusic() {
        List<Music> music = Arrays.asList(this.getTestMusic("Pop"), this.getTestMusic("Rock"));
        User user = this.getTestUser("loginTest", this.getTestAddress("Тест"), this.getTestRole("user"), music);
        int id = user.getId();
        assertNotEquals(-1, id);
        assertArrayEquals(music.toArray(), userRep.readAllMusicOfUser(user).toArray());
    }

    @Test
    void readAllUsersOfMusic() {
        Music rock = this.getTestMusic("Rock");
        Music pop = this.getTestMusic("Pop");
        Music trance = this.getTestMusic("Trance");
        List<Music> music = Arrays.asList(pop, rock);
        List<Music> music2 = Arrays.asList(trance, rock);
        User user = this.getTestUser("loginTest", this.getTestAddress("Тест"), this.getTestRole("user"), music);
        User user2 = this.getTestUser("loginTest2", this.getTestAddress("Тест"), this.getTestRole("user"), music2);
        int id = user.getId();
        int id2 = user2.getId();
        assertNotEquals(-1, id);
        assertNotEquals(-1, id2);
        List<Music> rockList = Collections.singletonList(rock);
        List<Music> popList = Collections.singletonList(pop);
        List<User> ofRock = Arrays.asList(user, user2);
        List<User> ofPop = Collections.singletonList(user);
        assertArrayEquals(ofRock.toArray(), userRep.readAllUsersOfMusic(rockList).toArray());
        assertArrayEquals(ofPop.toArray(), userRep.readAllUsersOfMusic(popList).toArray());
    }

    private Role getTestRole(String role) {
        RoleDaoImpl roleDAO = new RoleDaoImpl();
        Role result = roleDAO.read(role);
        if (result == null) {
            result = new Role();
            result.setRole(role);
            result.setDescription("ТестDesc");
            result.setId(roleDAO.create(result));
        }
        return result;
    }

    private Music getTestMusic(String genre) {
        Music result = new Music();
        result.setGenre(genre);
        MusicDaoImpl musicDAO = new MusicDaoImpl();
        result.setId(musicDAO.create(result));
        return result;
    }

    private Address getTestAddress(String street) {
        Address result = new Address();
        result.setCountry("Россия");
        result.setCity("Москва");
        result.setStreet(street);
        result.setHome(1);
        result.setApart(1);
        AddressDAO addressDAO = new AddressDAOImpl();
        result.setId(addressDAO.create(result));
        return result;
    }

    private User getTestUser(String login, Address address, Role role, List<Music> music) {
        User result = new User();
        result.setLogin(login);
        result.setPassword("password");
        result.setName("name");
        result.setAddress(address);
        result.setRole(role);
        result.setMusic(music);
        result.setId(userRep.create(result));
        return result;
    }

}
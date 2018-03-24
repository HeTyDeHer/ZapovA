package service.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.models.Address;
import service.dao.models.Music;
import service.dao.models.Role;
import service.dao.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private final RoleDaoImpl roleDAO = new RoleDaoImpl();
    private final AddressDAOImpl addressDAO = new AddressDAOImpl();
    private final DBConnectionPool db = DBConnectionPool.getDb();

    /**
     * Create new User in db.
     * @param model new User
     * @return if successfully - user_id in database.
     *         if not return -1
     */
    @Override
    public int create(User model) {
        int key = -1;
        if (model != null && model.getAddress() != null && model.getRole() != null ) {
            try (Connection connection = db.getConnection();
                 PreparedStatement addUser = connection.prepareStatement(USER.ADD_USER.toString(), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement userMusic = connection.prepareStatement(USER.ADD_USER_MUSIC.toString())) {
                addUser.setString(1, model.getLogin());
                addUser.setString(2, model.getPassword());
                addUser.setString(3, model.getName());
                addUser.setInt(4, model.getAddress().getId());
                addUser.setString(5, model.getRole().getRole());
                addUser.execute();
                ResultSet rs = addUser.getGeneratedKeys();
                if (rs.next()) {
                    key = rs.getInt("id");
                    connection.setAutoCommit(false);
                    for (Music music : model.getMusic()) {
                        userMusic.setInt(1, key);
                        userMusic.setInt(2, music.getId());
                        userMusic.addBatch();
                    }
                    userMusic.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return key;

    }

    /**
     * Get user from database by login.
     * @param key login.
     * @return User, if exists. Null if not.
     */
    @Override
    public User readByLogin(String key) {
        User user = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER.GET_BY_LOGIN.toString())) {
            ps.setString(1, key);
            ps.execute();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    this.setUserData(rs, user);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    public User read(Integer key) {
        User user = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER.GET_BY_ID.toString())) {
            ps.setInt(1, key);
            ps.execute();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    this.setUserData(rs, user);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    private void setUserData(ResultSet rs, User user) throws SQLException {
        Address address = addressDAO.read(rs.getInt("address_id"));
        Role role = roleDAO.read(rs.getString("role"));
        if (role != null && address != null) {
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setRole(role);
            user.setAddress(address);
            user.setMusic(this.readAllMusicOfUser(user));
        }
    }

    private List<Music> readAllMusicOfUser(User user) {
        int user_id = user.getId();
        List<Music> result = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER.GET_USERS_MUSIC.toString())) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                MusicDaoImpl musicDAO = new MusicDaoImpl();
                while (rs.next()) {
                    int music_id = rs.getInt("music_id");
                    Music music = musicDAO.read(music_id);
                    result.add(music);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return result;
        }
        return result;
    }

    @Override
    public boolean update(User model) {
        boolean result = false;
        if (model != null && model.getAddress() != null && model.getRole() != null) {
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(USER.UPDATE_USER.toString());
                 PreparedStatement psMus = connection.prepareStatement(USER.ADD_USER_MUSIC.toString());
                 PreparedStatement delMus = connection.prepareStatement("DELETE FROM users_music WHERE user_id = ?")) {
                connection.setAutoCommit(false);
                delMus.setInt(1, model.getId());
                delMus.execute();
                ps.setString(1, model.getLogin());
                ps.setString(2, model.getPassword());
                ps.setString(3, model.getName());
                ps.setInt(4, model.getAddress().getId());
                ps.setString(5, model.getRole().getRole());
                ps.setInt(6, model.getId());
                ps.execute();
                for (Music music : model.getMusic()) {
                    psMus.setInt(1, model.getId());
                    psMus.setInt(2, music.getId());
                    psMus.addBatch();
                }
                psMus.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
                result = true;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public void delete(int id) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER.DELETE_USER.toString());
             PreparedStatement addr_id = connection.prepareStatement(USER.GET_USERS_ADDRESS_ID.toString())) {
            addr_id.setInt(1, id);
            addr_id.execute();
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet rs = addr_id.getGeneratedKeys()) {
                if (rs.next()) {
                    int addrId = rs.getInt(1);
                    AddressDAO addressDAO = new AddressDAOImpl();
                    addressDAO.delete(addrId);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private enum USER {
        ADD_USER("INSERT INTO users (login, password, name, address_id, role) VALUES (?, ?, ?, ?, ?)"),
        ADD_USER_MUSIC("INSERT INTO users_music (user_id, music_id) VALUES (?, ?)"),
        GET_BY_LOGIN("SELECT * FROM users WHERE login = ? "),
        GET_BY_ID("SELECT * FROM users WHERE id = ? "),
        UPDATE_USER("UPDATE users SET login = ?, password = ?, name = ?, address_id = ?, role = ? WHERE id = ? "),
        DELETE_USER("DELETE FROM users WHERE id = ?"),
        GET_USERS_MUSIC("SELECT music_id FROM users_music WHERE user_id = ? "),
        GET_USERS_ADDRESS_ID("SELECT address_id FROM users WHERE id = ?");

        private final String text;

        USER(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}

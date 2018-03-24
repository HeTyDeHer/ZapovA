package service.rep;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.AddressDAOImpl;
import service.dao.RoleDaoImpl;
import service.dao.UserDaoImpl;
import service.dao.models.Address;
import service.dao.models.Music;
import service.dao.models.Role;
import service.dao.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Для User реализовать:
* -      операцию получения всех связанных с ним сущностей;
* -      операцию добавления нового User и всех связанных с ним сущностей;
* -      операцию поиска User по заданному параметру (Address, Role, MusicType).
 */
public class UserRep extends UserDaoImpl {

    private final UserDaoImpl userDAO = new UserDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(UserRep.class);
    private final DBConnectionPool db = DBConnectionPool.getDb();

    public List<User> readAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(USERREP.GET_ALL.toString())) {
            while (rs.next()) {
                User user = new User();
                this.setUserData(user, rs);
                result.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private void setUserData(User user, ResultSet rs) throws SQLException {
        AddressDAOImpl addressDAO = new AddressDAOImpl();
        RoleDaoImpl roleDAO = new RoleDaoImpl();
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


    public List<Music> readAllMusicOfUser(@NotNull User user) {
        return super.readByLogin(user.getLogin()).getMusic();
    }


    public List<User> readAllUsersOfMusic(List<Music> music) {
        List<User> result = new ArrayList<>();
        if (music != null && !music.isEmpty()) {
            StringBuilder sb = new StringBuilder(USERREP.GET_MUSIC.toString());
            for (int i = 1; i < music.size(); i++) {
                sb.append(" OR music_id = ?");
            }
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sb.toString())) {
                for (int i = 0; i < music.size(); i++) {
                    ps.setInt(i + 1, music.get(i).getId());
                }
                result = getUsers(ps);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }


    public List<User> readAllUsersByRole(Role role) {
        List<User> result = new ArrayList<>();
        if (role != null) {
            String name = role.getRole();
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(USERREP.GET_ROLE.toString())) {
                ps.setString(1, name);
                result = getUsers(ps);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    public User readByAddress(@NotNull Address address) {
        User result = null;
        if (address != null) {
            result = new User();
            int address_id = address.getId();
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(USERREP.GET_USER_BY_ADDR.toString())) {
                ps.setInt(1, address_id);
                result = this.getUsers(ps).get(0);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }


    private List<User> getUsers(PreparedStatement ps) throws SQLException {
        List<User> result;
        try (ResultSet rs = ps.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                int user_id = rs.getInt(1);
                User user = userDAO.read(user_id);
                result.add(user);
            }
        }
        return result;
    }

    public void deleteEntryUserMusic(@NotNull User user, @NotNull Music music) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USERREP.DELETE_MUSIC_OF_USER.toString())) {
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void saveSession(User user, String uuid) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USERREP.SAVE_SESSION.toString())) {
            ps.setString(1, uuid);
            ps.setString(2, user.getLogin());
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public User getSession(String uuid) {
        User user = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USERREP.GET_SESSION.toString())) {
            ps.setString(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    this.setUserData(user, rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    public void deleteSession(User user) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(USERREP.DELETE_SESSION.toString())) {
            ps.setString(1, user.getLogin());
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private enum USERREP {
        DELETE_MUSIC_OF_USER("DELETE FROM users_music WHERE user_id = ? AND music_id = ?"),
        DELETE_SESSION("UPDATE users SET session_id = null WHERE login = ?"),
        GET_ALL("SELECT * FROM users"),
        GET_USER("SELECT music_id FROM users_music WHERE user_id = ? "),
        GET_USER_BY_ADDR("SELECT * FROM users WHERE address_id = ?"),
        GET_MUSIC("SELECT DISTINCT user_id FROM users_music WHERE music_id = ?"),
        GET_ROLE("SELECT id FROM users WHERE role = ?"),
        GET_SESSION("SELECT * FROM users WHERE session_id = ? "),
        SAVE_SESSION("UPDATE users SET session_id = ? WHERE login = ?");


        private final String text;

        USERREP(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}


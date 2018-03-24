package service.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.models.Role;

import java.sql.*;

public class RoleDaoImpl implements RoleDAO {

    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    private final DBConnectionPool db = DBConnectionPool.getDb();

    @Override
    public int create(Role model) {
        int key = 0;
        if (model != null) {
            key = -1;
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(ROLE.ADD_ROLE.toString(), Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, model.getRole());
                ps.setString(2, model.getDescription());
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    key = rs.getInt("id");
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return key;

    }

    @Override
    public Role read(String key) {
        Role music = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(ROLE.READ_ROLE.toString())) {
            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    music = new Role();
                    music.setId(rs.getInt("id"));
                    music.setRole(rs.getString("role"));
                    music.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return music;
    }

    @Override

    public boolean update(Role model) {
        boolean result = false;
        if (model != null) {
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(ROLE.UPDATE_ROLE.toString())) {
                ps.setString(1, model.getRole());
                ps.setString(2, model.getDescription());
                ps.setInt(3, model.getId());
                ps.execute();
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
             PreparedStatement ps = connection.prepareStatement(ROLE.DELETE_ROLE.toString())) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private enum ROLE {
        ADD_ROLE("INSERT INTO role (role, description) VALUES (?, ?)"),
        READ_ROLE("SELECT * FROM role WHERE role = ?"),
        UPDATE_ROLE("UPDATE role SET role = ?, description = ? WHERE id = ?"),
        DELETE_ROLE("DELETE FROM role WHERE id = ?");

        private final String text;

        ROLE(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}

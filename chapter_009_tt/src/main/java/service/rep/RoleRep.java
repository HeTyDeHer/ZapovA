package service.rep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.RoleDaoImpl;
import service.dao.models.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleRep extends RoleDaoImpl {
    private DBConnectionPool db = DBConnectionPool.getDb();
    private static final Logger logger = LoggerFactory.getLogger(MusicRep.class);

    public List<Role> readAll() {
        List<Role> result = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ROLE.READ_ROLE.toString())) {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRole(rs.getString("role"));
                role.setDescription(rs.getString("description"));
                result.add(role);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private enum ROLE {
        READ_ROLE("SELECT * FROM role");
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

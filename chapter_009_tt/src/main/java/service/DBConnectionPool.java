package service;


import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

/**
 * Музыкальная прощадка [#3236].
 * Database connect && init tables.
 */
public final class DBConnectionPool {

    private static final  Logger logger = LoggerFactory.getLogger(DBConnectionPool.class);
    private static final DBConnectionPool db = new DBConnectionPool();
    private final BasicDataSource dataSource = new BasicDataSource();

    private DBConnectionPool() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(DBConnectionPool.class.getClassLoader().getResourceAsStream("testtask.properties")));
            Class.forName(prop.getProperty("driver"));
            this.dataSource.setUrl(prop.getProperty("database.url"));
            this.dataSource.setUsername(prop.getProperty("username"));
            this.dataSource.setPassword(prop.getProperty("password"));
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        this.initTables();
        this.fillCitiesTable();
        this.fillRoleTable();
        this.fillMusicTable();
        this.addAdmin();
    }

    public static DBConnectionPool getDb() {
        return db;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Create all tables, if not exists.
     */
    private void initTables() {
        try (Connection connection = this.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(INIT.CREATE_CITIES.toString());
            statement.execute(INIT.CREATE_ROLES.toString());
            statement.execute(INIT.CREATE_MUSIC.toString());
            statement.execute(INIT.CREATE_ADDRESS.toString());
            statement.execute(INIT.CREATE_USERS.toString());
            statement.execute(INIT.CREATE_USERS_TO_MUSIC.toString());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Add admin, if not exists.
     */
    private void addAdmin() {
        try (Connection connection = this.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(INIT.GET_ADMIN.toString())) {
            if (!rs.next()) {
                int address_id = 0;
                st.executeUpdate(INIT.ADD_ADMIN_ADDRESS.toString(), Statement.RETURN_GENERATED_KEYS);
                try (ResultSet rs1 = st.getGeneratedKeys()) {
                    if (rs1.next()) {
                        address_id = rs1.getInt(1);
                    }
                }
                try (PreparedStatement ps = connection.prepareStatement(INIT.ADD_ADMIN.toString())) {
                    ps.setInt(1, address_id);
                    ps.execute();
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Initial fill music table. Переделать!!!
     */
    private void fillMusicTable() {
        try (Connection connection = this.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(INIT.FILL_MUSIC.toString())) {
            ResultSet rs = statement.executeQuery(INIT.GET_ALL_MUSIC.toString());
            if(!rs.next()) {
                connection.setAutoCommit(false);
                ps.setString(1, "Rap");
                ps.addBatch();
                ps.setString(1, "Rock");
                ps.addBatch();
                ps.setString(1, "Pop");
                ps.addBatch();
                ps.setString(1, "Trance");
                ps.addBatch();
                ps.setString(1, "Jazz");
                ps.addBatch();
                ps.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Initial fill role table.
     */
    private void fillRoleTable() {
        try (Connection connection = this.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(INIT.FILL_ROLES.toString())) {
            ResultSet rs = statement.executeQuery(INIT.GET_ALL_ROLES.toString());
            if(!rs.next()) {
                connection.setAutoCommit(false);
                ps.setString(1, "user");
                ps.setString(2, "No permissions");
                ps.addBatch();
                ps.setString(1, "moderator");
                ps.setString(2, "Some permissions");
                ps.addBatch();
                ps.setString(1, "admin");
                ps.setString(2, "All permissions");
                ps.addBatch();
                ps.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Initial fill countries/cities from planetolog.ru.
     */
    private void fillCitiesTable() {
        try (Connection connection = this.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(INIT.GET_CITY.toString())) {
            if (!rs.next()) {
                try (PreparedStatement ps = connection.prepareStatement(INIT.FILL_CITIES.toString())) {
                    connection.setAutoCommit(false);
                    ParseSiteForCities parse = new ParseSiteForCities();
                    for (City city : parse.parsePlanetologDotRu()) {
                        ps.setString(1, city.getCountry());
                        ps.setString(2, city.getCity());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                }
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void close() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Delete all data from tables.
     */
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(INIT.DELETE_ALL.toString());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        DBConnectionPool.getDb().deleteAll();
    }

    private enum INIT {
        ADD_ADMIN_ADDRESS("INSERT INTO address (city_id, street, home, apart) VALUES (1, 'N0', 0, 0)"),
        ADD_ADMIN("INSERT INTO users (login, password, name, address_id, role) VALUES ('admin', 'admin', 'admin', ?, 'admin')"),
        CREATE_ADDRESS("CREATE TABLE IF NOT EXISTS address (id SERIAL PRIMARY KEY, " +
                "city_id INTEGER REFERENCES cities(id) ON DELETE NO ACTION," +
                "street VARCHAR NOT NULL, " +
                "home INTEGER NOT NULL, " +
                "apart INTEGER NOT NULL" +
                ")"),
        CREATE_CITIES("CREATE TABLE IF NOT EXISTS cities (" +
                "id SERIAL PRIMARY KEY, " +
                "country VARCHAR NOT NULL," +
                "city VARCHAR NOT NULL" +
                ")"),
        CREATE_MUSIC("CREATE TABLE IF NOT EXISTS music (" +
                "id SERIAL PRIMARY KEY, " +
                "genre VARCHAR UNIQUE NOT NULL" +
                ")"),
        CREATE_ROLES("CREATE TABLE IF NOT EXISTS role (id SERIAL UNIQUE NOT NULL," +
                " role VARCHAR PRIMARY KEY," +
                " description VARCHAR)"),
        CREATE_USERS("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, " +
                "login VARCHAR NOT NULL UNIQUE, " +
                "password VARCHAR NOT NULL, " +
                "name VARCHAR NOT NULL, " +
                "address_id INTEGER REFERENCES address(id) ON DELETE CASCADE, " +
                "role VARCHAR REFERENCES role(role) ON DELETE NO ACTION, " +
                "session_id VARCHAR" +
                ")"),
        CREATE_USERS_TO_MUSIC("CREATE TABLE IF NOT EXISTS users_music (id SERIAL PRIMARY KEY, " +
                "user_id INTEGER REFERENCES users(id) ON DELETE CASCADE, " +
                "music_id INTEGER REFERENCES music(id) ON DELETE CASCADE" +
                ")"),
        DELETE_ALL("DELETE FROM users_music; DELETE FROM users; DELETE FROM role; DELETE FROM music; DELETE FROM address"),
        FILL_CITIES("INSERT INTO cities (country, city) VALUES (?, ?)"),
        FILL_MUSIC("INSERT INTO music (genre) VALUES (?)"),
        FILL_ROLES("INSERT INTO role (role, description) VALUES (?, ?)"),
        GET_ALL_MUSIC("SELECT * FROM music"),
        GET_ALL_ROLES("SELECT * FROM role"),
        GET_ADMIN("SELECT * FROM users WHERE login = 'admin'"),
        GET_CITY("SELECT * FROM cities LIMIT 1");

        private final String text;

        INIT(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}

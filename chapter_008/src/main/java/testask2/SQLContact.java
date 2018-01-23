package testask2;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

/**
 * Testask JDBC.
 * Class for work with database.
 */
public class SQLContact {
    /** Connection to database. */
    private Connection connection;
    /** Database url. */
    private String bdUrl;
    private String username;
    private String password;
    private final Logger logger = Logger.getLogger(SQLContact.class.getName());

    /**
     * Constructor.
     * @param properties properties filename.
     */
    public SQLContact(String properties) {
        this.getProperties(properties);
        this.init();
    }

    /**
     * Init. Creating table, if not exists.
     */
    private void init() {
        try (Connection connection = DriverManager.getConnection(this.bdUrl, this.username, this.password);
             Statement statement = connection.createStatement()) {
            statement.execute(SQLQuery.CREATE_TABLE_VACANCIES);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Getting date and time of last database update.
     * @return date and time, when last element was added.
     */
    public LocalDateTime getLastUpdate() {
        LocalDateTime result = LocalDateTime.now().minusYears(1);
        try (Connection connection = DriverManager.getConnection(this.bdUrl, this.username, this.password);
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SQLQuery.SELECT_LAST_UPDATE)) {
                if (rs.next()) {
                    result = rs.getTimestamp(1).toLocalDateTime();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Adding result of parsing.
     * @param vacancies List of vacancies.
     */
    public void addVacancies(List<Vacancy> vacancies) {
        if (!vacancies.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(this.bdUrl, this.username, this.password);
                 PreparedStatement ps = connection.prepareStatement(SQLQuery.ADD_VACANCY)) {
                connection.setAutoCommit(false);
                for (Vacancy vacancy : vacancies) {
                    ps.setString(1, vacancy.getHeader());
                    ps.setString(2, vacancy.getUrl());
                    ps.setString(3, vacancy.getDescription());
                    ps.setTimestamp(4, Timestamp.valueOf(vacancy.getAdded()));
                    ps.setTimestamp(5, Timestamp.valueOf(vacancy.getProcessed()));
                    ps.addBatch();
                }
                ps.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    logger.error(e1.getMessage(), e1);
                }
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Getting properties from file.
     * @param properties filename.
     */
    private void getProperties(String properties) {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(SQLContact.class.getClassLoader().getResourceAsStream(properties)));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        this.bdUrl = prop.getProperty("database.url");
        this.username = prop.getProperty("username");
        this.password = prop.getProperty("password");
    }

}

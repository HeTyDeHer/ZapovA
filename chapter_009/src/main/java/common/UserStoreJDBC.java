package common;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 1. Crud servlet [#2512].
 * Класс для работы с базой данных.
 * Синглтон.
 * Используется Apache DBCP.
 */

public class UserStoreJDBC implements UserStore {

    private BasicDataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(UserStoreJDBC.class);
    private static final UserStoreJDBC INSTANCE = new UserStoreJDBC();

    private UserStoreJDBC() {
        this.init();
    }

    /**
     * Добавление пользователя.
     * @param user пользователь.
     */
    public synchronized void addUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement add = connection.prepareStatement(SQLQuery.INSERT)) {
            add.setString(1, user.getLogin());
            add.setString(2, user.getName());
            add.setString(3, user.getEmail());
            add.setTimestamp(4, user.getCreated());
            add.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Получение всех пользователей.
     * @return List со всеми пользователями.
     */
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement getAll = connection.createStatement();
             ResultSet users = getAll.executeQuery(SQLQuery.GET_ALL)) {
            while (users.next()) {
                allUsers.add(new User(users.getString(1), users.getString(2),
                        users.getString(3), users.getTimestamp(4)));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return allUsers;
    }

    /**
     * Получение пользователя по логину.
     * @param login логин.
     * @return пользователь.
     */
    public User getByLogin(String login) {
        User result = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getUser = connection.prepareStatement(SQLQuery.GET)) {
            getUser.setString(1, login);
            try (ResultSet user = getUser.executeQuery()) {
                if (user.next()) {
                    result = new User(user.getString(1), user.getString(2),
                            user.getString(3), user.getTimestamp(4));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Обновление данных пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement update = connection.prepareStatement(SQLQuery.UPDATE)) {
            update.setString(1, user.getName());
            update.setString(2, user.getEmail());
            update.setString(3, user.getLogin());
            update.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Удаление пользователся.
     * @param login логин.
     */
    public void delete(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement delete = connection.prepareStatement(SQLQuery.DELETE)) {
            delete.setString(1, login);
            delete.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Закрываетие пула.
     * todo надо оно вообще?
     */
    public void closeSession() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            if (dataSource != null) {
                try {
                    dataSource.close();
                } catch (SQLException e1) {
                    logger.error(e1.getMessage(), e1);
                }
            }
        }
    }

    public static UserStoreJDBC getInstance() {
        return INSTANCE;
    }

    /**
     * Инициализация DBCP.
     * Данные для подключения - из файла userstore.properties.
     */
    private void init() {
        if (dataSource == null) {
            Properties prop = new Properties();
            try {
                prop.load(new InputStreamReader(UserStoreJDBC.class.getClassLoader().getResourceAsStream("userstore.properties")));
                Class.forName(prop.getProperty("driver"));
                this.dataSource = new BasicDataSource();
                this.dataSource.setUrl(prop.getProperty("database.url"));
                this.dataSource.setUsername(prop.getProperty("username"));
                this.dataSource.setPassword(prop.getProperty("password"));
            } catch (IOException | ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

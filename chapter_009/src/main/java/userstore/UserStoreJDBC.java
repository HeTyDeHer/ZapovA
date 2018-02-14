package userstore;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UserStoreJDBC implements UserStore {

    private Connection connection;
    private final Logger logger = LoggerFactory.getLogger(UserStoreJDBC.class.getName());

    private final static UserStoreJDBC instance = new UserStoreJDBC();
    private UserStoreJDBC() {
        this.init();
    }

    public synchronized void addUser(User user) {
        try (PreparedStatement add = connection.prepareStatement(SQLQuery.INSERT)){
            add.setString(1, user.getLogin());
            add.setString(2, user.getName());
            add.setString(3, user.getEmail());
            add.setTimestamp(4, user.getCreated());
            add.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public synchronized List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Statement getAll = connection.createStatement();
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

    public synchronized User getByLogin(String login) {
        User result = null;
        try (PreparedStatement getUser = connection.prepareStatement(SQLQuery.GET)) {
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

    public synchronized void update (User user) {
        try (PreparedStatement update = connection.prepareStatement(SQLQuery.UPDATE)) {
            update.setString(1, user.getName());
            update.setString(2, user.getEmail());
            update.setString(3, user.getLogin());
            update.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }


    public synchronized void delete (String login) {
        try (PreparedStatement delete = connection.prepareStatement(SQLQuery.DELETE)) {
            delete.setString(1, login);
            delete.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void closeSession() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            if (connection !=null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    logger.error(e1.getMessage(), e1);
                }
            }
        }
    }

    public static UserStoreJDBC getInstance() {
        return instance;
    }

    private void init() {
        Properties prop = new Properties();
        try {
            logger.error("qweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqweqwe");
            prop.load(new InputStreamReader(UserStoreJDBC.class.getClassLoader().getResourceAsStream("userstore.properties")));
            Class.forName(prop.getProperty("driver"));
            this.connection = DriverManager.getConnection(prop.getProperty("database.url"), prop.getProperty("username"), prop.getProperty("password"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

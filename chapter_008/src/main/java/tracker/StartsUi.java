package tracker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Взаимодействие с пользователем.
 * @author Алексей on 21.09.2017.
 */
public class StartsUi {
    /** Ввод. */
    private Input input;
    /** Массив. */
    private Tracker tracker;

    /** Конструктор.
     * @param input механизм ввода.
     */
    public StartsUi(Input input) {
        this.input = input;
    }

    /**
     * Get connection.
     * @return Connection.
     */
    private Connection getConnection() {
        Connection connection = null;
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(StartsUi.class.getClassLoader().getResourceAsStream("tracker.properties")));
            connection = DriverManager.getConnection(prop.getProperty("database.url"), prop.getProperty("username"), prop.getProperty("password"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /** Сообственно, взаимодействие. */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fill();
        List<Integer> range = new ArrayList<>();
        for (int i = 0; i < menu.getAction().size(); i++) {
            range.add(i, i);
        }
        int choice;
        try {
            do {
                menu.showMenu();
                choice = input.ask("Выбор: ", range);
                menu.select(choice);
            } while (choice != 6);
        } finally {
            if (tracker.getConn() != null) {
                try {
                    tracker.getConn().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    /** main.
     * @param args ??.
     */
    public static void main(String[] args) {
        Input input = new ValidateInput();
        StartsUi start = new StartsUi(input);
        start.setTracker(new Tracker(start.getConnection()));
        start.init();
    }

}

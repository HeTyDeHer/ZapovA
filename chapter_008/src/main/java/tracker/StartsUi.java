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

    /** Сообственно, взаимодействие. */
    public void init() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(StartsUi.class.getClassLoader().getResourceAsStream("tracker.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(prop.getProperty("database.url"), prop.getProperty("username"), prop.getProperty("password"))) {
            this.tracker.init(connection);
            MenuTracker menu = new MenuTracker(this.input, this.tracker);
            menu.fill();
            List<Integer> range = new ArrayList<>();
            for (int i = 0; i < menu.getAction().size(); i++) {
                range.add(i, i);
            }
            int choice;
                do {
                    menu.showMenu();
                    choice = input.ask("Выбор: ", range);
                    menu.select(choice, connection);
                } while (choice != 6);
        } catch (SQLException e) {
            e.printStackTrace();
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
        start.setTracker(new Tracker());
        start.init();
    }

}

package ru.start;

import java.util.ArrayList;
import java.util.List;

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
     * @param tracker массив.
     */
    public StartsUi(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
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
        do {
            menu.showMenu();
            choice = input.ask("Выбор: ", range);
            menu.select(choice);
        } while (choice != 6);
    }

    /** main.
     * @param args ??.
     */
    public static void main(String[] args) {
        Input input = new ValidateInput();
        new StartsUi(input, new Tracker()).init();
    }

}

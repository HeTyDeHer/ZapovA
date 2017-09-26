package ru.start;

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
        int choice = 10;
        do {
            menu.showMenu();
            try {
                choice = Integer.valueOf(input.ask("Выбор: "));
            } catch (NumberFormatException e) {
            }
            if (choice >= 0 && choice <= 6) {
                menu.select(choice);
            } else {
                System.out.println("Введите число от 0 до 6");
            }
        } while (choice != 6);
    }

    /** main.
     * @param args ??.
     */
    public static void main(String[] args) {
        Input input = new ConsoleInput();
        new StartsUi(input, new Tracker()).init();
    }

}

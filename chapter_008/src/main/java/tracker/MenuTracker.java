package tracker;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Меню трекер.
 * Created by Алексей on 26.09.2017.
 */
public class MenuTracker {
    /** Ввод. */
    private Input input;
    /** Массив item. */
    private Tracker tracker;
    /** Массив возможных действий. */
    private List<UserAction> action = new ArrayList<>();

    /** Геттер массива возможных действий.
     * @return массив.
     */
    public List<UserAction> getAction() {
        return action;
    }

    /**
     * Конструктор.
     * @param input ввод.
     * @param tracker массив item.
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /** Отображение меню.  */
    public void showMenu() {
        for (UserAction act : action) {
                System.out.println(act.info());
            }
    }
    /** Действие по ключу.
     * @param key ключ.
     */
    public void select(int key, Connection connection) {
        this.action.get(key).execute(this.input, this.tracker, connection);
    }
    /** Заполнение меню.  */
    public void fill() {
        this.action.add(0, new Add(0, "Add new item"));
        this.action.add(1, new Show(1, "Show all items"));
        this.action.add(2, new Edit(2, "Edit item"));
        this.action.add(3, new Delete(3, "Delete item"));
        this.action.add(4, new FindById(4, "Find item by ID"));
        this.action.add(5, new FindByName(5, "Find item by name"));
        this.action.add(6, new Exit(6, "Exit"));
    }

    /**
     * Добавление item в массив tracker.
     */
    private class Add extends BaseAction {
        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        Add(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            Item item = new Item(0, input.ask("Введите название: "), input.ask("Введиет описание: "),
                    LocalDateTime.now());
            tracker.add(item, connection);
            System.out.printf("%nЭлемент cоздан.%nID = %d %n Название %s %n Описание %s %n", item.getId(), item.getName(), item.getDescription());
        }

    }
    /**
     * Добавление item в массив tracker.
     */
    private class Show extends BaseAction  {

        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        Show(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            int z = 1;
            for (Item i : tracker.findAll(connection)) {
                System.out.printf("%nЭлемент %d %nID = %d %n Название %s %n Описание %s %n" +
                        " Создан %tF %<tH:%<tM:%<tS %n", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
                int c = 1;
                for (String s : i.getComments()) {
                    System.out.printf("Комментарий %d %n Описание %s %n", c++, s);
                }
            }
        }

    }
    /**
     * Редактирование item в массиве tracker.
     */
    private class Edit extends BaseAction  {
        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        Edit(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            int id;
            do {
                id = input.ask("Введите id элемента для редактирования или введите 0 для выхода в главное меню. ", 0);
                Item item = tracker.findById(id, connection);
                if (item != null) {
                    item.setName(input.ask("Введите новое название: "));
                    item.setDescription(input.ask("Введиет новое описание: "));
                    tracker.update(item, connection);
                    System.out.println("Элемент отредактирован.");
                } else if (0 != id) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (0 != id);
        }
    }
    /**
     * Удаление item из массива tracker.
     */
    private class Delete extends BaseAction  {
        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        Delete(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            int id;
            do {
                id = input.ask("Введите id элемента для удаления или введите 0 для выхода в главное меню. ", 0);
                Item item = tracker.findById(id, connection);
                if (item != null) {
                    tracker.delete(item, connection);
                    System.out.println("Элемент удален.");
                } else if (0 != id) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (0 != id);
        }
    }
    /**
     * Поиск по ID.
     */
    private class FindById extends BaseAction  {
        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        FindById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            int id;
            do {
                id = input.ask("Введите id элемента или введите 0 для выхода в главное меню. ", 0);
                Item item = tracker.findById(id, connection);
                if (item != null) {
                    System.out.printf("%n Название %s %n Описание %s %n Создан %tF %n", item.getName(), item.getDescription(), item.getCreated());
                    int c = 1;
                    for (String s : item.getComments()) {
                        System.out.printf("%n Комментарий %d %n Описание %s %n", c++, s);
                    }
                } else if (0 != id) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (0 != id);
        }

    }
    /**
     * Поиск по названию.
     */
    private class FindByName extends BaseAction  {
        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        FindByName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            String name;
            do {
                name = input.ask("Введите название элемента или введите 0 для выхода в главное меню. ");
                List<Item> item = tracker.findByName(name, connection);
                int z = 1;
                if (item.size() != 0) {
                    for (Item i : item) {
                        System.out.printf("%n Совпадение %d %n Название %s %n Описание %s %n Создан %tF %n", z++, i.getName(), i.getDescription(), i.getCreated());
                        int c = 1;
                        for (String s : i.getComments()) {
                            System.out.printf("%n Комментарий %d %n Описание %s %n", c++, s);
                        }
                    }
                } else if (!"0".equals(name)) {
                    System.out.println("Элемент с таким названием отсутствует, попробуйте ещё раз.");
                }
            } while (!"0".equals(name));
        }
    }
    /**
     * Выход.
     */
    private class Exit extends BaseAction {
        /**
         * Конструктор.
         * @param key ключ.
         * @param name описание.
         */
        Exit(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker traker, Connection connection) {
            System.out.println("Выход из программы.");

        }

    }




}

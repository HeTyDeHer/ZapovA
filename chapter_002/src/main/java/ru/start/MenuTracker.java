package ru.start;

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
    private UserAction[] action = new UserAction[7];

    /** Геттер массива возможных действий.
     * @return массив.
     */
    public UserAction[] getAction() {
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
    public void select(int key) {
        this.action[key].execute(this.input, this.tracker);
    }
    /** Заполнение меню.  */
    public void fill() {
        this.action[0] = new Add();
        this.action[1] = new Show();
        this.action[2] = new Edit();
        this.action[3] = new Delete();
        this.action[4] = new FindById();
        this.action[5] = new FindByName();
        this.action[6] = new Exit();
    }

    /**
     * Добавление item в массив tracker.
     */
    private class Add implements UserAction {

        @Override
        public int key() {
            return 0;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            Item item = new Item(input.ask("Введите название: "), input.ask("Введиет описание: "), System.currentTimeMillis());
            tracker.add(item);
            System.out.printf("%nЭлемент cоздан.%nID %s %n Название %s %n Описание %s %n ", item.getId(), item.getName(), item.getDescription());
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Add new item");
        }
    }
    /**
     * Добавление item в массив tracker.
     */
    private class Show implements UserAction {
        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            int z = 1;
            for (Item i : tracker.findAll()) {
                System.out.printf("%nЭлемент %d %n ID %s %n Название %s %n Описание %s %n Создан %tF %<tH:%<tM:%<tS %n", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
            }
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Show all items");
        }
    }
    /**
     * Редактирование item в массиве tracker.
     */
    private class Edit implements UserAction {
        @Override
        public int key() {
            return 2;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            String id;
            do {
                id = input.ask("Введите id элемента для редактирования или введите 0 для выхода в главное меню. ");
                Item item = tracker.findById(id);
                if (item != null) {
                    item = new Item(input.ask("Введите новое название: "), input.ask("Введиет новое описание: "), System.currentTimeMillis());
                    item.setId(id);
                    tracker.update(item);
                    System.out.println("Элемент отредактирован.");
                } else if (!"0".equals(id)) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (!"0".equals(id));
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Edit item");
        }
    }
    /**
     * Удаление item из массива tracker.
     */
    private class Delete implements UserAction {
        @Override
        public int key() {
            return 3;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            String id;
            do {
                id = input.ask("Введите id элемента для удаления или введите 0 для выхода в главное меню. ");
                Item item = tracker.findById(id);
                if (item != null) {
                    tracker.delete(item);
                    System.out.println("Элемент удален.");
                } else if (!"0".equals(id)) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (!"0".equals(id));
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Delete item");
        }
    }
    /**
     * Поиск по ID.
     */
    private class FindById implements UserAction {
        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            String id;
            do {
                id = input.ask("Введите id элемента или введите 0 для выхода в главное меню. ");
                Item item = tracker.findById(id);
                if (item != null) {
                    System.out.printf("%n ID %s %n Название %s %n Описание %s %n Создан %tF", item.getId(), item.getName(), item.getDescription(), item.getCreated());
                } else if (!"0".equals(id)) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (!"0".equals(id));
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Find item by ID");
        }
    }
    /**
     * Поиск по названию.
     */
    private class FindByName implements UserAction {
        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            String name;
            do {
                name = input.ask("Введите название элемента или введите 0 для выхода в главное меню. ");
                Item[] item = tracker.findByName(name);
                int z = 1;
                if (item.length != 0) {
                    for (Item i : item) {
                        System.out.printf("%n Совпадение %d%n ID %s %n Название %s %n Описание %s %n Создан %tF", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
                    }
                } else if (!"0".equals(name)) {
                    System.out.println("Элемент с таким названием отсутствует, попробуйте ещё раз.");
                }
            } while (!"0".equals(name));
        }
        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Find item by name");
        }
    }
    /**
     * Выход.
     */
    private class Exit implements UserAction {
        @Override
        public int key() {
            return 6;
        }

        @Override
        public void execute(Input input, Tracker traker) {
            System.out.println("Выход из программы.");
        }
        @Override
        public String info() {
            return String.format("%s. %s.", this.key(), "Exit");
        }
    }




}

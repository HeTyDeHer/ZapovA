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
        this.action[0] = new Add(0, "Add new item");
        this.action[1] = new Show(1, "Show all items");
        this.action[2] = new Edit(2, "Edit item");
        this.action[3] = new Delete(3, "Delete item");
        this.action[4] = new FindById(4, "Find item by ID");
        this.action[5] = new FindByName(5, "Find item by name");
        this.action[6] = new Exit(6, "Exit");
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
        public void execute(Input input, Tracker traker) {
            Item item = new Item(input.ask("Введите название: "), input.ask("Введиет описание: "), System.currentTimeMillis());
            tracker.add(item);
            System.out.printf("%nЭлемент cоздан.%nID %s %n Название %s %n Описание %s %n", item.getId(), item.getName(), item.getDescription());
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
        public void execute(Input input, Tracker traker) {
            int z = 1;
            for (Item i : tracker.findAll()) {
                System.out.printf("%nЭлемент %d %n ID %s %n Название %s %n Описание %s %n Создан %tF %<tH:%<tM:%<tS %n", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
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
        public void execute(Input input, Tracker traker) {
            String id;
            do {
                id = input.ask("Введите id элемента или введите 0 для выхода в главное меню. ");
                Item item = tracker.findById(id);
                if (item != null) {
                    System.out.printf("%n ID %s %n Название %s %n Описание %s %n Создан %tF %n", item.getId(), item.getName(), item.getDescription(), item.getCreated());
                } else if (!"0".equals(id)) {
                    System.out.println("Неверное id, попробуйте ещё раз");
                }
            } while (!"0".equals(id));
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
        public void execute(Input input, Tracker traker) {
            String name;
            do {
                name = input.ask("Введите название элемента или введите 0 для выхода в главное меню. ");
                Item[] item = tracker.findByName(name);
                int z = 1;
                if (item.length != 0) {
                    for (Item i : item) {
                        System.out.printf("%n Совпадение %d%n ID %s %n Название %s %n Описание %s %n Создан %tF %n", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
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
        public void execute(Input input, Tracker traker) {
            System.out.println("Выход из программы.");
        }

    }




}

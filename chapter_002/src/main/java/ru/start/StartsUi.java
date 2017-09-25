package ru.start;

/**
 * Взаимодействие с пользователем.
 * @author Алексей on 21.09.2017.
 */
public class StartsUi {
    /** Добавление в массив 0. */
    private static final String ADD = "0";
    /** Отображение массива 1. */
    private static final String SHOW = "1";
    /** Редактирование записи 2. */
    private static final String EDIT = "2";
    /** Удаление записи 3. */
    private static final String DEL = "3";
    /** Поиск по ID 4. */
    private static final String FINDID = "4";
    /** Поиск по имени 5. */
    private static final String FINDNAME = "5";
    /** Выход 6. */
    private static final String EXIT = "6";
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


    /**
     * 0. Добавление.
     */
    private void add() {
        Item item = new Item(input.ask("Введите название: "), input.ask("Введиет описание: "), System.currentTimeMillis());
        tracker.add(item);
        System.out.printf("%nЭлемент cоздан.%nID %s %n Название %s %n Описание %s %n ", item.getId(), item.getName(), item.getDescription());
    }
    /**
     * 1. Вывод всего массива.
     */
    private void show() {
        int z = 1;
        for (Item i : tracker.findAll()) {
            System.out.printf("%nЭлемент %d %n ID %s %n Название %s %n Описание %s %n Создан %tF", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
        }
    }
    /**
     * 2. Редактирование.
     */
    private void edit() {
        String id = input.ask("Введите id элемента для редактирования или введите 0 для выхода ");
        if (!id.equals("0")) {
            Item item = tracker.findById(id);
            if (item != null) {
                item = new Item(input.ask("Введите новое название: "), input.ask("Введиет новое описание: "), System.currentTimeMillis());
                item.setId(id);
                tracker.update(item);
            } else {
                System.out.println("Неверное id, попробуйте ещё раз");
                edit();
            }
        }
    }
    /**
     * 3. Удаление.
     */
    private void delete() {
        String id = input.ask("Введите id элемента для удаления или введите 0 для выхода ");
        if (!id.equals("0")) {
            Item item = tracker.findById(id);
            if (item != null) {
                tracker.delete(item);
            } else {
                System.out.println("Неверное id, попробуйте ещё раз");
                delete();
            }
        }
    }
    /**
     * 4. Поиск по ID.
     */
    private void findByID() {
        String id = input.ask("Введите id элемента или введите 0 для выхода ");
        Item item = tracker.findById(id);
        if (!id.equals("0")) {
            if (item != null) {
                System.out.printf("%n ID %s %n Название %s %n Описание %s %n Создан %tF", item.getId(), item.getName(), item.getDescription(), item.getCreated());
             } else {
                System.out.println("Неверное id, попробуйте ещё раз");
                findByID();
            }
        }
    }

    /**
     * 5. Поиск по имени.
     */
    private void findByName() {
        String name = input.ask("Введите название элемента или введите 0 для выхода ");
        if (!name.equals("0")) {
            Item[] item = tracker.findByName(name);
            int z = 1;
            if (item.length != 0) {
                for (Item i : item) {
                    System.out.printf("%n Совпадение %d%n ID %s %n Название %s %n Описание %s %n Создан %tF", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
                }
            } else {
                System.out.println("Элемент с таким названием отсутствует, попробуйте ещё раз");
                findByName();
            }
        }
    }
    /** Сообственно, взаимодействие. */
    public void init() {
        boolean exit = true;
        do {
            String in = input.ask("%n0. Add new Item%n1. Show all items%n2. Edit item%n3. Delete item%n4. Find item by Id%n5. Find items by name%n6. Exit Program%nSelect: ");
            if (in.equals(ADD)) {
                this.add();
            } else if (in.equals(SHOW)) {
                this.show();
            } else if (in.equals(EDIT)) {
                this.edit();
            } else if (in.equals(DEL)) {
                this.delete();
            } else if (in.equals(FINDID)) {
                this.findByID();
            } else if (in.equals(FINDNAME)) {
                this.findByName();
            } else if (in.equals(EXIT)) {
                exit = false;
            } else {
                System.out.println("Неверный ввод, попробуйте ещё раз.");
            }
        } while (exit);
    }

    /** main.
     * @param args ??.
     */
    public static void main(String[] args) {
        Input input = new ConsoleInput();
        new StartsUi(input, new Tracker()).init();
    }

}

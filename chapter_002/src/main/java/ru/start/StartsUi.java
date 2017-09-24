package ru.start;

/**
 * Взаимодействие с пользователем.
 * @author Алексей on 21.09.2017.
 */
public class StartsUi {
    private static final String ADD = "0";
    private static final String SHOW = "1";
    private static final String EDIT = "2";
    private static final String DEL = "3";
    private static final String FINDID = "4";
    private static final String FINDNAME = "5";
    private static final String EXIT = "6";

    private static ConsoleInput input = new ConsoleInput();
    private static Tracker tracker = new Tracker();
    /**
     * 0. Добавление.
     */
    private static void add() {
        Item item = new Item(input.ask("Введите название: "), input.ask("Введиет описание: "), System.currentTimeMillis());
        tracker.add(item);
        System.out.printf("%nЭлемент cоздан.%nID %s %n Название %s %n Описание %s %n ", item.getId(), item.getName(), item.getDescription());
    }
    /**
     * 1. Вывод всего массива.
     */
    private static void show() {
        int z = 1;
        for (Item i : tracker.findAll()) {
            System.out.printf("%nЭлемент %d %n ID %s %n Название %s %n Описание %s %n Создан %tF", z++, i.getId(), i.getName(), i.getDescription(), i.getCreated());
        }
    }
    /**
     * 2. Редактирование.
     */
    private static void edit() {
        String id = input.ask("Введите id элемента для редактирования или введите 0 для выхода ");
        if (id.equals("0")) {
        } else {
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
    private static void delete() {
        String id = input.ask("Введите id элемента для удаления или введите 0 для выхода ");
        if (id.equals("0")) {
        } else {
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
    private static void findByID() {
        String id = input.ask("Введите id элемента или введите 0 для выхода ");
        Item item = tracker.findById(id);
        if (id.equals("0")) {
        } else {
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
    private static void findByName() {
        String name = input.ask("Введите название элемента или введите 0 для выхода ");
        if (name.equals("0")) {
        } else {
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

    public static void main(String[] args) {
        boolean exit = true;
        do {
            String in = input.ask("%n0. Add new Item%n1. Show all items%n2. Edit item%n3. Delete item%n4. Find item by Id%n5. Find items by name%n6. Exit Program%nSelect: ");
            if (in.equals(ADD)) {
                StartsUi.add();
            } else if (in.equals(SHOW)) {
                StartsUi.show();
            } else if (in.equals(EDIT)) {
                StartsUi.edit();
            } else if (in.equals(DEL)) {
                StartsUi.delete();
            } else if (in.equals(FINDID)) {
                StartsUi.findByID();
            } else if (in.equals(FINDNAME)) {
                StartsUi.findByName();
            } else if (in.equals(EXIT)) {
                exit = false;
            } else {
                System.out.println("Неверный ввод, попробуйте ещё раз.");
            }
        } while (exit);

    }

}

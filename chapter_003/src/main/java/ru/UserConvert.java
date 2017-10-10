package ru;

import java.util.HashMap;
import java.util.List;

/**
 * 2. Написать программу преобразования List в Map. [#10093].
 * Created by Алексей on 09.10.2017.
 */
public class UserConvert {
    /**
     * Преобразовываем List User ов в HashMap.
     * @param list List User ов.
     * @return HashMap, в котором key - User.id.
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User user : list) {
            result.put(user.getId(), user);
        }
        return result;
    }

}

/**
 * User.
 */
class User implements Comparable {
    /** Имя, город. */
    private String name, city;
    /** Id, возраст. */
    private int id, age;

    /**
     * Конструктор.
     * @param name Имя.
     * @param age возраст.
     */
    User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Конструктор.
     * @param name Имя.
     * @param city Город.
     * @param id ID.
     * @param age возраст.
     */
    User(String name, String city, int id, int age) {
        this.name = name;
        this.city = city;
        this.id = id;
        this.age = age;
    }

    /**
     * Геттер Id.
     * @return Id.
     */
    public int getId() {
        return id;
    }

    /**
     * Сравнение по возрасту.
     * @param o второй юзер.
     * @return 0, если равны; +1, если первый > второго; -1 , если наоборот.
     */
    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return this.age == ((User) o).age ? 0 : this.age > ((User) o).age ? 1 : -1;
    }

}

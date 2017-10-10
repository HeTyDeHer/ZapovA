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
class User {
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
     * Геттер age.
     * @return age.
     */
    public int getAge() {
        return age;
    }
    /**
     * Геттер name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Геттер Id.
     * @return Id.
     */
    public int getId() {
        return id;
    }

    /**
     * toString.
     * @return name + age.
     */
    @Override
    public String toString() {
        return "name = " + name + ", age = " + age + ";";
    }
}


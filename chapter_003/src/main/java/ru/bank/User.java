package ru.bank;


/**
 * класс User с полями name, passport.
 * Created by Алексей on 10.10.2017.
 */
public class User {
    /** Имя. */
    private String name;
    /** Пасспорт. */
    private int passport;

    /**
     * Конструктор.
     * @param name Имя.
     * @param passport пасспорт.
     */
    public User(String name, int passport) {
        this.name = name;
        this.passport = passport;
    }

    /**
     * Getter.
     * @return passport.
     */
    public int getPassport() {
        return passport;
    }
    /**
     * Getter.
     * @return name.
     */
    public String getName() {
        return name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return getPassport() == user.getPassport();
    }

    @Override
    public int hashCode() {
        return getPassport();
    }

    @Override
    public String toString() {
        return "name = " + name + ", passport = " + passport;
    }
}

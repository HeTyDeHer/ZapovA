package ru.bank;

/**
 * класс Account с полями value (кол-во денег), requisites (реквизиты счёта) - это банковский счёт.
 * Created by Алексей on 10.10.2017.
 */
public class Account {
    /** Количество денег. */
    private double value;
    /** Реквизиты. */
    private long requisites;

    /**
     * Конструктор.
     * @param value количество денег.
     */
    public Account(int value) {
        this.value = value;
        this.requisites = System.nanoTime();
    }

    /**
     * Добавить денег.
     * @param value сумма для добавления.
     */
    public void addValue(double value) {
        this.value += value;
    }

    /**
     * Геттер.
     * @return реквизиты.
     */
    public long getRequisites() {
        return requisites;
    }

    /**
     * Геттер.
     * @return количество денег.
     */
    public double getValue() {
        return value;
    }

    /**
     * toString.
     * @return реквизиты и баланс.
     */
    @Override
    public String toString() {
        return "Account " + requisites + ", balance = " + value;
    }

    /**
     * equals.
     * @param o Объект к сравнению.
     * @return Счета равны, если равны реквизиты.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return getRequisites() == account.getRequisites();
    }

    /**
     * hashcode.
     * @return hash на основе реквизитовы.
     */

    @Override
    public int hashCode() {
        return (int) (getRequisites() ^ (getRequisites() >>> 32));
    }
}

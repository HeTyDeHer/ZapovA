package ru.start.vendingmachine;

/**
 * Монетки.
 * Created by Алексей on 01.10.2017.
 */
public abstract class Coins {
    /** количесвто.*/
    private int coins;
    /** качество. */
    private final int value;

    /**
     * Конструктор. Кладем 100 монет заданного номиналаю.
     * @param value номинал.
     */
    public Coins(int value) {
        this.coins = 100;
        this.value = value;
    }

    /**
     * Отдаем сдачу монетами заданного номинала.
     * @param amount сдача.
     * @return сколько не смогли отдать.
     */
    public int giveChange(int amount) {
        int counter = 0;
        while (amount / value != 0 && coins != 0) {
            amount -= value;
            coins--;
            counter++;
        }
        if (counter != 0) {
            System.out.print(counter + " монет по " + value + ", ");
        }

        return amount;
    }

    /**
     * Информация.
     * @return номинал монеты.
     */
    public String info() {
        return String.format("Монета %s", value);
    }

    /**
     * Грузим монетки.
     * @param number количесвто.
     */
    public void addCoins(int number) {
        coins += number;
    }

    /**
     * Поучение остатка монет.
     * @return количество монет.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Получение достоинства монеты.
     * @return достоинство монеты.
     */
    public int getValue() {
        return value;
    }
}

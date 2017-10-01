package ru.start.vendingmachine;

/**
 * Монетки.
 * Created by Алексей on 01.10.2017.
 */
public class Coins {
    /** количесвто.*/
    private int coins;
    /** качество. */
    private final int value;

    /**
     * Конструктор. Кладем 100 монет заданного номиналаю.
     * @param value номинал.
     */
    protected Coins(int value) {
        this.coins = 100;
        this.value = value;
    }

    /**
     * Отдаем сдачу монетами заданного номинала.
     * @param amount сдача.
     * @return сколько не смогли отдать.
     */
    public int giveChange(int amount) {
        int coinsCounter = 0;
        while (amount / value != 0 && coins != 0) {
            amount -= value;
            coins--;
            coinsCounter++;
        }
        if (coinsCounter > 0) {
            System.out.println("Выдано " + coinsCounter + " монет по " + value);
        }
        return amount;
    }

    /**
     * Грузим монетки.
     * @param number количесвто.
     */
    public void addCoins(int number) {
        coins += number;
    }
}

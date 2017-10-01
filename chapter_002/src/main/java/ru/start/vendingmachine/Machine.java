package ru.start.vendingmachine;

/**
 * Машина по продвже плюшек.
 * Created by Алексей on 01.10.2017.
 */
public class Machine {
    /** Ввод. */
    private Input input;
    /** Опачирик. */
    private Coins coin10;
    /** Пятак. */
    private Coins coin5;
    /** Двушечка. */
    private Coins coin2;
    /** Рублик. */
    private Coins coin1;

    /**
     * Конструктор.
     * @param input система ввода.
     * @param coin10 монетки 10.
     * @param coin5 5.
     * @param coin2 2.
     * @param coin1 1.
     */
    private Machine(Input input, Coins coin10, Coins coin5, Coins coin2, Coins coin1) {
        this.input = input;
        this.coin10 = coin10;
        this.coin5 = coin5;
        this.coin2 = coin2;
        this.coin1 = coin1;
    }

    /**
     * Выбор плюшек.
     * @return количество сдачи.
     */
    private int choice() {
        int cost = -1;
        int banknote = 0;
        do {
            switch (input.ask("Какие плюшки будете брать?"
                    + "%n0. Никакие. "
                    + "%n1. Шикарные, размером с большую печать, по 47. "
                    + "%n2. Стандартные, по 30 "
                    + "%n3. Детские, по 18."
                    + "%nВыбор: ")) {
                case 0: cost = 0;
                    break;
                case 1: cost = 47;
                    break;
                case 2: cost = 30;
                    break;
                case 3: cost = 18;
                    break;
                default:
                    System.out.println("Выберите число от 1 до 3");
            }
        } while (cost == -1);
        if (cost != 0) {
            do {
                switch (input.ask("Купюра? ")) {
                    case 10:
                        banknote += 10;
                        break;
                    case 50:
                        banknote += 50;
                        break;
                    case 100:
                        banknote += 100;
                        break;
                    case 500:
                        banknote += 500;
                        break;
                    default:
                        System.out.println("Банкноты 10, 50, 100, 500");
                }
            } while (banknote < cost);
        }
        return (banknote - cost);
    }

    /**
     * Выдача сдачи.
     * @param amount количество.
     */
    private void giveChange(int amount) {
        amount = coin10.giveChange(amount);
        amount = coin5.giveChange(amount);
        amount = coin2.giveChange(amount);
        amount = coin1.giveChange(amount);
        if (amount != 0) {
            System.out.println(amount + " автомату на чай");
        }
    }
    /** Основная. */
    private void work() {
        do {
            giveChange(choice());
        } while (input.ask("Нажмите 0 для выхода ") != 0);
    }

    /**
     * main.
     * @param args args.
     */
    public static void main(String[] args) {
        Machine machine = new Machine(new ConsoleInput(), new Coins(10), new Coins(5), new Coins(2), new Coins(1));
        machine.work();
    }

}

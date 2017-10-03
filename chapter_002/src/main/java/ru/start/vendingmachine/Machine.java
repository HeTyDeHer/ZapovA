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
    /** Диапазон меню. */
    private int[] menuRange = new int[] {0, 1, 2, 3};
    /** Возможные банкноты. */
    private int[] banknoteRange = new int[] {10, 50, 100, 500};


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
        switch (input.ask("Какие плюшки будете брать?"
                    + "%n0. Никакие. "
                    + "%n1. Шикарные, размером с большую печать, по 107. "
                    + "%n2. Стандартные, по 57 "
                    + "%n3. Детские, по 18."
                    + "%nВыбор: ", menuRange)) {
                case 0: cost = 0;
                    break;
                case 1: cost = 107;
                    break;
                case 2: cost = 57;
                    break;
                case 3: cost = 18;
                    break;
                default:
                    System.out.println("Выберите число от 0 до 3");
            }

        if (cost != 0) {
            do {
                System.out.println("Не хватает " + cost);
                switch (input.ask("Купюра? Банкноты 10, 50, 100, 500 ", banknoteRange)) {
                    case 10:
                        banknote += 10;
                        cost -= 10;
                        break;
                    case 50:
                        banknote += 50;
                        cost -= 50;
                        break;
                    case 100:
                        banknote += 100;
                        cost -= 100;
                        break;
                    case 500:
                        banknote += 500;
                        cost -= 500;
                        break;
                    default:
                        System.out.println("Банкноты 10, 50, 100, 500");
                }
            } while (cost > 0);
        }
        return  -cost;
    }

    /**
     * Выдача сдачи.
     * @param amount количество.
     */
    private void giveChange(int amount) {
        System.out.println("Сдача - " + amount);
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
        } while (!"0".equals(input.ask("Введите 0 для выхода ")));
    }

    /**
     * main.
     * @param args args.
     */
    public static void main(String[] args) {
        Machine machine = new Machine(new ValidateInput(), new Coins(10), new Coins(5), new Coins(2), new Coins(1));
        machine.work();
    }

}

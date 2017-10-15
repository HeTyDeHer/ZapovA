package ru.start.vendingmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Машина по продвже плюшек.
 * Created by Алексей on 01.10.2017.
 */
public class Machine {
    /** Ввод. */
    private Input input;
    /** Монеты в аппарате. */
    private List<Coins> coins;

    /** Диапазон меню. */
    private List<Integer> menuRange = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
    /** Возможные банкноты. */
    private List<Integer> banknoteRange = new ArrayList<>(Arrays.asList(10, 50, 100, 500));

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
        this.coins = new ArrayList<>(Arrays.asList(coin10, coin5, coin2, coin1));
    }

    /**
     * Выбор плюшек.
     * @return количество сдачи.
     */


    private int choice() {
        int price = 0;
        switch (input.ask("Какие плюшки будете брать?"
                    + "%n0. Никакие. "
                    + "%n1. Шикарные, размером с большую печать, по 107. "
                    + "%n2. Стандартные, по 57 "
                    + "%n3. Детские, по 18."
                    + "%nВыбор: ", menuRange)) {
                case 0: price = 0;
                    break;
                case 1: price = 107;
                    break;
                case 2: price = 57;
                    break;
                case 3: price = 18;
                    break;
                default:
                    System.out.println("Выберите число от 0 до 3");
            }
        return  price;
    }

    /**
     * Получение денег.
      * @param price снеобходимая сумма.
     * @return внесенная сумма.
     */
    private int pay(int price) {
        int paid = 0;
        do {
                System.out.println("Не хватает " + price);
                switch (input.ask("Купюра? Банкноты 10, 50, 100, 500 ", banknoteRange)) {
                    case 10:
                        paid += 10;
                        price -= 10;
                        break;
                    case 50:
                        paid += 50;
                        price -= 50;
                        break;
                    case 100:
                        paid += 100;
                        price -= 100;
                        break;
                    case 500:
                        paid += 500;
                        price -= 500;
                        break;
                    default:
                        System.out.println("Банкноты 10, 50, 100, 500");
                }
            } while (price > 0);
        return paid;
    }

    /**
     * Выбор предпочитаемой монеты для сдачи.
     * @return индекс этой монеты в массиве монет.
     */
    private int chooseChangeValue() {
        System.out.println("Выберите номинал монеты для сдачи.");
        List<Integer> availableCoins = new ArrayList<>();
        for (Coins c : coins) {
            if (c.getCoins() != 0) {
                System.out.println(c.info());
                availableCoins.add(c.getValue());
            }
        }
        int preferableValue = input.ask("Выбор? ", availableCoins);
        int indexOfpreferableValue = 0;
        for (Coins c : coins) {
            if (c.getValue() == preferableValue) {
                indexOfpreferableValue = coins.indexOf(c);
                break;
            }
        }
        return indexOfpreferableValue;
    }
    /**
     * Выдача сдачи.
     * @param paid внесенная сумма.
     * @param price цена.
     */
    private void giveChange(int price, int paid) {
        int change = paid - price;
        System.out.printf("Сдача %s%n", change);
        System.out.print("Выдано: ");
        change = coins.get(chooseChangeValue()).giveChange(change);
        if (change != 0) {
            for (Coins c : coins) {
                change = c.giveChange(change);
            }
        }
        System.out.println("всего " + (paid - price - change));
        if (change != 0) {
            System.out.println(change + " автомату на чай");
        }
    }


    /** Основная. */
    private void work() {
        do {
            int price = choice();
            if (price != 0) {
                giveChange(price, pay(price));
            }
        } while (!"0".equals(input.ask("Введите 0 для выхода ")));
    }

    /**
     * main.
     * @param args args.
     */
    public static void main(String[] args) {
        Machine machine = new Machine(new ValidateInput(), new Coin10(), new Coin5(), new Coin2(), new Coin1());
        machine.work();
    }
}
/** 10. */
 class Coin10 extends Coins {
    /**
     * Конструктор. Кладем 100 монет заданного номиналаю.
     */
     Coin10() {
        super(10);
    }
}
/** 5. */
 class Coin5 extends Coins {
    /**
     * Конструктор. Кладем 100 монет заданного номиналаю.
     */
     Coin5() {
        super(5);
    }
}
/** 2. */
 class Coin2 extends Coins {
    /**
     * Конструктор. Кладем 100 монет заданного номиналаю.
     */
    Coin2() {
        super(2);
    }
}
/** Рубль. */
 class Coin1 extends Coins {
    /**
     * Конструктор. Кладем 100 монет заданного номиналаю.
     */
    Coin1() {
        super(1);
    }
}

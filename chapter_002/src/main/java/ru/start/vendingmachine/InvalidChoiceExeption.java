package ru.start.vendingmachine;

/**
 * Непраильный выбор exeption.
 * Created by Алексей on 02.10.2017.
 */
public class InvalidChoiceExeption extends RuntimeException {
    /**
     * Непраильный выбор exeption.
     * @param msg сообщение.
     */
    public InvalidChoiceExeption(String msg) {
        super(msg);
    }
}

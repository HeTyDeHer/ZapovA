package ru.start;

/**
 * Exeption не соответствует диапазону.
 * Created by Алексей on 26.09.2017.
 */
public class MenuOutExeption extends RuntimeException {
    /**
     * не соответствует диапазону.
     * @param msg сообзение.
     */
    public MenuOutExeption(String msg) {
        super(msg);
    }
}

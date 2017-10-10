package ru.start.chess;

/**
 * Фигура не найдена исключение.
 * Created by Алексей on 29.09.2017.
 */
public class FigureNotFoundException extends Exception {
    /** Фигура не найдена исключение.
     * @param msg сообщение. */
    public FigureNotFoundException(String msg) {
        super(msg);
    }
}

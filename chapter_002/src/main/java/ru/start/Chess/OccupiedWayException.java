package ru.start.chess;

/**
 * Занятый путь exeption.
 * Created by Алексей on 29.09.2017.
 */
public class OccupiedWayException extends Exception {
    /**  Занятый путь exeption.
     * @param msg сообщение.
     */
    public OccupiedWayException(String msg) {
        super(msg);
    }
}

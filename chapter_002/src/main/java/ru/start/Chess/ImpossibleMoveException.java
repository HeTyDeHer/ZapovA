package ru.start.Chess;

/**
 * Created by Алексей on 27.09.2017.
 */
public class ImpossibleMoveException extends Exception {
    public ImpossibleMoveException (String msg) {
        super(msg);
    }
}

package ru.start.Chess;

/**
 * Created by Алексей on 29.09.2017.
 */
public class OccupiedWayException extends Exception{
    public OccupiedWayException(String msg) {
        super(msg);
    }
}

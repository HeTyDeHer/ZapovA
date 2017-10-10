package ru.start.chess;

/**
 * Невозможный ход исключение.
 * Created by Алексей on 27.09.2017.
 */
public class ImpossibleMoveException extends Exception {
  /** Невозможный ход исключение.
   * @param msg сообщение.  */
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
}

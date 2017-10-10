package ru.start.chess;

/**
 * Created by Алексей on 27.09.2017.
 */
public interface Input {
    /**
     * @param question Запрос пользователю.
     * @return ввод от пользователя.
     */
    String ask(String question);
}

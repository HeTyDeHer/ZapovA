package ru.start.vendingmachine;

/**
 * Интерфейс input.
 * Created by Алексей on 27.09.2017.
 */
public interface Input {
    /**
     * @param question Запрос пользователю.
     * @return ввод от пользователя.
     */
    int ask(String question);

}

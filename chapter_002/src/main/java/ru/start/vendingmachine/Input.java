package ru.start.vendingmachine;

import java.util.List;

/**
 * Интерфейс input.
 * Created by Алексей on 27.09.2017.
 */
public interface Input {

    /**
     * @param question Запрос пользователю.
     * @return ввод от пользователя.
     */

    String ask(String question);

    /**
     * @param question Запрос пользователю.
     * @param range Диапазон выбора.
     * @return ввод от пользователя.
     */

    int ask(String question, List<Integer> range);


}

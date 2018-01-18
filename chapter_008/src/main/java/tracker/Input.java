package tracker;

import java.util.List;

/**
 * Интерфейс ввода.
 * Created by Алексей on 22.09.2017.
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

    /**
     * @param question Запрос пользователю.
     * @param min минимальное значение для выбора.
     * @return ввод от пользователя.
     */
    int ask(String question, int min);
}

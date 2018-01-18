package tracker;

/**
 * Интерфейс для описание действий пользователя.
 * Created by Алексей on 26.09.2017.
 */
public interface UserAction {
    /**
     * Ключ меню для действия.
     * @return ключ
     */
    int key();

    /**
     * Действие.
     * @param input ввод.
     * @param traker массив.
     */
    void execute(Input input, Tracker traker);

    /**
     * Описание действия.
     * @return описание.
     */
    String info();
}

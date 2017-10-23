package generic;

/**
 * Сделать интерфейс Store<T extends Base>.
 * @param <T> class extends Base.
 * Created by Алексей on 22.10.2017.
 */
public interface Store<T extends Base> {
    /**
     * Добавление.
     * @param model объект для добавления.
     * @return объект для добавления(зачем?).
     */
    T add(T model);

    /**
     * Обновление.
     * @param model объект для обновления.
     * @return старый объект.
     */
    T update(T model);

    /**
     * Удаление.
     * @param id id объекта для удаления.
     * @return получилось/нет.
     */
    boolean delete(String id);
}

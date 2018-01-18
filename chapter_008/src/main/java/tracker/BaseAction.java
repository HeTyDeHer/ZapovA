package tracker;

/**
 * Класс действия пользователя.
 * Created by Алексей on 27.09.2017.
 */
public abstract class BaseAction implements UserAction {
    /**
     * @param key ключ.
     */
    private int key;
    /**
     * @param name Описание(??).
     */
    private String name;

    /**
     * Конструктор.
     * @param key ключ.
     * @param name описание.
     */
    public BaseAction(int key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String info() {
        return String.format("%s. %s.", this.key, this.name);
    }
}

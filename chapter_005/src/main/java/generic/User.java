package generic;

/**
 * Сделать два класса User, и Role которые наследуют Base класс.
 * Created by Алексей on 22.10.2017.
 */
public class User extends Base {
    /**
     * Конструктор.
     * @param id id.
     * @param name name.
     */
    public User(String id, String name) {
        super(id, name);
    }
}


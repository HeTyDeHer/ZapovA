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

/**
 * Сделать два класса хранилища UserStore и RoleStore. Внутри для хранения данных использовать SimpleArray.
 * @param <T> класс, наследуемый от base.
 */
class UserStore<T extends Base> extends AbstractStore {
    /** Внутренний массив. */
    private SimpleArray<Base> sarray;
    /**
     * Конструктор.
     * @param size размер внутреннего массива.
     */
    UserStore(int size) {
        super(size);
    }
}

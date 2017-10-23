package generic;

/**
 * Сделать два класса User, и Role которые наследуют Base класс.
 * Created by Алексей on 22.10.2017.
 */
public class Role extends Base {
    /** id.*/
    private String id;
    /** name.*/
    private String name;

    /**
     * Конструктор.
     * @param id id.
     * @param name name.
     */
    public Role(String id, String name) {
        super(id, name);
    }
}

/**
 * Сделать два класса хранилища UserStore и RoleStore. Внутри для хранения данных использовать SimpleArray.
 * @param <T> класс, наследуемый от base.
 */
class RoleStore<T extends Base> extends AbstractStore {
    /** Внутренний массив. */
    private SimpleArray<Base> sarray;
    /**
     * Конструктор.
     * @param size размер внутреннего массива.
     */
    RoleStore(int size) {
        super(size);
    }
}

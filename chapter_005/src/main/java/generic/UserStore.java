package generic;

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

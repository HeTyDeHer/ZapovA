package generic;

/**
 * Сделать два класса хранилища UserStore и RoleStore. Внутри для хранения данных использовать SimpleArray.
 * @param <T> класс, наследуемый от base.
 */
class RoleStore<T extends Base> extends AbstractStore<Role> {
    /** Внутренний массив. */
    private SimpleArray<T> sarray;
    /**
     * Конструктор.
     * @param size размер внутреннего массива.
     */
    RoleStore(int size) {
        super(size);
    }
}

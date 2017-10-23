package generic;

/**
 * UserStore и RoleStore будут иметь один и тот же функционал. Общий для них функционал необходимо вынести в абстрактный класс AbstractStore.
 * @param <T> class T extends Base.
 * Created by Алексей on 22.10.2017.
 */
public abstract class AbstractStore<T extends Base> implements Store<Base> {
    /** Внутренний массив SympleArray. */
    private SimpleArray<Base> sarray;

    /**
     * Конструктор.
     * @param size размер массива.
     */
    public AbstractStore(int size) {
        this.sarray = new SimpleArray<>(size);
    }

    /**
     * Добавление.
     * @param model объект для добавления.
     * @return объект для добавления(зачем?).
     */
    @Override
    public Base add(Base model) {
        this.sarray.add(model);
        return model;
    }

    /**
     * Обновление.
     * @param model объект для обновления.
     * @return старый объект.
     */
    @Override
    public Base update(Base model) {
        return sarray.update(model, model);
    }
    /**
     * Удаление.
     * @param id id объекта для удаления.
     * @return получилось/нет.
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < sarray.size(); i++) {
            if (sarray.get(i).getId().equals(id)) {
                sarray.delete(i);
                result = true;
            }
        }
        return result;
    }
}

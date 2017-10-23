package generic;

/**
 * 5.2.1. Реализовать SimpleArray<T> [#156].
 * @param <T> тип данных.
 * Created by Алексей on 21.10.2017.
 */
public class SimpleArray<T> {
    /** Сам массив. */
    private T[] array;
    /** Индекс первого свободной ячейки. */
    private int index = 0;

    /**
     * Конструктор.
     * @param size размер массива.
     */
    public SimpleArray(int size) {
        this.array = (T[]) new Object[size];
    }

    /**
     * Добавление значения.
     * @param value значение для добавления.
     */
    public void add(T value) {
        this.array[index++] = value;
    }

    /**
     * Обновление значения по указанному индексу.
     * @param indexToUpdate индекс.
     * @param value новое значение.
     * @return старое значение.
     */
    public T update(int indexToUpdate, T value) {
        T result = this.array[indexToUpdate];
        this.array[indexToUpdate] = value;
        return result;
    }

    /**
     * Обновление значения по значению.
     * @param value значение для обновления.
     * @param newValue новое значение.
     * @return старое значение.
     */
    public T update(T value, T newValue) {
        int i;
        for (i = 0; i < this.index; i++) {
            if (this.array[i].equals(value)) {
                break;
            }
        }
        T result = this.array[i];
        this.array[i] = newValue;
        return result;
    }
    /**
     * Удаление значения по указанному индексу.
     * @param indexToDelete индекс.
     */
    public void delete(int indexToDelete) {
        System.arraycopy(this.array, indexToDelete + 1, this.array, indexToDelete, --index - indexToDelete);
        this.array[index] = null;
    }

    /**
     * Получение значения по указанному индексу.
     * @param indexToGet индекс.
     * @return значение.
     */
    public T get(int indexToGet) {
        return this.array[indexToGet];
    }

    /**
     * Получение индекса по значению.
     * @param value значение.
     * @return индекс.
     */
    public int get(T value) {
        int i = 0;
        for (; i < index; i++) {
            if (this.array[i].equals(value)) {
                break;
            }
        }
        return i;
    }

    /**
     * Размер заполненного массива.
     * @return размер заполненного массива.
     */
    public int size() {
        return this.index;
    }
}

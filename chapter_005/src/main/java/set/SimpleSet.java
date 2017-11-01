package set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. Реализовать коллекцию Set на массиве [#996].
 * Реализовать коллекцию SimpleSet. Коллекция должна обеспечивать void add(E e) и реализовывать Iterator<E>.
 * Коллекция не должна хранить дубликаты.
 * Set - внутри для хранения данных использует обычные массивы.
 * Created by Алексей on 29.10.2017.
 */
public class SimpleSet<T> implements Iterable<T> {
    /** Внутреннее хранидище.*/
    private T[] container = (T[]) new Object[10];
    /** индекс первой свободной ячейки. */
    private int index = 0;

    /**
     * Содержит ли set элемент?.
     * @param value элемент.
     * @return да/нет.
     */
    public boolean contain(T value) {
        boolean result = false;
        for (int i = 0; i < index; i++) {
            if (container[i] != null && container[i].equals(value)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Добавление элемента. Если элемент уже содержится, не добавляем его.
     * @param value элемент.
     */
    public void add(T value) {
        if (contain(value)) {
            return;
        }
        if (this.index == this.container.length) {
            T[] newContainer = (T[]) new Object[this.container.length * 3 / 2];
            System.arraycopy(this.container, 0, newContainer, 0, this.index - 1);
            this.container = newContainer;
        }
        container[this.index++] = value;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < index;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[currentIndex++];
            }
        };
    }
}

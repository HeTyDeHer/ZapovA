package list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 5.3.1. Создать динамический список на базе массива. [#158].
 * @param <T> что вообще мавен предполагает здесь писать?
 * Created by Алексей on 22.10.2017.
 */
@ThreadSafe
public class SimpleArrayList<T> implements Iterable<T> {
    /** Внутренний контейнер. */
    private T[] container;
    /** Индекс первой свободной ячейки. */
    private int index = 0;
    /** Конструктор. Начальная емкость - 10. */
    public SimpleArrayList() {
        this.container = (T[]) new Object[10];
    }

    /**
     * Добавление элемента.
     * Если емкость массива не достаточна, пересоздаем с емкостью в 1,5 раза больше.
     * @param value объект для добавления.
     */
    @GuardedBy("this")
    public synchronized void add(T value) {
        if (this.index == this.container.length) {
            T[] newContainer = (T[]) new Object[this.container.length * 3 / 2];
            System.arraycopy(this.container, 0, newContainer, 0, this.index - 1);
            this.container = newContainer;
        }
        container[this.index++] = value;
    }

    /**
     * Получаем элемент по индексу. Если индекса нет - ArrayIndexOutOfBoundsException.
     * @param index индекс.
     * @return объект по указанному индексу.
     */
    @GuardedBy("this")
    public synchronized T get(int index) {
        checkIndex(index);
        return container[index];
    }

    /**
     * Удаление элемента по индексу. Если индекса нет - ArrayIndexOutOfBoundsException.
     * @param index индекс элемента для удаления.
     * @return old старый элемент.
     */
    @GuardedBy("this")
    public synchronized T remove(int index) {
        checkIndex(index);
        T old = this.container[index];
        System.arraycopy(this.container, index + 1, this.container, index, --this.index - index);
        container[this.index] = null;
        return old;
    }

    /**
     * Проверка, существует ли указанный индекс в массиве.
     * @param index индек сдля проверки.
     */
    private void checkIndex(int index) {
        if (index >= this.index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;
            final Object obj = new Object();
            @Override
            @GuardedBy("obj")

            public boolean hasNext() {
                synchronized (obj) {
                    return currentIndex < index;
                }
            }

            @Override
            @GuardedBy("obj")
            public synchronized T next() {
                synchronized (obj) {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return container[currentIndex++];
                }
            }

            @Override
            @GuardedBy("this")
            public synchronized void remove() {
                if (currentIndex != 0) {
                    SimpleArrayList.this.remove(--currentIndex);
                } else {
                    throw new IllegalStateException();
                }
            }
        };
    }
}


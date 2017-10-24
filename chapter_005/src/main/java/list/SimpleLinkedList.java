package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 5.3.2. Создать контейнер на базе связанного списка  [#159].
 *  @param <T> T
 * Created by Алексей on 22.10.2017.
 */
public class SimpleLinkedList<T> implements Iterable<T> {


    /**
     * Хранилище элемента с информацией о предыдущем/следующем.
     * @param <T> T
     */
    public static class Element<T> {
        /** Следующий контейнер в списке. */
        private Element<T> nextElement;
        /** Предыдущий контейнер в списке. */
        private Element<T> previousElement;
        /** Непосредственно хранимое значение. */
        private T value;

        /**
         * Геттер value.
         * @return value.
         */
        public T getValue() {
            return value;
        }
        /**
         * Геттер getPreviousElement.
         * @return getPreviousElement.
         */
        public Element<T> getPreviousElement() {

            return previousElement;
        }
        /**
         * Геттер nextElement.
         * @return nextElement.
         */
        public Element<T> getNextElement() {

            return nextElement;
        }
        /**
         * Cеттер value.
         */
        public void setValue(T value) {
            this.value = value;
        }
        /**
         * Cеттер getPreviousElement.
         */
        public void setPreviousElement(Element<T> previousElement) {

            this.previousElement = previousElement;
        }
        /**
         * Cеттер nextElement.
         */
        public void setNextElement(Element<T> nextElement) {

            this.nextElement = nextElement;
        }

        /**
         * Конструктор.
         * @param nextElement следующий контейнер.
         * @param previousElement предыдущий.
         * @param value хранимое значение.
         */
        private Element(Element<T> nextElement, Element<T> previousElement, T value) {
            this.nextElement = nextElement;
            this.previousElement = previousElement;
            this.value = value;
        }

    }

    /** Стартовый контейнер. */
    private Element<T> base = new Element<>(null, null, null);

    /** Количество хранимых элементов . */
    private int size = 0;

    public SimpleLinkedList() {
        this.base.nextElement = base;
        this.base.previousElement = base;
    }



    /**
     * Поиск элемнета по индексу.
     * @param index индекс.
     * @return элемент.
     */
    private Element<T> findElementByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Element<T> result = null;
        if (index < size / 2) {
            result = base.nextElement;
            for (int i = 0; i < index; i++) {
                result = result.nextElement;
            }
        } else {
            result = base.previousElement;
            for (int i = 0; i < size - index - 1; i++) {
                result = result.previousElement;
            }
        }
        return result;
    }

    /**
     * Добавление элемента.
     * @param value элемент.
     */
    public void add(T value) {
        Element<T> element = new Element<>(base, base.previousElement, value);
        element.nextElement.previousElement = element;
        element.previousElement.nextElement = element;
        size++;
    }

    /**
     * Получение элемента по индексу.
     * @param index индекс.
     * @return значение.
     */
    public T get(int index) {
        return this.findElementByIndex(index).value;
    }

    /**
     * Удаление элемента по индексу.
     * @param index индекс.
     * @return удаленное значение.
     */
    public T remove(int index) {
        Element<T> toRemove = this.findElementByIndex(index);
        T result = toRemove.value;
        toRemove.nextElement.previousElement = toRemove.previousElement;
        toRemove.previousElement.nextElement = toRemove.nextElement;
        size--;
        return result;
    }

    /**
     * Удаление первого добавленного элемента.
     * @return удаленный элемент.
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T result = base.nextElement.value;
        base.nextElement.nextElement.previousElement = base;
        base.nextElement = base.nextElement.nextElement;
        size--;
        return result;
    }

    /**
     * Удаление последнего добавленного элемента.
     * @return удаленный элемент.
     */
    public T removeLast() {
        T result = base.getPreviousElement().getValue();
        base.getPreviousElement().getPreviousElement().setNextElement(getBase());
        base.setPreviousElement(base.getPreviousElement().getPreviousElement());
        size--;
        return result;
    }

    /**
     * Геттер юазовый элемент.
     * @return базовый элемент.
     */
    public Element<T> getBase() {
        return base;
    }

    /**
     * Геттер размер.
     * @return размер.
     */

    public int getSize() {
        return size;
    }

    /**
     * Уменьшение размра списка на 1.
     */
    public void reduceSize() {
        this.size--;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Element<T> currentElement = base;
            @Override
            public boolean hasNext() {
                return !currentElement.nextElement.equals(base);
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                currentElement = currentElement.nextElement;
                return currentElement.value;
            }

            @Override
            public void remove() {
                if (currentElement.equals(base)) {
                    throw new IllegalStateException();
                } else {
                    currentElement.nextElement.previousElement = currentElement.previousElement;
                    currentElement.previousElement.nextElement = currentElement.nextElement;
                    currentElement = currentElement.previousElement;
                    size--;
                }
            }
        };
    }
}

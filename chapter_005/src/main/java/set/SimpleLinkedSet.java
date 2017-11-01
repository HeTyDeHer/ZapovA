package set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 2. Set реализованный на связном списке. [#997].
 * Реализовать коллекцию SimpleSet.
 * Коллекция должна обеспечивать void add(E e) и реализовывать Iterator<E>.
 * Коллекция не должна хранить дубликаты.
 * Set - внутри для хранения данных использует связный список.
 * Created by Алексей on 29.10.2017.
 */
public class SimpleLinkedSet<T> implements Iterable<T> {
    /**
     * Внутренний класс. Хранилище информации о предыдущем/следующем объекте.
     * @param <T> T.
     */
    private class Element<T> {
        /** Следующий эдемент. */
        Element<T> next;
        /** Предыдущий. */
        Element<T> prev;
        /** Хранимое значение. */
        T value;

        /**
         * Конструктор.
         * @param next Следующий эдемент.
         * @param prev Предыдущий.
         * @param value Хранимое значение.
         */
        public Element(Element<T> next, Element<T> prev, T value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        /**
         * Сеттер.
         * @param value хранимое значение.
         */
        public void setValue(T value) {
            this.value = value;
        }
        /**
         * Сеттер.
         * @param prev предыдущий элемент.
         */
        public void setPrev(Element<T> prev) {

            this.prev = prev;
        }
        /**
         * Сеттер.
         * @param next слдеующий элемент.
         */
        public void setNext(Element<T> next) {

            this.next = next;
        }

        /**
         * Геттер.
         * @return хранимое значение.
         */
        public T getValue() {

            return value;
        }
        /**
         * Геттер.
         * @return предыдущий элемент.
         */
        public Element<T> getPrev() {

            return prev;
        }
        /**
         * Геттер.
         * @return следующий элемент.
         */
        public Element<T> getNext() {

            return next;
        }
    }

    /**
     * Базовый элемент. Хранит информацию о первом и последнем элементе списка.
     */
    private Element<T> base = new Element<>(null, null, null);
    /**
     * Размер списка.
     */
    private int size = 0;

    /**
     * Конструктор. Задаем базовый элемент.
     */
    public SimpleLinkedSet() {
        this.base.setNext(base);
        this.base.setPrev(base);
        this.base.setValue(null);
    }

    /**
     * Проверка, содержится ли значение в списке.
     * @param value значение.
     * @return да-нет.
     */
    public boolean contain(T value) {
        Element<T> currentElement = base;
        boolean result = false;
        while (!currentElement.getNext().equals(base)) {
            currentElement = currentElement.getNext();
            if (currentElement.getValue().equals(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Добавление значения.
     * @param value значение.
     */
    public void add(T value) {
        if (contain(value)) {
            return;
        }
        Element<T> element  = new Element<>(base, base.getPrev(), value);
        element.getPrev().setNext(element);
        element.getNext().setPrev(element);
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Element<T> currentElement = base;
            @Override
            public boolean hasNext() {
                return !currentElement.getNext().equals(base);
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                currentElement = currentElement.getNext();
                return currentElement.getValue();
            }
        };
    }
}

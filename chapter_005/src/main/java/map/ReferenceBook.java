package map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 8. Реализовать собственную структуру данных - справочник. [#1008].
 * Справочник должен быть унифицирован через генерики.
 * иметь методы
 * boolean insert(T key, V value);
 * V get(T key);
 * boolean delete(T key);
 * Реализовывать итератор.
 * Внутренняя реализация должна использовать массив.
 * нужно обеспечить фиксированное время вставки и получение.
 * Created by Алексей on 01.11.2017.
  */
public class ReferenceBook<T, V> implements Iterable<V> {
    /** Внутреннее хранидище.*/
    private Element<T, V>[] container;
    /** Количество хранимых элементов. */
    private int size = 0;

    /**
     * Геттер.
     * @return количество хранимых элементов.
     */
    public int getSize() {
        return size;
    }

    /** Конструктор. */
    public ReferenceBook() {
        this.container =  new Element[16];
    }



    private static class Element<T, V> {

        /** Следующий эдемент. */
        Element<T, V> next;
        /** Предыдущий элемент. */
        Element<T, V> prev;
        /** Ключ. */
        T key;
        /** Хранимое значение. */
        V value;
        /** Hash value. */
        int hash;


        /**
         * Конструктор.
         * @param value Хранимое значение.
         */
        private Element(T key, V value) {
            this.key = key;
            this.value = value;

        }
        /**
         * Конструктор.
         * @param value Хранимое значение.
         */
        private Element(Element<T, V> next, Element<T, V> prev, T key, V value) {
            this.next = next;
            this.prev = prev;
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }

        /**
         * Геттер.
         * @return следующий элемент в ячейке.
         */
        public Element<T, V> getNext() {
            return next;
        }

        /**
         * Сеттер.
         * @param next следующий элемент в ячейке.
         */
        public void setNext(Element<T, V> next) {
            this.next = next;
        }
        /**
         * Геттер.
         * @return предыдущий элемент в ячейке.
         */
        public Element<T, V> getPrev() {
            return prev;
        }

        /**
         * Сеттер.
         * @param prev предыдущий элемент в ячейке.
         */
        public void setPrev(Element<T, V> prev) {
            this.prev = prev;
        }
        /**
         * Геттер.
         * @return ключ элемента.
         */
        public T getKey() {
            return key;
        }

        /**
         * Сеттер.
         * @param key ключ элемента.
         */
        public void setKey(T key) {
            this.key = key;
        }
        /**
         * Геттер.
         * @return ключ элемента.
         */
        public V getValue() {
            return value;
        }

        /**
         * Сеттер.
         * @param value значение элемента.
         */
        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * Добавление элемента в map.
     * В случае, если элемент уже содержится в массиве, просто возвращаем false.
     * В случае, если количество хранимых элементов > длины массива *0,75
     * увеличиваем массив в 2 раза.
     * @param key ключ.
     * @param value значение.
     * @return добавлено/нет.
     */
    public boolean insert(T key, V value) {
        if (size > container.length * 4 / 3) {
            resize();
        }
        int index = chooseIndex(key);
        if (contains(key)) {
            return false;
        }
        if (container[index] == null) {
            Element<T, V> e = new Element<>(key, value);
            container[index] = e;
            container[index].setNext(container[index]);
            container[index].setPrev(container[index]);
        } else {
            Element<T, V> e = new Element<>(container[index], container[index].getPrev(), key, value);
            container[index].getPrev().setNext(e);
            container[index].setPrev(e);
        }
        size++;
        return true;
    }

    /**
     * Получаем значение по ключу.
     * @param key ключ.
     * @return значение (null в случае, если ключ отсутствует).
     */
    public V get(T key) {
        int index = chooseIndex(key);
        if (container[index] == null) {
            return null;
        }
        V result = null;
        Element<T, V> start = container[index];
        do {
            if (start.getKey().equals(key)) {
                result = start.getValue();
                break;
            }
            start = start.getNext();
        } while (!start.equals(container[index]));
        return result;
    }

    /**
     * Удаляем элемент по ключу.
     * @param key ключ.
     * @return true - удалено, false - ключ отсутствует.
     */
    public boolean delete(T key) {
        if (!contains(key)) {
            return false;
        }
        int index = chooseIndex(key);
        Element<T, V> start = container[index];
        if (start.getNext().equals(container[index])) {
            container[index] = null;
            size--;
            return true;
        }
        do {
            if (start.getKey().equals(key)) {
                start.getPrev().setNext(start.getNext());
                start.getNext().setPrev(start.getPrev());
                break;
            }
            start = start.getNext();
        } while (!start.equals(container[index]));
        size--;
        return true;
    }

    /**
     * Проверка, содержится ли данный ключ в map.
     * @param key ключ.
     * @return да/нет.
     */
    public boolean contains(T key) {
        return get(key) != null;
    }

    /**
     * Выбор индекса в массиве по ключу на основе метода hash(key.hashCode()).
     * @param key ключ.
     * @return индес в массиве.
     */
    private int chooseIndex(T key) {
        return hash(key) % container.length;
    }

    /**
     * Считаем хэш.
     * @param key ключ.
     * @return хэш.
     */
    private int hash(T key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Увеличение емкости массива в 2 раза.
     */
    private void resize() {
        Element<T, V>[] oldContainer = container;
        container = new Element[oldContainer.length * 2];
        size = 0;
        for (Element<T, V> e : oldContainer) {
            if (e != null) {
                Element<T, V> start = e;
                do {
                    insert(start.getKey(), start.getValue());
                    start = start.getNext();
                } while (!start.equals(e));
            }
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int index = 0;
            private int containerIndex = 0;
            Element<T, V> currentElement;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public V next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (currentElement != null && currentElement.equals(container[containerIndex])) {
                    containerIndex++;
                    currentElement = container[containerIndex];
                }
                while (currentElement == null) {
                    currentElement = container[containerIndex];
                    if (currentElement == null) {
                        containerIndex++;
                    }
                }
                V result = currentElement.getValue();
                index++;
                currentElement = currentElement.getNext();
                return result;
            }
        };
    }
}

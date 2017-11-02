package set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Уменьшить время добавления элемента в SimpleSet/SimpleLinkedSet [#998].
 * Created by Алексей on 30.10.2017.
 */
public class FasterSimpleSet<T> implements Iterable<T> {
    /** Внутреннее хранидище.*/
    private Element<T>[] container;
    /** Количество хранимых элементов. */
    private int size = 0;

    public int getSize() {
        return size;
    }

    /** Конструктор. */
    public FasterSimpleSet() {
        this.container =  new Element[16];
    }

    private static class Element<T> {

        /** Следующий эдемент. */
        Element<T> next;
        /** Хранимое значение. */
        T value;
        /** Hash value. */
        int hash;


        /**
         * Конструктор.
         * @param next Следующий эдемент.
         * @param value Хранимое значение.
         */
        public Element(Element<T> next, T value) {
            this.next = next;
            this.value = value;
            this.hash = value.hashCode();
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
         * @return следующий элемент.
         */
        public Element<T> getNext() {
            return next;
        }
        /**
         * Геттер.
         * @return hash от value.
         */
        public int getHash() {
            return hash;
        }
    }

    /**
     * Добавление значения.
     * @param value значение.
     * @return успешно/нет (Нет - в случае, если Set уже содержит данное значение).
     */

    public boolean add(T value) {
        ensureCapacity();
        boolean result = false;
        int index = chooseIndex(value, container.length);
        if (!indexContains(index, value)) {
            if (container[index] == null) {
                container[index] = new Element<>(null, value);
                container[index].setNext(container[index]);
            } else {
                Element<T> element = new Element<>(container[index].getNext(), value);
                container[index].setNext(element);
            }
            result = true;
            size++;
        }
        return result;
    }

    /**
     * Проверка емкости внутреннего хранилища.
     * В случае, если хранилище заполнено на 3/4,
     * данный метод удваивает емкость хранилища
     * и пересчитывает индексы хранимых значений для нового хранилища.
     */
    private void ensureCapacity() {
        if (size > container.length / 4 * 3) {
            Element<T>[] oldContainer = container;
            container = new Element[container.length * 2];
            size = 0;
            for (int i = 0; i < oldContainer.length; i++) {
                if (oldContainer[i] != null) {
                    Element<T> start = oldContainer[i];
                    do {
                        add(start.getValue());
                        start = start.getNext();
                    } while (!start.equals(oldContainer[i]));
                }
            }
        }
    }
    /**
     * Проверка, содержит ли данная корзина указанный значение.
     * @param index индекс
     * @param value значение.
     * @return да-нет.
     */
    private boolean indexContains(int index, T value) {
        boolean result = false;
        if (container[index] == null) {
            return result;
        }
        int hash = value.hashCode();
        Element<T> start = container[index];
        do {
            if (start.getHash() == hash && start.getValue().equals(value)) {
                result = true;
                break;
            }
            start = start.getNext();
        }
        while (!start.equals(container[index]));
        return result;
    }

    /**
     * Генерируем hash на основе значения.
     * Реализация нагло стырена из литературы без понимания сути происходящего.
     * Вернее, без понимания того, почему именно такая реализация обеспечивает наименьшее число коллизий.
     * @param h value.hashCode().
     * @return новый hash.
     */
    private int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Выбираем индекс на основе hash(value.hashCode()) и длины массива.
     * @param value значение.
     * @param containerLength длина массива.
     * @return индекс в массиве.
     */
    private int chooseIndex(T value, int containerLength) {
        return hash(value.hashCode()) % containerLength;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            private int containerIndex = 0;
            Element<T> currentElement;
            @Override
            public boolean hasNext() {
               return index < size;
            }

            @Override
            public T next() {
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
                T result = currentElement.getValue();
                index++;
                currentElement = currentElement.getNext();
                return result;
            }
        };
    }

    public static void main(String[] args) {
        FasterSimpleSet<String> fss = new FasterSimpleSet<>();
        SimpleLinkedSet<String> sls = new SimpleLinkedSet<>();
        SimpleSet<String> ss = new SimpleSet<>();

        long startTime = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            ss.add(String.valueOf(j));
        }
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime); //~23000ms на моем компьютере

        startTime = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            sls.add(String.valueOf(j));
        }
        endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime); //~35000ms на моем компьютере

        startTime = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            fss.add(String.valueOf(j));
        }
        endTime = System.currentTimeMillis();  //~60ms на моем компьютере

        System.out.println(endTime - startTime);

    }

}


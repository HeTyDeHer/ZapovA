package list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 5.3.2. Создать контейнер на базе связанного списка  [#159].
 *  @param <T> T
 * Created by Алексей on 22.10.2017.
 */
@ThreadSafe
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

        public Element() {
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
    @GuardedBy("this")
    public synchronized void add(T value) {
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
     * Сортировка связанного списка.
     * После сортировки восстанавливаем связи с базовым элементом
     * и ссылки на предыдущий элемент у всех элементов.
     */
    @GuardedBy("this")
    public synchronized  <E extends Comparable<E>> void sort() {
        Element<E> temp1 = mergeSort((Element<E>) base.getNextElement());
        Element<E> temp2;
        temp1.setPreviousElement((Element<E>) base);
        base.setNextElement((Element<T>) temp1);
        while (!temp1.getNextElement().equals(base)) {
            temp2 = temp1;
            temp1 = temp1.getNextElement();
            temp1.setPreviousElement(temp2);
        }
        base.setPreviousElement((Element<T>) temp1);
    }

    /**
     * Разбиваем список на составляющие,
     * и передаем в метод merge.
     * @param head первый элемент списка.
     * @return первый элемент отсортированного списка.
     */
    private <E  extends Comparable<E>> Element<E> mergeSort(Element<E> head) {
        if (head.equals(base) || head.getNextElement().equals(base)) {
            return head;
        }
        Element<E> a = head;
        Element<E> b = head.getNextElement();
        while (!b.equals(base) && !b.getNextElement().equals(base)) {
            head = head.getNextElement();
            b = b.getNextElement().getNextElement();
        }
        b = head.getNextElement();
        head.setNextElement((Element<E>) base);
        a = mergeSort(a);
        b = mergeSort(b);
        return merge(a, b);
    }

    /**
     * Собираем один сортированный список из двух сортированных списков.
     * @param head1 первый элемнет первого сортированного списка.
     * @param head2 первый элемнет второго сортированного списка.
     * @return первый элемент итогового сортированного списка.
     */
    private <E  extends Comparable<E>> Element<E> merge(Element<E> head1, Element<E> head2) {

        Element<E> newHead = new Element<>();
        Element<E> pointer = newHead;
        while (!head1.equals(base) && !head2.equals(base)) {
            if (head1.getValue().compareTo(head2.getValue()) < 0) {
                pointer.setNextElement(head1);
                pointer = pointer.getNextElement();
                head1 = head1.getNextElement();
            } else {
                pointer.setNextElement(head2);
                pointer = pointer.getNextElement();
                head2 = head2.getNextElement();
            }
        }
        if (head1.equals(base)) {
            pointer.setNextElement(head2);
        } else {
            pointer.setNextElement(head1);
        }
        return newHead.getNextElement();
    }

    /**
     * Удаление элемента по индексу.
     * @param index индекс.
     * @return удаленное значение.
     */
    @GuardedBy("this")
    public synchronized T remove(int index) {
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
    @GuardedBy("this")
    public synchronized T remove() {
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
    @GuardedBy("this")
    public synchronized T removeLast() {
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
            @GuardedBy("this")
            public synchronized void remove() {
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

package list;

import java.util.EmptyStackException;


/**
 * 5.3.3. Используя контейнер на базе связанного списка создать контейнер Stack. [#160].
 * Created by Алексей on 23.10.2017.
 */
public class SimpleStack<T> extends SimpleLinkedList<T> {

    /**
     * Poll. Как я понимаю, т.к. Stack LIFO, то должен отдавать последний добавленный элемент.
     * @return последний добавленный элемент в стеке.
     */
    public T pop() {
        if (this.getSize() == 0) {
            throw new EmptyStackException();
        }
        return super.removeLast();
    }

    /**
     * push. Добавление элемента в очередь.
     * @param value элемент.
     */
    public void push(T value) {
        super.add(value);
    }

    /**
     * peek. Смотрим последний добавленный элемент бкз удаления элемента.
     * @return последний добавленный элемент.
     */
    public T peek() {
        if (this.getSize() == 0) {
            throw new EmptyStackException();
        }
       return super.getBase().getPreviousElement().getValue();
    }
}

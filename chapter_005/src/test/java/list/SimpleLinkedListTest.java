package list;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест 5.3.2. Создать контейнер на базе связанного списка  [#159].
 * Created by Алексей on 22.10.2017.
 */
public class SimpleLinkedListTest {
    /**
     * Тест сортировки.
     * Результат проверяем через get(), чтобы убедиться, что обратные ссылки также восстановились.
     */
    @Test
    public void sortTest() {
        SimpleLinkedList<Integer> sll = new SimpleLinkedList<>();
        sll.add(15);
        sll.add(-5);
        sll.add(0);
        sll.sort();
        assertThat(sll.get(0), is(-5));
        assertThat(sll.get(1), is(0));
        assertThat(sll.get(2), is(15));

    }

    /**
     * Тест метода remove() без избыточного поведения.
     * Добавляем 1 элемент.
     * Убеждаемся, что remove его удаляет.
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void deleteTest() {
        SimpleLinkedList<String> linklist = new SimpleLinkedList<>();
        linklist.add("0");
        assertThat(linklist.remove(), is("0"));
        linklist.get(0);
    }

    /**
     * Тест методов листа.
     * Добавляем 2 элементов.
     * Убеждаемся, что get корректно работает в обе стороны.
     * Убеждаемся, что remove() удаляет нужный элемент.
     * Убеждаемся, что remove(index) удаляет нужный элемент.
     */

    @Test (expected = IndexOutOfBoundsException.class)
    public void addDeleteGetTest() {
        SimpleLinkedList<String> linklist = new SimpleLinkedList<>();
        linklist.add("0");
        linklist.add("1");
        assertThat(linklist.get(0), is("0"));
        assertThat(linklist.get(1), is("1"));
        assertThat(linklist.remove(), is("0"));
        assertThat(linklist.remove(0), is("1"));
        linklist.get(0);
    }

    /**
     * Тест итератора LinkedList.
     * Убеждаемся, что hasNext() работает и не двигает каретку.
     * Убеждаемся, что next() работает и двигает каретку.
     */
    @Test (expected = NoSuchElementException.class)
    public void iteratorOfSimpleArrayListTest() {
        SimpleLinkedList<String> linklist = new SimpleLinkedList<>();
        Iterator<String> iterator = linklist.iterator();
        linklist.add("0");
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("0"));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();

    }

}
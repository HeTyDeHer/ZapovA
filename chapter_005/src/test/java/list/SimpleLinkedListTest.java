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
     * Тест методов листа.
     * Добавляем 2 элементов.
     * Убеждаемся, что remove удаляет нужный элемент.
     * Убеждаемся, что get корректно работает в обе стороны.
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void addDeleteGetTest() {
        SimpleLinkedList<String> linklist = new SimpleLinkedList<>();
        linklist.add("0");
        linklist.add("1");
        linklist.add("2");
        assertThat(linklist.get(0), is("0"));
        assertThat(linklist.get(2), is("2"));
        assertThat(linklist.remove(1), is("1"));
        assertThat(linklist.remove(), is("0"));
        linklist.get(1);
    }
    /**
     * Тест итератора LinkedList.
     */
    @Test (expected = NoSuchElementException.class)
    public void iteratorOfSimpleArrayListTest() {
        SimpleLinkedList<String> linklist = new SimpleLinkedList<>();
        Iterator<String> iterator = linklist.iterator();
        for (int i = 0; i < 5; i++) {
            linklist.add(String.valueOf(i));
        }
        assertThat(iterator.hasNext(), is(true));
        iterator.hasNext();
        assertThat(iterator.next(), is("0"));
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.next(), is("2"));
        iterator.remove();
        assertThat(linklist.get(2), is("3"));
        assertThat(iterator.next(), is("3"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("4"));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

}
package list;

import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест 5.3.3. Используя контейнер на базе связанного списка создать контейнер Stack и Queue. [#160].
 * Created by Алексей on 23.10.2017.
 */
public class SimpleStackTest {
    /**
     * Тест методов листа.
     * Добавляем 2 элементов.
     * Убеждаемся, что pop удаляет нужный элемент.
     * Убеждаемся, что get корректно работает.
     */
    @Test (expected = EmptyStackException.class)
    public void addDeleteGetTest() {
        SimpleStack<String> sstack = new SimpleStack<>();
        sstack.push("0");
        sstack.push("1");
        assertThat(sstack.get(0), is("0"));
        assertThat(sstack.peek(), is("1"));
        assertThat(sstack.pop(), is("1"));
        sstack.pop();
        sstack.pop();
    }
    /**
     * Тест итератора SimpleStack.
     */
    @Test (expected = NoSuchElementException.class)
    public void iteratorOfSimpleArrayListTest() {
        SimpleStack<String> sstack = new SimpleStack<>();
        Iterator<String> iterator = sstack.iterator();
        sstack.push("0");
        sstack.push("1");
        assertThat(iterator.hasNext(), is(true));
        iterator.hasNext();
        assertThat(iterator.next(), is("0"));
        iterator.remove();
        assertThat(sstack.get(0), is("1"));
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

}
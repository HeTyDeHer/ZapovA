package list;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест 5.3.1. Создать динамический список на базе массива. [#158].
 * Created by Алексей on 22.10.2017.
 */

public class SimpleArrayListTest {
    /**
     * Тест методов массива.
     * Добавляем 20 элементов.
     * Убеждаемся, что remove удаляет нужный элемент,
     * возвращает значение старого элемента и сдвигает оставшийся массив.
     *
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void addDeleteGetTest() {
        SimpleArrayList<String> arrlist = new SimpleArrayList<>();
        for (int i = 0; i < 21; i++) {
            arrlist.add(String.valueOf(i));
        }
        assertThat(arrlist.remove(15), is("15"));
        assertThat(arrlist.get(15), is("16"));
        arrlist.add("15");
        assertThat(arrlist.get(20), is("15"));
        arrlist.get(21);
    }

    /**
     * Тест итератора массива.
     */
    @Test (expected = NoSuchElementException.class)
    public void iteratorOfSimpleArrayListTest() {
        SimpleArrayList<String> arrlist = new SimpleArrayList<>();
        Iterator<String> iterator = arrlist.iterator();
        for (int i = 0; i < 5; i++) {
            arrlist.add(String.valueOf(i));
        }
        assertThat(iterator.hasNext(), is(true));
        iterator.hasNext();
        assertThat(iterator.next(), is("0"));
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.next(), is("2"));
        iterator.remove();
        assertThat(arrlist.get(2), is("3"));
        assertThat(iterator.next(), is("3"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("4"));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }
}
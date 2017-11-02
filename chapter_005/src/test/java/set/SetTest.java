package set;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Уменьшить время добавления элемента в SimpleSet/SimpleLinkedSet [#998].
 * Created by Алексей on 30.10.2017.
 */
public class SetTest {
    /** Тест FasterSimpleSet. */
    @Test (expected = NoSuchElementException.class)
    public void fasterSimpleSetTest() {
        FasterSimpleSet<String> fss = new FasterSimpleSet<>();
        assertThat(fss.add("0"), is(true));                     // убеждаемся, что элементы добавляются.
        for (int i = 0; i < 30; i++) {                                  // убеждаемся, что ensureCapacity правильно перераспределяет индексы
            fss.add(String.valueOf(i));                                 // для этого заставляем вызвать этот метод 2 раза...
        }
        for (int i = 0; i < 30; i++) {
            assertThat(fss.add(String.valueOf(i)), is(false));    //... и убеждаемся, что повторно элементы не добавляются.
        }
        Iterator<String> iterator = fss.iterator();
        assertThat(iterator.hasNext(), is(true));                   // убеждаемся, что hasNext() работает правильно.
        for (int i = 0; i < 30; i++) {
            iterator.next();
        }
        assertThat(iterator.hasNext(), is(false));                  // убеждаемся, что hasNext() работает правильно.
        iterator.next();                                                   // и что итератор перебирает нужное количество элементов.

        SimpleSet<String> ss = new SimpleSet<>();
    }
    /** Тест SimpleSet. */
    @Test (expected = NoSuchElementException.class)
    public void simpleSetTest() {
        SimpleSet<String> ss = new SimpleSet<>();
        ss.add("0");
        ss.add("0");
        Iterator<String> iterator = ss.iterator();
        assertThat(iterator.hasNext(), is(true));                   // убеждаемся, что hasNext() работает правильно.
        assertThat(iterator.next(), is("0"));                       //
        assertThat(iterator.hasNext(), is(false));                  // убеждаемся, что hasNext() работает правильно и add не добавляет дублирующие элементы.
        iterator.next();                                                   // и что итератор перебирает нужное количество элементов.

    }
    /** Тест SimpleLinkedSet. */
    @Test (expected = NoSuchElementException.class)
    public void simpleLinkedSetTest() {
        SimpleLinkedSet<String> sls = new SimpleLinkedSet<>();
        sls.add("0");
        sls.add("0");
        Iterator<String> iterator = sls.iterator();
        assertThat(iterator.hasNext(), is(true));                   // убеждаемся, что hasNext() работает правильно.
        assertThat(iterator.next(), is("0"));                       //
        assertThat(iterator.hasNext(), is(false));                  // убеждаемся, что hasNext() работает правильно и add не добавляет дублирующие элементы.
        iterator.next();                                                   // и что итератор перебирает нужное количество элементов.

    }
}
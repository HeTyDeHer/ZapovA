package map;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Реализовать собственную структуру данных - справочник. [#1008].
 * Created by Алексей on 02.11.2017.
 */
public class ReferenceBookTest {
    @Test
    public void whenAddDuplicateShouldReturnFalse() {
        ReferenceBook<String, Integer> rb = new ReferenceBook<>();
        assertThat(rb.insert("zero", 0), is(true));
        assertThat(rb.insert("zero", 10), is(false));
        assertThat(rb.getSize(), is(1));
    }
    @Test
    public void deleteAndGetTest() {
        ReferenceBook<String, Integer> rb = new ReferenceBook<>();
        assertThat(rb.insert("zero", 0), is(true));
        assertThat(rb.delete("0"), is(false));
        assertThat(rb.get("zero"), is(0));
        assertThat(rb.delete("zero"), is(true));
        assertThat(rb.getSize(), is(0));
    }

    @Test (expected = NoSuchElementException.class)
    public void resizeAndIteratorTest() {
        ReferenceBook<Integer, Integer> rb = new ReferenceBook<>();
        Iterator<Integer> iterator = rb.iterator();
        for (int i = 0; i < 49; i++) {
            assertThat(rb.insert(i, i), is(true));      //вызываем resize() 3 раза.
        }
        for (int i = 0; i < 49; i++) {                        //и убеждаемся, что при resize() не были потеряны элементы.
            assertThat(rb.insert(i, i), is(false));
        }
        assertThat(iterator.hasNext(), is(true));
        for (int i = 0; i < 49; i++) {
            iterator.next();
        }
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

}
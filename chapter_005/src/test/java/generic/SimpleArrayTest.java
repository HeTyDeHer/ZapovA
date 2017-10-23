package generic;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Тест 5.2.1. Реализовать SimpleArray<T> [#156].
 * Created by Алексей on 22.10.2017.
 */
public class SimpleArrayTest {
    /**
     * Тест добавления, обновления, удаления, получения.
     */
    @Test
    public void addUpdateDeleteGetTest() {
        SimpleArray<String> sarray = new SimpleArray<>(5);
        sarray.add("A");
        sarray.add("B");
        sarray.add("C");
        sarray.add("D");
        sarray.add("E");
        sarray.update(1, "BB");
        sarray.update("A", "AA");
        sarray.delete(3);
        sarray.add("DD");
        assertThat(sarray.get(0), is("AA"));
        assertThat(sarray.get(1), is("BB"));
        assertThat(sarray.get(2), is("C"));
        assertThat(sarray.get(3), is("E"));
        assertThat(sarray.get(4), is("DD"));
    }
}
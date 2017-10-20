package iterators;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тесты 5.1.2. Создать итератор четные числа [#150].
 * Created by Алексей on 20.10.2017.
 */
public class IteratorEvenTest {
    /**
     * Тест который последовательно вызывает hasNext и next .
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldReturnEvenNumbersSequentially() {
        IteratorEven it = new IteratorEven(new int[]{1, 2, 3, 4, 5, 6, 7});
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(6));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
    /**
     * Тест, что последовательный вызов hasNext без вызова next не влияет на возврат значений.
     */
    @Test public void checkThatHasNextDoesntAffect() {
        IteratorEven it = new IteratorEven(new int[]{1, 2, 4, 6});
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(6));
    }

}
package iterators;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест 5.1.3. Создать итератор простые числа. [#151].
 * Created by Алексей on 20.10.2017.
 */
public class PrimeItTest {
    /**
     * Тест который последовательно вызывает hasNext и next .
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldReturnPrimeNumbersOnly() {
        PrimeIt it = new PrimeIt(new int[]{1, 2, 3, 4, 5, 6, 7, 3571});
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(7));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3571));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
    /**
     * Тест, что последовательный вызов hasNext без вызова next не влияет на возврат значений.
     */
    @Test
    public void checkThatHasNextDoesntAffect() {
        PrimeIt it = new PrimeIt(new int[]{2, 3, 4, 5, 6, 7, 12, 3571});
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(7));
        assertThat(it.next(), is(3571));
    }


}
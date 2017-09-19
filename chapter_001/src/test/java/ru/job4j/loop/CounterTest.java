package ru.job4j;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест задачи 4.1. Подсчет суммы чётных чисел в диапазоне [#192].
 * Подсчет суммы чётных чисел в диапазоне.
 * @author ZapovA
 * @since 14.9.17
*/
public class CounterTest {
/**
* Подсчет суммы чётных чисел в диапазоне.
*/
@Test
	public void sumOfEven() {
		Counter counter = new Counter();
		int result = counter.add(-10, 20);
		assertThat(result, is(80));
	}
}
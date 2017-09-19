package ru.job4j;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Тест задачи 3.1 /001, 3.4/001 Максимум из трех чисел [#190].
 * @author ZapovA
 * @since 14.9.17
*/
public class MaxTest {
 /**
 * Тест задачи 3.1 /001, 3.4/001 Максимум из трех чисел [#190].
 * Первое число меньше второго.
*/
@Test
	public void whenFirstLessSecond() {
		Max maxim = new Max();
		int result = maxim.maxOfTwo(1, 2);
		assertThat(result, is(2));
	}
/**
 * Второе число меньше первого.
*/
@Test
	public void whenSecondLessFirst() {
		Max maxim = new Max();
		int result = maxim.maxOfTwo(2, 1);
		assertThat(result, is(2));
	}
/**
 * Три числа.
*/
@Test
	public void threeNumbers() {
		Max maxim = new Max();
		int result = maxim.maxOfThree(-15, 0, 1);
		assertThat(result, is(1));
	}

}

package ru.job4j;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест задачи 4.2. Создать программу вычисляющую факториал. [#193].
 * @author ZapovA
 * @since 14.9.17
*/
public class FactorialTest {
/**
* Тест факториал отрицательного числа.
*/
@Test
	public void factorialNegative() {
	Factorial f = new Factorial();
	long result = f.factorial(-10);
	assertThat(result, is(0L));
	}

/**
* Тест факториал положительного числа.
*/
@Test
	public void factorialPositive() {
	Factorial f = new Factorial();
	long result = f.factorial(10);
	assertThat(result, is(3628800L));
	}

/**
* Тест факториал нуля.
*/
@Test
	public void factorialZero() {
	Factorial f = new Factorial();
	long result = f.factorial(0);
	assertThat(result, is(1L));
	}
}
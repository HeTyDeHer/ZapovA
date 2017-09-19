package ru.job4j.array;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест 5.0 Перевернуть массив [#4441].
 * @author ZapovA
 * @since 14.9.17
*/
public class TurnTest {
/**
 * Тест переворота, четное число элементов.
*/
 @Test
	public void turnEven() {
	Turn turn = new Turn();
	int[] array = new int[] {1, 2, 3, 4, 5};
	int[] result = turn.back(array);
	int[] expected = new int[] {5, 4, 3, 2, 1};
	assertThat(result, is(expected));
	}
/**
 * Тест переворота, нечетное число элементов.
*/
 @Test
	public void turnOdd() {
	Turn turn = new Turn();
	int[] array = new int[] {1, 2, 3, 4, 5, 6};
	int[] result = turn.back(array);
	int[] expected = new int[] {6, 5, 4, 3, 2, 1};
	assertThat(result, is(expected));
	}
}
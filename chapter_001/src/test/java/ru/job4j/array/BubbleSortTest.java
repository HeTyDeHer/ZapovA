package ru.job4j.array;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест 5.1. Создать программу для сортировки массива методом перестановки. [#195].
 * @author ZapovA
 * @since 14.9.17
*/
public class BubbleSortTest {
/**
 * Тест сортировки.
*/

 @Test
	public void sortArray() {
	BubbleSort bubbleSort = new BubbleSort();
	int[] arrayToSort = new int[] {9, 0, -5, 2, 15};
	int[] result = bubbleSort.sort(arrayToSort);
	int[] expected = new int[] {-5, 0, 2, 9, 15};
	assertThat(result, is(expected));
	}
}
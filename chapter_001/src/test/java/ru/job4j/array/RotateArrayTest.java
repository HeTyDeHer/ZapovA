package ru.job4j.array;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест 5.2. Создание программы поворота квадратного массива. [#223].
 * @author ZapovA
 * @since 14.9.17
*/
public class RotateArrayTest {
/**
 * Тест поворота, 2x2.
*/
 @Test
	public void twoXtwoRotate() {
	RotateArray rotate = new RotateArray();
	int[][] array = new int[2][2];
	int k = 1;
	for (int i = 0; i < array.length; i++) {
		for (int j = 0; j < array.length; j++) {
			array[i][j] = k++;
			}
		}
	int[][] result = rotate.rotate(array);
	int[][] expected = new int[array.length][array.length];
	k = 1;
	for (int i = expected.length - 1; i >= 0; i--) {
		for (int j = 0; j < expected.length; j++) {
			expected[j][i] = k++;
			}
		}
	assertThat(result, is(expected));
	}

/**
 * Тест поворота, 3x3.
*/
 @Test
	public void threeXthreeRotate() {
	RotateArray rotate = new RotateArray();
	int[][] array = new int[3][3];
	int k = 1;
	for (int i = 0; i < array.length; i++) {
		for (int j = 0; j < array.length; j++) {
			array[i][j] = k++;
			}
		}
	int[][] result = rotate.rotate(array);
	int[][] expected = new int[array.length][array.length];
	k = 1;
	for (int i = expected.length - 1; i >= 0; i--) {
		for (int j = 0; j < expected.length; j++) {
			expected[j][i] = k++;
			}
		}
	assertThat(result, is(expected));
	}
}
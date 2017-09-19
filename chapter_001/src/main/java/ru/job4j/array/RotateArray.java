package ru.job4j.array;

/**
 * 5.2. Создание программы поворота квадратного массива. [#223].
 * @author ZapovA
 * @since 14.9.17
*/
public class RotateArray {
/**
 * Метод для поворота квадратного массива на 90.
 * @param array - входной массив.
 * @return повернутый.
 */
 public int[][] rotate(int[][] array) {
	for (int i = 0; i < array.length; i++) {
		for (int j = i; j < array.length; j++) {
			int temp = array[i][j];
			array[i][j] = array[j][i];
			array[j][i] = temp;
			}
		}
	for (int i = 0; i < array.length; i++) {
		for (int j = 0; j < array.length / 2; j++) {
			int temp = array[i][j];
			array[i][j] = array[i][array.length - j - 1];
			array[i][array.length - j - 1] = temp;
			}
		}
	return array;
}
}
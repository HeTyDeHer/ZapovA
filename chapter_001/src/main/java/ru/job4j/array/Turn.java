package ru.job4j.array;

/**
 * 5.0 Перевернуть массив [#4441].
 * @author ZapovA
 * @since 14.9.17
*/
public class Turn {
/**
 * Переворачиваем массив.
 * @param array - входной массив.
 * @return перевернутый массив.
 */
	public int[] back(int[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			int temp = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = temp;
		}
		return array;
	}
}
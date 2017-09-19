package ru.job4j.array;

/**
 * 5.1. Создать программу для сортировки массива методом перестановки. [#195].
 * @author ZapovA
 * @since 14.9.17
*/
public class BubbleSort {
/**
 * Метод для сортировки массива методом перестановки.
 * @param array - входной массив.
 * @return отсортированный.
 */
	 public int[] sort(int[] array) {
		for (int i = array.length - 1; i >= 0; i--) {
			for (int j = 0; j < array.length - 1; j++) {
				if (array[j] > array[j + 1]) {
				int temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
					}
				}
			}
			return array;
	 }
}
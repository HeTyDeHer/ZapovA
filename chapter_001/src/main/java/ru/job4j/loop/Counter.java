package ru.job4j;

/**
 * Решение задачи 4.1. Подсчет суммы чётных чисел в диапазоне [#192].
 * Подсчет суммы чётных чисел в диапазоне.
 * @author ZapovA
 * @since 14.9.17
*/
public class Counter {
/**
 * Подсчет суммы чётных чисел в диапазоне.
 * @param start - начало диапазона.
 * @param finish - конец диапазона.
 * @return сумма четных.
 */
	public int add(int start, int finish) {
	int sum = 0;
	for (; start <= finish; start++) {
		if (start % 2 == 0) {
			sum += start;
			}
		}
	return sum;
	}
}
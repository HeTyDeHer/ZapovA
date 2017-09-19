package ru.job4j;

/**
 * Решение задачи 3.1 /001, 3.4/001 Максимум из трех чисел [#190].
 * Нахождение максимума двух/трех чисел.
 * @author ZapovA
 * @since 14.9.17
*/

public class Max {
/**
 * Максимум двух чисел.
 * @param first - первое число.
 * @param second - второе число.
 * @return максимум.
*/
	public int maxOfTwo(int first, int second) {
	return (first > second) ? first : second;
	}
/**
 * Максимум трёх чисел.
 * @param first - первое число.
 * @param second - второе число.
 * @param third - третье число.
 * @return максимум.
*/
	public int maxOfThree(int first, int second, int third) {
	int temp = this.maxOfTwo(first, second);
	return this.maxOfTwo(temp, third);
	}
}
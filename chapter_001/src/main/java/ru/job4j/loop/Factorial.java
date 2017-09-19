package ru.job4j;

/**
 * Решение задачи 4.2. Создать программу вычисляющую факториал. [#193].
 * Вычисление факториала.
 * @author ZapovA
 * @since 14.9.17
*/
public class Factorial {
/**
 * Вычисление факториала.
 * @param n - вычисление факториала для числа n.
 * @return значение  факториала для числа n.
 */
	public long factorial(int n) {
	if (n < 0) {
	return 0;
	} else if (n == 0) {
	return 1;
	}
	long fact = 1;
	for (int i = 1; i <= n; i++) {
		fact *= i;
		}
	return fact;
	}
}
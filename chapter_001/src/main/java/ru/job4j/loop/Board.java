package ru.job4j;

/**
 * Решение задачи 4.3. Построить шахматную доску в псевдографике. [#13559].
 * @author ZapovA
 * @since 14.9.17
*/
public class Board {
/**
 * Рисуем доску.
 * @param wight - ширина доски.
 * @param height - высота доски.
 * @return строка с доской.
 */
	public String paint(int wight, int height) {
	StringBuilder s1 = new StringBuilder();
	StringBuilder s2 = new StringBuilder();
	StringBuilder result = new StringBuilder();
	for (int i = 0; i < wight; i++) {
		if (i % 2 == 0) {
			s1.append('x');
			s2.append(' ');
		} else {
			s2.append('x');
			s1.append(' ');
		}
	}
	for (int i = 0; i < height; i++) {
		if (i % 2 == 0) {
			result.append(s1 + System.getProperty("line.separator"));
		} else {
			result.append(s2 + System.getProperty("line.separator"));
		}
	}
	return result.toString();
	}
}
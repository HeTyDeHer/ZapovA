package ru.job4j.loop;

/**
 * 4.4. Построить пирамиду в псевдографике. [#4412].
 * @author ZapovA
 * @since 14.9.17
*/
public class Paint {
/**
 * Рисуем пирамиду.
 * @param h - высота пирамиды.
 * @return строка с пирамидой.
 */
	public String pyramid(int h) {
	StringBuilder result = new StringBuilder();
	for (int i = 1; h > 0; h--, i += 2) {
		for (int j = h - 1; j > 0; j--) {
			result.append(' ');
			}
		for (int j = i; j > 0; j--) {
			result.append('^');
			}
		result.append(System.getProperty("line.separator"));
		}
	return result.toString();
	}
}
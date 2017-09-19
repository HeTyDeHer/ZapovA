package ru.job4j;

/**
 * 7. Проверка, что одно слово находится в другом слове [#228].
 * @author ZapovA
 * @since 14.9.17
*/
public class DoesItContain {
/**
 * Проверка, что одно слово находится в другом слове.
 * @param origin - исходное слово.
 * @param sub - слово для поиска.
 * @return да/нет.
 */
public boolean contains(String origin, String sub) {
		boolean contain = false;
		if (sub.length() > origin.length()) {
			return contain;
		}
		char[] originArray = origin.toCharArray();
		char[] subArray = sub.toCharArray();
		for (int i = 0; i <= origin.length() - sub.length(); i++) {
			if (originArray[i] == subArray[0]) {
				for (int j = 1; j < sub.length(); j++) {
					contain = true;
					if (originArray[i + j] != subArray[j]) {
						contain = false;
						break;
					}
				}
			}
			if (contain) {
				break;
			}
		}
	return contain;
}
}
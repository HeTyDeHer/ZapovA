package ru.job4j.array;
import java.util.Arrays;

/**
 * 5.3. Удаление дубликатов в массиве. [#225].
 * @author ZapovA
 * @since 14.9.17
*/
public class ArrayDuplicate {
/**
 * Удаление дубликатов.
 * @param array - входной массив.
 * @return без дубликатов.
 */
public String[] remove(String[] array) {
	int index = array.length;
		for (int i = 0; i < index; i++) {
			for (int j = i + 1; j < index; j++) {
			if (array[i].equals(array[j])) {
				array[j] = array[index - 1];
				index--;
				j--;
				}
			}
		}
	return Arrays.copyOf(array, index);
}
}










/*	for (int i = 0; i < array.length - 1; i++){
		for (int j = i + 1; j < array.length; j++){
			if(array[i].equals(array[j])){
				array[j] = "";
				}
			}
		}
	for (int i = array.length - 1; i > 0; i--){
		if (!array[i].equals("")){
			for (int j = 0; j < i; j++){
				if(array[j].equals(""){
					array[j] = array[i];
					}
				}
			}
		}
	int index = 0;
	for (int i = 0; i < array.length; i++) {
		if (array[i].equals("")) {
			index = i - 1;
			break;
			}
		}
	return Arrays.copyOf(array, index);
}
}*/
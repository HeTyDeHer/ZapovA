package ru.job4j;

/**
 * Слияние двух упорядоченных массивов.
 * Created by Алексей on 20.09.2017.
 */
public class SortedArraysConjuction {
  /**
   * Слияние двух упорядоченных массивов.
   * @param array1 первый упорядоченный массив.
   * @param array2 второй упорядоченный массив.
   * @return один упорядоченный массив.
   */
    public int[] conjuct(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        int j = 0, i = 0, index = 0;
        while (i < array1.length && j < array2.length) {
            result[index++] = array1[i] < array2 [j] ? array1[i++] : array2[j++];
        }
        if (i == array1.length) {
            for (; j < array2.length; j++) {
                result[index++] = array2[j];
            }
        } else if (j == array2.length) {
            for (; i < array1.length; i++) {
                result[index++] = array1[i];
            }
        }
        return result;
    }
}

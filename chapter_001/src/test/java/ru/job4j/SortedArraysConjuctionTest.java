package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест слияния двух упорядоченных массивов.
 * Created by Алексей on 20.09.2017.
 */

public class SortedArraysConjuctionTest {
   /**
   *  Тестирование слияния двух упорядоченных массивов в один упорядоченный массив.
    */
    @Test
    public void twoSortedArraysConjuction() {
        Random random = new Random();
        int[] array1 = new int[random.nextInt(21)];
        int[] array2 = new int[random.nextInt(21)];
        for (int i = 0; i < array1.length; i++) {
            array1[i] = random.nextInt(201) - 100;
        }
        for (int i = 0; i < array2.length; i++) {
            array2[i] =  random.nextInt(201) - 100;
        }
        Arrays.sort(array1);
        Arrays.sort(array2);
        int[] result = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        Arrays.sort(result);
        SortedArraysConjuction conjuction = new SortedArraysConjuction();
        int[] expected = conjuction.conjuct(array1,array2);
        assertThat(result,is(expected));
    }
}

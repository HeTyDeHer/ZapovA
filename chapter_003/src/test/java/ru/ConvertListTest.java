package ru;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Convert list.
 * Created by Алексей on 09.10.2017.
 */
public class ConvertListTest {
    /**
     * Тест Array в List.
     */
    @Test
    public void convertArrayToList() {
        ConvertList convert = new ConvertList();
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> result = convert.toList(array);
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertThat(result, is(expected));
    }
    /**
     * Тест List в Array.
     */
    @Test
    public void convertListToArray() {
        ConvertList convert = new ConvertList();
        List<Integer> toConvert = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, null, 7));
        int[][] result = convert.toArray(toConvert, 2);
        int[][] expected = new int[][]{{1, 2, 3, 4}, {5, 0, 7, 0}};
        assertThat(result, is(expected));
    }
    /**
     * Тест List в Array.
     */
    @Test
    public void convertListOfArraysToOneList() {
        ConvertList convert = new ConvertList();
        List<int[]> toConvert = new ArrayList<>(Arrays.asList(new int[] {1, 2}, new int[] {3, 4, 5}, new int[3]));
        List<Integer> result = convert.convert(toConvert);
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 0, 0, 0));
        assertThat(result, is(expected));
    }

}
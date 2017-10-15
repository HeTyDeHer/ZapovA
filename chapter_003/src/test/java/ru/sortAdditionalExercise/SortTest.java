package ru.sortAdditionalExercise;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тестирование задачи: Реализовать возможность сортировки массива кодов подразделений по возрастанию и убыванию,
 * при которых сохранялась бы иерархическая структура (показано далее в примерах сортировки),
 * т.к. отсортированный массив используется для отображения категорий пользователю.
 * Created by Алексей on 15.10.2017.
 */
public class SortTest {
    /**
     * Тест сортировки по возрастранию.
     */
    @Test
    public void sortUpscTest() {
        Sort sort = new Sort();
        String s1 = "K1/SK1";
        String s2 = "K1/SK2";
        String s4 = "K1/SK1/SSK1";
        String s5 = "K1/SK1/SSK2";
        String s6 = "K2";
        String s7 = "K2/SK1/SSK1";
        String s8 = "K2/SK1/SSK2";
        ArrayList<String> result = sort.sortUpsc(new ArrayList<>(Arrays.asList(s1, s2, s4, s5, s6, s7, s8)));
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("K1", s1, s4, s5, s2, s6, "K2/SK1", s7, s8));
        assertThat(result, is(expected));
    }
    /**
     * Тест сортировки по убыванию.
     */
    @Test
    public void sortDescTest() {
        Sort sort = new Sort();
        String s1 = "K1/SK1";
        String s2 = "K1/SK2";
        String s4 = "K1/SK1/SSK1";
        String s5 = "K1/SK1/SSK2";
        String s6 = "K2";
        String s7 = "K2/SK1/SSK1";
        String s8 = "K2/SK1/SSK2";
        ArrayList<String> result = sort.sortDesc(new ArrayList<>(Arrays.asList(s1, s2, s4, s5, s6, s7, s8)));
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(s6, "K2/SK1", s8, s7, "K1", s2, s1, s5, s4));
        assertThat(result, is(expected));
    }
}

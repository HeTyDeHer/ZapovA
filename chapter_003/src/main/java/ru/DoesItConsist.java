package ru;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Проверка, состоит ли слово 2 только из букв слова 1.
 * Created by Алексей on 19.10.2017.
 */
public class DoesItConsist {
    /**
     * Проверка, состоит ли слово 2 только из букв слова 1.
     * @param s1 первое слово.
     * @param s2 второе слово.
     * @return true/false.
     */
    public static boolean anagramCheck(String s1, String s2) {
        if (s1.length() < s2.length()) {
            return false;
        }

        boolean result = true;
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        if (s1.equals(s2)) {
            return true;
        }

        LinkedList<String> list1 = new LinkedList<>(Arrays.asList(s1.split("")));
        LinkedList<String> list2 = new LinkedList<>(Arrays.asList(s2.split("")));
        for (String s : list2) {
            if (!list1.remove(s)) {
                result = false;
                break;
            }
        }
        return result;
    }

}

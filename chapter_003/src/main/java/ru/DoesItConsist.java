package ru;

import java.util.HashMap;

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
    public static boolean check(String s1, String s2) {
        if (s1.length() < s2.length()) {
            return false;
        }

        boolean result = true;
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        if (s1.equals(s2)) {
            return true;
        }
        HashMap<Character, Integer> hash = new HashMap<>();

        for (Character ch : s1.toCharArray()) {
            if (hash.containsKey(ch)) {
                int value = hash.get(ch);
                hash.put(ch, ++value);
            } else {
                hash.put(ch, 1);
            }
        }
        for (Character ch : s2.toCharArray()) {
            if (!hash.containsKey(ch)) {
                result = false;
                break;
            }
            int value = hash.get(ch);
            if (value == 0) {
                result = false;
                break;
            }
            hash.put(ch, --value);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(DoesItConsist.check("12345", "135"));
        System.out.println(DoesItConsist.check("Поездка", "поезда"));
        System.out.println(DoesItConsist.check("Поездка", "узда"));
    }

}

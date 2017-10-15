package ru.sortAdditionalExercise;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Реализовать возможность сортировки массива кодов подразделений по возрастанию и убыванию,
 * при которых сохранялась бы иерархическая структура (показано далее в примерах сортировки),
 * т.к. отсортированный массив используется для отображения категорий пользователю.
 * Created by Алексей on 15.10.2017.
 */
public class Sort {
    /**
     * Добавление недостающих элементов.
     * @param list входной лист.
     * @return с элементами.
     */
    private static ArrayList<String> addElements(ArrayList<String> list) {
        HashSet<String> hash = new HashSet<>();
        for (String s : list) {
            int pos = s.lastIndexOf('/');
            if (pos != -1 && !list.contains(s.substring(0, pos))) {
                hash.add(s.substring(0, pos));
            }
        }
        list.addAll(hash);
        return list;
    }

    /**
     * Сортировка по возрастанию.
     * @param list входной лист.
     * @return отсортированный.
     */
    public ArrayList<String> sortUpsc(ArrayList<String> list) {
        list = Sort.addElements(list);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return list;
    }

    /**
     * Сортировка по убыванию.
     * @param list входной лист.
     * @return отсортированный.
     */
    public ArrayList<String> sortDesc(ArrayList<String> list) {
        list = Sort.addElements(list);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o2.substring(0, 2).compareTo(o1.substring(0, 2)) != 0) {
                    return o2.substring(0, 2).compareTo(o1.substring(0, 2));
                }
                if (o2.length() == o1.length()) {
                    return o2.compareTo(o1);
                }
                return o2.length() > o1.length() ? -1 : 1;
            }
        });
        return list;
    }
}

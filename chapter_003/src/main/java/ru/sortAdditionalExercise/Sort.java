package ru.sortAdditionalExercise;


import java.util.*;

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
        HashSet<String> hash = new HashSet<>(list);
        for (String s : list) {
            int pos = s.lastIndexOf('/');
            if (pos != -1) {
                hash.add(s.substring(0, pos));
            }
        }
        list.clear();
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
                int result = 0;
                int i = 0;
                String[] s1 = o1.split("/");
                String[] s2 = o2.split("/");
                if (o1.length() != o2.length() && s2[i].compareTo(s1[i]) == 0)  {
                    result = o1.length() - o2.length();
                } else {
                    while (result == 0 && i < s1.length && i < s2.length) {
                        result = s2[i].compareTo(s1[i]);
                        i++;
                    }
                }
                return result;
            }
        });
        return list;
    }

    public void sortDescThroughtSet(List<String> list) {
        Set<List<String>> set = new TreeSet<>(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                int i = 0;
                int result = 0;
                if (o1.size() != o2.size() && o2.get(i).compareTo(o1.get(i)) == 0)  {
                    result = o1.size() - o2.size();
                } else {
                    while (i < o1.size() && i < o2.size()) {
                        result = o2.get(i).compareTo(o1.get(i));
                        if (result != 0) {
                            break;
                        }
                        i++;
                    }
                }
                return result;
            }
        });

        for (String s : list) {
            int pos = s.lastIndexOf('/');
            if (pos != -1) {
                set.add(Arrays.asList(s.substring(0, pos).split("/")));
            }
            set.add(new ArrayList<>(Arrays.asList(s.split("/"))));
        }
    }

}

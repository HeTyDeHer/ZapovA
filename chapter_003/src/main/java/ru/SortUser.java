package ru;

import java.util.Comparator;
import java.util.List;

/**
 * Сортировка User.
 * Created by Алексей on 10.10.2017.
 */
class SortUser {

    /**
     * Сортировка User по длине имени.
     * @param list несортированный List.
     * @return Сортированный List.
     */
    public List<User> sortNameLength(List<User> list) {
        list.sort(nameLengthComparator);
        return list;
    }

    /**
     * Сортировка User по имени, если имена равны - по возрасту.
     * @param list несортированный List.
     * @return Сортированный List.
     */
    public List<User> sortByAllFields(List<User> list) {
        list.sort(sortByNameThenSortByAgeComparator);
        return list;
    }

    /**
     * Comparator User по длине имени.
     */
    private static Comparator<User> nameLengthComparator = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().length() - o2.getName().length();
        }
    };
    /**
     * Comparator User по имени, есл иимена равны по возрасту.
     */
    private static Comparator<User> sortByNameThenSortByAgeComparator = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().compareTo(o2.getName()) != 0
                    ? o1.getName().compareTo(o2.getName()) : o1.getAge() - o2.getAge();
        }
    };
}

package ru;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Сортировка User.
 * Created by Алексей on 10.10.2017.
 */
public class SortUser {
    /**
     * Сортировка Comparable User-ов.
     * @param list несортированный List.
     * @return Сортированный set.
     */
    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
}

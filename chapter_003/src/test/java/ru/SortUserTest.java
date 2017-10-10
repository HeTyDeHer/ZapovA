package ru;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест сравнения Comparable User.
 * Created by Алексей on 10.10.2017.
 */
public class SortUserTest {
    /**
     * Сравнение по длине имени.
     */
    @Test
    public void compareByNameLengthTest() {
        SortUser sort = new SortUser();
        User user1 = new User("Вася", 10);
        User user2 = new User("Анатолий", 101);
        User user3 = new User("Алексей", 100);
        List<User> toSort = new ArrayList<>(Arrays.asList(user1, user2, user3));
        List<User> result = sort.sortNameLength(toSort);
        List<User> expected = new ArrayList<>(Arrays.asList(user1, user3, user2));
        assertThat(result, is(expected));
    }
    /**
     * Сравнение по длине имени.
     */
    @Test
    public void compareByNameThenByAge() {
        SortUser sort = new SortUser();
        User user1 = new User("Вася", 10);
        User user2 = new User("Анатолий", 101);
        User user3 = new User("Алексей", 100);
        User user4 = new User("Вася", 1);
        List<User> toSort = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));
        List<User> result = sort.sortByAllFields(toSort);
        List<User> expected = new ArrayList<>(Arrays.asList(user3, user2, user4, user1));
        assertThat(result, is(expected));
    }
}

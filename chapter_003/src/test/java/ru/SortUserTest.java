package ru;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест сравнения Comparable User.
 * Created by Алексей on 10.10.2017.
 */
public class SortUserTest {
    /**
     * Сравнение по возрасту.
     */
    @Test
    public void compareTest() {
        User user1 = new User("Вася", 10);
        User user2 = new User("Петя", 100);
        User user3 = new User("Анатолий", 100);
        assertThat(user1.compareTo(user2), is(-1));
        assertThat(user2.compareTo(user3), is(0));
        assertThat(user3.compareTo(user1), is(1));
    }
}

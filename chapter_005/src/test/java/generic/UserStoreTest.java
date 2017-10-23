package generic;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Тест 5.2.2. Реализовать Store<T extends Base> [#157].
 * Created by Алексей on 22.10.2017.
 */
public class UserStoreTest {
    /** Тест UserStore.
     * Добавляем, обновляем, удаляем.
     */
    @Test
    public void addUpdateAndDeleteUsers() {
        User user1 = new User("1", "Вася1");
        User user2 = new User("2", "Вася2");
        User user3 = new User("3", "Вася3");
        User user4 = new User("2", "Вася4");
        UserStore<User> userStore = new UserStore<>(5);
        assertThat(userStore.add(user1), is(user1));
        userStore.add(user2);
        userStore.add(user3);
        assertThat(userStore.update(user4), is(user2));
        assertThat(userStore.delete("2"), is(true));
    }
}
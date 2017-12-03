package monitorsynch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

/**
 * 2. Класс хранилища пользователей UserStorage [#1104].
 * Created by Алексей on 24.11.2017.
 */

@ThreadSafe
public class UserStorage {
    private HashMap<Integer, User> users = new HashMap<>();

    @GuardedBy("this")
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    @GuardedBy("this")
    public synchronized boolean update(User user) {
        boolean contains = users.containsKey(user.getId());
        if (contains) {
            users.put(user.getId(), user);
        }
        return contains;
    }

    @GuardedBy("this")
    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @GuardedBy("this")
    public synchronized boolean transfer (int fromId, int toId, int amount) {
        User from = users.get(fromId);
        User to = users.get(toId);
        if (from == null || to == null) {
            return false;
        }
        if(users.get(fromId).getAmount() < amount) {
            return false;
        }
        users.get(fromId).addAmount(- amount);
        users.get(toId).addAmount(amount);
        return true;
    }
}

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

    public boolean add(User user) {
        synchronized (users) {
            return users.putIfAbsent(user.getId(), user) == null;
        }
    }

    public boolean update(User user) {
        boolean contains = users.containsKey(user.getId());
        if (contains) {
            users.put(user.getId(), user);
        }
        return contains;
    }

    public boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @GuardedBy("!!! Разобраться что сюда писать !!! ")
    public boolean transfer (int fromId, int toId, int amount) {
        User from = users.get(fromId);
        User to = users.get(toId);
        if (from == null || to == null) {
            return false;
        }
        synchronized (from) {
            synchronized (to) {
                if(users.get(fromId).getAmount() < amount) {
                    return false;
                }
                users.get(fromId).addAmount(- amount);
                users.get(toId).addAmount(amount);
            }
        }
        return true;
    }
}

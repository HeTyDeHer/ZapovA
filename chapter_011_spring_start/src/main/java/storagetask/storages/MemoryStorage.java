package storagetask.storages;

import storagetask.models.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 2. Реализовать различные виды хранилищ. [#1070]
 *      1. Реализовать хранилище MemoryStorage.
 */

/*@Component*/
public class MemoryStorage implements Storage<Integer, User> {

    private final Map<Integer, User> storage = new HashMap<>();
    private int nextId = 0;

    @Override
    public Integer create(User model) {
        int result = -1;
        if (model.getId() == 0) {
            model.setId(nextId);
            storage.put(model.getId(), model);
            result = nextId++;
        }
        return result;
    }

    @Override
    public User read(Integer id) {
        return storage.get(id);
    }

    @Override
    public void update(User model) {
        storage.put(model.getId(), model);
    }

    @Override
    public void delete(Integer id) {
        storage.remove(id);
    }

    @Override
    public Collection<User> readAll() {
        return storage.values();
    }

    @Override
    public void cleardb() {
        storage.clear();
    }
}

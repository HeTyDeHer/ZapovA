package storagetask.storages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storagetask.models.User;

import java.util.Collection;

/**
 *  2. Реализовать различные виды хранилищ. [#1070]
 *         ...
 *         3. Сделать класс ImportUser для запуска приложения. Приложение должно позволять добавляй пользователей в базу данных.
 */

@Service
public class ImportUser {

    private static final Logger logger = LoggerFactory.getLogger(ImportUser.class);
    private final Storage<Integer, User> storage;

    @Autowired
    public ImportUser(Storage<Integer, User> storage) {
        this.storage = storage;
    }

    @Transactional
    public int create(User user) {
        return this.storage.create(user);
    }

    @Transactional
    public User read(int id) {
        return this.storage.read(id);
    }

    @Transactional
    public void update(User user) {
        this.storage.update(user);
    }

    @Transactional
    public void delete(int id) {
        this.storage.delete(id);
    }

    @Transactional
    public Collection<User> readAll() {
        return this.storage.readAll();
    }

    @Transactional
    public void cleardb() {
        this.storage.cleardb();
    }

}

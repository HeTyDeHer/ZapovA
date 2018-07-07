package storagetask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import storagetask.config.HibernateTestConfig;
import storagetask.models.User;
import storagetask.storages.ImportUser;

import static org.junit.Assert.*;

class ImportUserTest {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(HibernateTestConfig.class);
        context.scan("storagetask");
        context.refresh();
    }

    @AfterEach
    void clean() {
        context.getBean(ImportUser.class).cleardb();
    }

    @Test
    void addAndRead() {
        ImportUser us = context.getBean(ImportUser.class);
        int id = us.create(new User("test"));
        assertNotNull(us.read(id));
    }

    @Test
    void addAndDelete() {
        ImportUser us = context.getBean(ImportUser.class);
        int id = us.create(new User("test"));
        assertNotNull(us.read(id));
        us.delete(id);
        assertNull(us.read(id));
    }

    @Test
    void addMultipleAndReadAll() {
        ImportUser us = context.getBean(ImportUser.class);
        us.create(new User("test"));
        us.create(new User("test1"));
        assertEquals(2, us.readAll().size());
    }
}
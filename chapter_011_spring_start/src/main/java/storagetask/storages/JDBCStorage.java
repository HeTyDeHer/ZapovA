package storagetask.storages;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import storagetask.models.User;

import java.util.Collection;

/**
 * 2. Реализовать различные виды хранилищ. [#1070]
 *      ...
 *      2. Реализовать хранилище JdbcStorage. В хранилище нужно передавать настройки.
 */

@Repository
public class JDBCStorage implements Storage<Integer, User> {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCStorage.class);

    @Autowired
    public JDBCStorage(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(User model) {
        return (Integer) sessionFactory.getCurrentSession().save(model);
    }

    @Override
    public User read(Integer id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void update(User model) {
        sessionFactory.getCurrentSession().update(model);
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> readAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM User").getResultList();
    }

    @Override
    public void cleardb() {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM User");
        query.executeUpdate();
    }
}

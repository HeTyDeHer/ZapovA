package cars.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Class connection to database (by hibernate).
 */
public final class DBConnection {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    /**
     * Get session.
     * @return session.
     */
    static Session getSession() {
        return SESSION_FACTORY.openSession();
    }

    private DBConnection() {
    }

    /**
     * Close session factory.
     */
    public static void close() {
        SESSION_FACTORY.close();
    }
}

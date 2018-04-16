package cars.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Реализовать площадку продаж машин. [#4747].
 * DAO implementation.
 * @param <PK> primary key.
 * @param <T> entity.
 */
public class HibernateDAO<PK extends Serializable, T> implements AbstractDAO<PK, T> {

    @Override
    public PK create(T entity) {
        PK id;
        try (Session session =  DBConnection.getSession()) {
            Transaction tx = session.beginTransaction();
            id = (PK) session.save(entity);
            tx.commit();
        }
        return id;
    }

    @Override
    public void update(T entity) {
        try (Session session =  DBConnection.getSession()) {
            Transaction tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session =  DBConnection.getSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> read(Class<T> tClass, Map<String, Object> params) {
        List<T> result;
        StringBuilder sb = new StringBuilder("from ");
        sb.append(tClass.getSimpleName());
        String hQuery = sb.toString();
        if (!params.isEmpty()) {
            sb.append(" where ");
            for (String s : params.keySet()) {
                sb.append(s);
                sb.append(" = :");
                sb.append(s);
                sb.append(" and ");
            }
            hQuery = sb.substring(0, sb.length() - 5);
        }

        try (Session session =  DBConnection.getSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(hQuery, tClass);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            result = query.getResultList();
            tx.commit();
        }
        return result;
    }

}

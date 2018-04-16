package cars.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Реализовать площадку продаж машин. [#4747].
 * DAO.
 * @param <PK> primary key.
 * @param <T> entity.
 */
public interface AbstractDAO<PK extends Serializable, T> {
    PK create(T entity);
    List<T> read(Class<T> tClass, Map<String, Object> params);
    void update(T entity);
    void delete(T entity);

}

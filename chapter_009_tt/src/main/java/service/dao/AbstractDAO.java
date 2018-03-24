package service.dao;

/**
 * Музыкальная прощадка [#3236].
 * Abstract modelDAO
 * @param <T> model.
 */
public interface AbstractDAO<T, K> {

    int create(T model);
    T read(K key);
    boolean update(T model);
    void delete(int id);

}

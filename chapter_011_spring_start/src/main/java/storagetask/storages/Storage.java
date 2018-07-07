package storagetask.storages;

import java.util.Collection;

/**
 * 2. Реализовать различные виды хранилищ. [#1070]
 * @param <PK> primary key.
 * @param <E> Entity.
 */

public interface Storage<PK, E> {
    PK create(E model);
    E read(PK id);
    void update(E model);
    void delete(PK id);
    Collection<E> readAll();
    void cleardb();
}

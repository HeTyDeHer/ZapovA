package iterators;

import java.util.Iterator;


/**
 * 5.1.4. Создать convert(Iterator<Iterator>) [#152].
 * Created by Алексей on 20.10.2017.
 */
public class IteratorConverter {
    /**
     * Возвращаем новый итератор по значениям всех итераторов.
     * @param it итератор итераторов.
     * @return новый итератор по значением всех итераторов.
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new IteratorOfIterators<>(it);

    }
}

/**
 * Итератор по знчениям итераторов.
 * @param <T> значения.
 */
class IteratorOfIterators<T> implements Iterator {
    /** Внешний итератор по итераторам. */
    private final Iterator<Iterator<T>> mainIterator;
    /** Внутренний, по значениям. */
    private Iterator<T> iterator;

    /**
     * Конструктор.
     * @param iterator итеравтор.
     */
    IteratorOfIterators(Iterator<Iterator<T>> iterator) {
        this.mainIterator = iterator;
    }

    /**
     * Выбираем текущий итератор и от него получаем hasnext.
     * @return есть ли следующий элемент.
     */
    @Override
    public boolean hasNext() {
        chooseIterator();
        return iterator.hasNext();
    }

    /**
     * Выбираем текущий итератор и от него получаем next.
     * @return следующий элемент. Если нет - ошибка NoSuchElementException.
     */
    @Override
    public Object next() {
        chooseIterator();
        return iterator.next();
    }

    /**
     * Выбираем итератор.
     */
    private void chooseIterator() {
        /* Если текущий итератор уже выбран и в нем есть следующий элемент, не делаем ничего... */
        if (iterator != null && iterator.hasNext()) {
            return;
        }
        /* ... а если нет, то берем следующий итератор. Если следующего итератора нет, оставляем все как есть. */
        if (mainIterator.hasNext()) {
            iterator = mainIterator.next();
        }
    }
}


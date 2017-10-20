package iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 5.1.1. Итератор для двухмерного массива int[][] [#9539].
 * Created by Алексей on 20.10.2017.
 */
public class IteratorTwoDimentionArray implements Iterator {
    /** Массив для итерации. */
    private final Integer[][] values;
    /** Индекс строки. */
    private int indexI = 0;
    /** индекс столбца. */
    private int indexJ = 0;

    /**
     * Конструктор.
     * @param values массив для итерации.
     */
    public IteratorTwoDimentionArray(Integer[][] values) {
        this.values = values;
    }

    /**
     * есть ли следующий элемент?
     * @return да/нет.
     */
    @Override
    public boolean hasNext() {
        return indexI != values.length;
    }

    /**
     * Получаем следующий элемент.
     * @return Следующий элемент.
     */
    @Override
    public Object next() {
        /* Если нет элемента, бросаем NoSuchElementException(). */
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        /* Если есть, получаем его... */
        int result = values[indexI][indexJ];
        /* ... и двигаем каретку. */
        if (indexJ < values[indexI].length - 1) {
            indexJ++;
        } else {
            indexJ = 0;
            indexI++;
        }
        return result;
    }
}

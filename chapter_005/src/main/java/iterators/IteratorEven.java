package iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 5.1.2. Создать итератор четные числа [#150].
 * Created by Алексей on 20.10.2017.
 */
public class IteratorEven implements Iterator {
    /** Массив для итерации. */
    private final int[] values;
    /** Индекс в массиве. */
    private int indexI = 0;

    /**
     * Конструктор.
     * @param values массив для итерации.
     */
    public IteratorEven(int[] values) {
        this.values = values;
    }

    /**
     * есть ли следующий элемент c четным значением?
     * @return да/нет.
     */
    @Override
    public boolean hasNext() {
        for (int i = indexI; i < values.length; i++) {
            if (values[i] % 2 == 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * Получаем следующий элемент c четным значением или бросаем NoSuchElementException.
     * @return Следующий элемент c четным значением.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        while (values[indexI] % 2 != 0) {
            indexI++;
        }

        return values[indexI++];
    }
}

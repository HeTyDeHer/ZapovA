package iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 5.1.3. Создать итератор простые числа. [#151].
 * Created by Алексей on 20.10.2017.
 */
public class PrimeIt implements Iterator {
    /** Массив для итерации. */
    private final int[] values;
    /** Индекс в массиве. */
    private int indexI = 0;

    /**
     * Конструктор.
     * @param values массив для итерации.
     */
    public PrimeIt(int[] values) {
        this.values = values;
    }

    /**
     * Проверяем, есть ли еще простые числа.
     * @return да/нет.
     */
    private boolean hasNextPrime() {
        boolean isPrime = false;
        for (; indexI < values.length; indexI++) {
            isPrime = true;
            for (int j = 2; j * j <= values[indexI]; j++) {
                if (values[indexI] % j == 0) {
                    isPrime = false;
                    break;
                }

            }
            if (isPrime && values[indexI] != 1) {
                break;
            }
        }
        return isPrime;
    }
    /**
     * есть ли следующий элемент c простым значением?
     * @return да/нет.
     */
    @Override
    public boolean hasNext() {
        boolean result = false;
        if (indexI < values.length) {
            result = hasNextPrime();
        }
        return result;
    }

    /**
     * Получаем следующий элемент c простым значением или бросаем NoSuchElementException.
     * @return Следующий элемент c простым значением.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return values[indexI++];
    }
}

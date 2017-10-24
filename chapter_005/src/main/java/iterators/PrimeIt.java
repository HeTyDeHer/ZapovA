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
     * Проверяем, простое ли число.
     * @return да/нет.
     */
    private boolean isPrime(int value) {
        if (value == 1) {
            return false;
        }
        boolean isPrime = true;
        for (int j = 2; j * j <= value; j++) {
            if (values[indexI] % j == 0) {
                isPrime = false;
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
        while (indexI < values.length) {
            if (isPrime(values[indexI])) {
                result = true;
                break;
            }
            indexI++;
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

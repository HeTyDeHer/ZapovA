package ru;

import java.util.ArrayList;
import java.util.List;

/**
 * 3. Конвертация двумерного массива в ArrayList и наоборот [#10035].
 * Created by Алексей on 09.10.2017.
 */
public class ConvertList {
    /**
     * Конвертирует Array в ArrayList.
     * @param array входной Array.
     * @return Получившийся List.
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> result = new ArrayList<>();
        for (int[] ints : array) {
            for (int i : ints) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Преоюразование list в array с заданным числом рядов. Недостающие элеиенты заменя.тся нулями.
     * @param list входной лист.
     * @param rows необходимое число рядов.
     * @return результат преобразования.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int z = 0;
        int columns = (int) Math.ceil((double) list.size() / rows);
        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (z < list.size()) {
                    try {
                        result[i][j] = list.get(z++);
                    } catch (NullPointerException e) {
                        result[i][j] = 0;
                    }
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }

    /**
     * Преобразование List массивов в один list.
     * @param list List с массивами.
     * @return итоговый List.
     */
    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] ints : list) {
            for (int i : ints) {
                result.add(i);
            }
        }
        return result;
    }

}

package ru;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * 2. Тестирование производительности коллекций. [#10022].
 * Написать программу, которая замеряет время вставки в коллекцию большого количества случайных строк и удаления в коллекции первых n элементов для:
 * LinkedList
 * ArrayList
 * TreeSet
 * Created by Алексей on 07.10.2017.
 */

public class TimeCheck {
    /** Число повторов. */
    private int amount;
    /** Испытуемый. */
    private Collection collection;

    /**
     * КОнструктор.
     * @param amount Число повторов.
     * @param collection Испытуемый.

     */
    public TimeCheck(Collection collection, int amount) {
        this.amount = amount;
        this.collection = collection;
    }

    /**
     * Тестирование времени добавления в коллекцию.
     * @param collection коллекция.
     * @param amount число добавляемых элементов.
     * @return время на операцию, в мс.
     */

    public long add(Collection<String> collection, int amount) {
        long startTime = 0;
        long endTime = 0;
            startTime = System.currentTimeMillis();
            for (int j = 0; j < amount; j++) {
                collection.add(String.valueOf(j));
            }
            endTime = System.currentTimeMillis();
 //       }
        return (endTime - startTime);
    }

    /**
     * Тестирование времени удаления по значению элементов из коллекции.
     * @param collection коллекция.
     * @param amount число удаляемых элементов.
     * @return время на операцию в мс.
     */
    public long delete(Collection<String> collection, int amount) {
        long startTime = 0;
        long endTime = 0;
            startTime = System.currentTimeMillis();
            for (int j = 0; j < amount; j++) {
                collection.remove(String.valueOf(j));
            }
            endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }

    /**
     * Вывод результатов тестирования.
     */
    public void printResults() {
        long addResult = this.add(this.collection, this.amount);
        long removeResult = this.delete(this.collection, this.amount);
        System.out.printf("%s add result: %d ms, remove result: %d ms%n", this.collection.getClass().getSimpleName(), addResult, removeResult);
    }

    /**
     * Main.
     * @param args .
     */
    public static void main(String[] args) {
        TimeCheck linkedListTest = new TimeCheck(new LinkedList<String>(), 100000);
        TimeCheck arrayListTest = new TimeCheck(new ArrayList<String>(), 100000);
        TimeCheck treeSet = new TimeCheck(new TreeSet<String>(), 100000);
        linkedListTest.printResults();
        arrayListTest.printResults();
        treeSet.printResults();
    }

}

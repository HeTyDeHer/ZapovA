package ru.start.strategy;

/**
 * Реализация квадрат.
 * Created by Алексей on 25.09.2017.
 */
public class Square implements Shape {
    @Override
    public String pic() {
        return "ooo" + System.getProperty("line.separator") + "o o" + System.getProperty("line.separator") + "ooo";
    }
}

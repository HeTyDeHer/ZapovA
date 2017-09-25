package ru.start.strategy;

/**
 * Реализация треугольник.
 * Created by Алексей on 25.09.2017.
 */
public class Triangle implements Shape {
    @Override
    public String pic() {
        return "  o" + System.getProperty("line.separator") + " ooo" + System.getProperty("line.separator") + "ooooo";
    }
}

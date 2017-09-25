package ru.start.strategy;

/**
 * Рисуем форму.
 * Created by Алексей on 25.09.2017.
 */
public class Paint {
    /**
     * Рисуем форму.
     * @param shape форма.
     */

    public void draw(Shape shape) {
        System.out.printf(shape.pic());
    }
}

package ru.start.strategy;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Тест квадрат/треугольник.
 * Created by Алексей on 25.09.2017.
 */
public class PaintTest {
    /** Тест квадрат. */
    @Test
    public void squareTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Paint paint = new Paint();
        paint.draw(new Square());
        assertEquals("ooo" + System.getProperty("line.separator") + "o o" + System.getProperty("line.separator") + "ooo", outContent.toString());
    }
    /** Тест треугольник. */
    @Test
    public void triangleTest() {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Paint paint = new Paint();
        paint.draw(new Triangle());
        assertEquals("  o" + System.getProperty("line.separator") + " ooo" + System.getProperty("line.separator") + "ooooo", outContent.toString());
    }
}

package ru.job4j;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
/**
 * Тест задачи 3.3 /001 [#9461].
 * Вычисление площади треугольника.
 * @author ZapovA
 * @since 14.9.17
*/
public class TriangleTest {

 /**
 * Тест задачи 3.3 /001 [#9461].
 * Вычисление площади треугольника, возможный треугольник.
*/
@Test
	public void whenAreaSetThreePointsThenTriangleArea() {
    Point a = new Point(0, 0);
    Point b = new Point(0, 2);
    Point c = new Point(2, 0);
    Triangle triangle = new Triangle(a, b, c);
    double result = triangle.area();
    double expected = 2D;
    assertThat(result, closeTo(expected, 0.1));
	}
 /**
 * Тест задачи 3.3 /001 [#9461].
 * Вычисление площади треугольника, невозможный треугольник.
*/
@Test
	public void whenWrongTriangle() {
    Point a = new Point(0, 0);
    Point b = new Point(0, 0);
    Point c = new Point(0, 0);
    Triangle triangle = new Triangle(a, b, c);
    double result = triangle.area();
	double expected = -1d;
    assertThat(result, closeTo(expected, 0.1));
	}
}
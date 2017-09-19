package ru.job4j;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
/**
 * Тест задачи 3.2 /001 [#188].
 * Положение точки.
 * @author ZapovA
 * @since 14.9.17
*/

public class PointTest {
	/**
	* Тест, на функции.
	*/
@Test
	public void onFunc() {
	Point point = new Point(1, 1);
	boolean onfunc = point.isPointOnFunc(1, 0);
	assertThat(onfunc, is(true));
	}
	/**
	* Тест, не на функции.
	*/
@Test
	public void notOnFunc() {
	Point point = new Point(1, 1);
	boolean onfunc = point.isPointOnFunc(0, 0);
	assertThat(onfunc, is(false));
	}
}
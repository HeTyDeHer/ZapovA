package ru.job4j.loop;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * 4.4. Построить пирамиду в псевдографике. [#4412].
 * @author ZapovA
 * @since 14.9.17
*/
public class PaintTest {
/**
* Высота 5.
*/
@Test
	public void heightFive() {
		Paint paint = new Paint();
		String result = paint.pyramid(5);
		String expect = String.format("    ^%n   ^^^%n  ^^^^^%n ^^^^^^^%n^^^^^^^^^%n");
		assertThat(result, is(expect));
		}
/**
* Высота 3.
*/
@Test
	public void heightThree() {
		Paint paint = new Paint();
		String result = paint.pyramid(3);
		String expect = String.format("  ^%n ^^^%n^^^^^%n");
		assertThat(result, is(expect));
		}
}
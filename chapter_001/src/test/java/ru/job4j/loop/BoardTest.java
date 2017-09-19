package ru.job4j;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест задачи 4.3. Построить шахматную доску в псевдографике. [#13559].
 * @author ZapovA
 * @since 14.9.17
*/
public class BoardTest {
/**
* Тест 4х4.
*/
@Test
	public void fourFour() {
	Board board = new Board();
	String result = board.paint(4, 4);
	String expected = String.format("x x %n x x%nx x %n x x%n");
	assertThat(result, is(expected));
	}

/**
* Тест 5х7.
*/
@Test
	public void eightSeven() {
	Board board = new Board();
	String result = board.paint(5, 7);
	String expected = String.format("x x x%n x x %nx x x%n x x %nx x x%n x x %nx x x%n");
	assertThat(result, is(expected));
	}
}
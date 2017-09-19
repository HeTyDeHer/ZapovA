package ru.job4j;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест 7. Проверка, что одно слово находится в другом слове [#228].
 * @author ZapovA
 * @since 14.9.17
*/
public class DoesItContainTest {
/**
 * Тест, слово содержится в начале.
*/
 @Test
public void containsBeginning() {
	DoesItContain cont = new DoesItContain();
	boolean result = cont.contains("Тестирование", "Тест");
	assertThat(result, is(true));
	}
/**
 * Тест, слово содержится в конце.
*/
 @Test
public void containsEnding() {
	DoesItContain cont = new DoesItContain();
	boolean result = cont.contains("Тестирование", "ние");
	assertThat(result, is(true));
	}

/**
 * Тест, слово не содержится.
*/
 @Test
public void doesNotContains() {
	DoesItContain cont = new DoesItContain();
	boolean result = cont.contains("Тестированваие", "вал");
	assertThat(result, is(false));
	}
}
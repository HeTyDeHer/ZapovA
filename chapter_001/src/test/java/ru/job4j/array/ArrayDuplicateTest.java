package ru.job4j.array;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест 5.3. Удаление дубликатов в массиве. [#225].
 * @author ZapovA
 * @since 14.9.17
*/
public class ArrayDuplicateTest {
/**
 * Тест.
*/
 @Test
 public void doesItRemoveDuplicates() {
	ArrayDuplicate duplicate = new ArrayDuplicate();
	String[] array = new String[] {"мама", "мыла", "мама", "мама", "мыла", "раму", "мама", "раму"};
	String[] result = duplicate.remove(array);
	String[] expected = new String[] {"мама", "мыла", "раму"};
	assertThat(result, is(expected));
	}
 }
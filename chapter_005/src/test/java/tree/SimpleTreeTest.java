package tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тест создать элементарную структуру дерева [#1711].
 * Created by Алексей on 03.11.2017.
 */
public class SimpleTreeTest {
    /**
     * Тест add() и contains().
     * Убеждаемся, что элемента нет в дереве,
     * добавляем элемент, убеждаемся, что он появился.
     */
    @Test
    public void addAndContainTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        assertFalse(tree.contains(5));
        tree.add(5);
        assertTrue(tree.contains(5));
    }

    /**
     * Тест итератора.
     * Добавляем элементы, создаем ветвь с одной стороны без листьев
     * и ветвь с двумя листьями с другой стороны. Убеждаемся, что итератор видит все ветви
     * и может удалить элемент. Убеждаемся, что hasNext() также работает.
     */
    @Test
    public void iteratorTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        Iterator<Integer> iterator = tree.iterator();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(17);
        tree.add(13);
        assertThat(iterator.next(), is(5));
        assertThat(iterator.next(), is(10));
        assertThat(iterator.next(), is(13));
        iterator.remove();
        assertThat(iterator.next(), is(15));
        assertThat(iterator.next(), is(17));
        assertFalse(tree.contains(13));
        assertFalse(iterator.hasNext());
    }

    /**
     * Тест delete. Удаляем ветвь без листьев.
     */
    @Test
    public void deleteBranchWithoutLeavesTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        Iterator<Integer> iterator = tree.iterator();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        assertTrue(tree.delete(5));
        assertThat(iterator.next(), is(10));
        assertThat(iterator.next(), is(15));
        assertFalse(iterator.hasNext());
    }

    /**
     * Тест delete. Удаляем ветвь только с левыми листьями.
     */
    @Test
    public void deleteBranchWithoutRightLeafCTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(2);
        assertTrue(tree.delete(5));
        assertFalse(tree.contains(5));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(15));
    }

    /**
     * Тест delete. Удаляем ветвь только с правыми листьями.
     */
    @Test
    public void deleteBranchWithOnlyRightLeavesTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        tree.add(5);
        tree.add(10);
        tree.add(15);
        tree.add(14);
        tree.add(17);
        assertTrue(tree.delete(10));
        assertFalse(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(15));
        assertTrue(tree.contains(14));
        assertTrue(tree.contains(17));
    }

    /**
     * Тест delete. Удаляем корень всего дерева.
     */
    @Test
    public void deleteStartRootTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        tree.add(20);
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(30);
        tree.add(25);
        tree.add(35);
        assertTrue(tree.delete(20));
        assertFalse(tree.contains(20));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(15));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(30));
        assertTrue(tree.contains(35));
    }

    /**
     * Тест delete. Удаляем корень.
     */
    @Test
    public void deleteRootTest() {
        SimpleTree<Integer> tree = new SimpleTree<>();
        tree.add(20);
        tree.add(30);
        tree.add(25);
        tree.add(35);
        tree.add(22);
        assertTrue(tree.delete(30));
        assertFalse(tree.contains(30));
        assertTrue(tree.contains(20));
        assertTrue(tree.contains(22));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(35));
    }
}
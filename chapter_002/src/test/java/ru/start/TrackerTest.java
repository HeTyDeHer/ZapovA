package ru.start;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Tracker.
 * Created by Алексей on 20.09.2017.
 */

public class TrackerTest {
    /**
     * Добавление, получение списка.
            */
    @Test
    public void addAndGet() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("Name1", "Desc1", 1201L);
        tracker.add(item1);
        List<Item> result = tracker.findAll();
        List<Item> expected = new ArrayList<>(Arrays.asList(item1));
        assertThat(result, is(expected));
    }
    /**
     * Добавление, поиск по id, модификация.
     */
    @Test
    public void addFindByIdUpdate() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("Name1", "Desc1", 1201L);
        tracker.add(item1);
        String id1 = item1.getId();
        Item item = tracker.findById(id1);
        item.setName("NameMod");
        tracker.update(item);
        String result = tracker.findById(id1).getName();
        assertThat(result, is("NameMod"));
    }
    /**
     * Добавление, поиск по имени.
     */
    @Test
    public void addFindbyName() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("Name1", "Desc1", 1201L);
        Item item2 = new Item("Name2", "Desc2", 1202L);
        Item item3 = new Item("Name1", "Desc3", 1203L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> result = tracker.findByName("Name1");
        List<Item> expected = new ArrayList<>(Arrays.asList(item1, item3));
        assertThat(result, is(expected));
    }
    /**
     * Добавление, поиск по имени.
     */
    @Test
    public void addDelete() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("Name1", "Desc1", 1201L);
        Item item2 = new Item("Name2", "Desc2", 1202L);
        Item item3 = new Item("Name3", "Desc3", 1203L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.delete(item2);
        List<Item> result = tracker.findAll();
        List<Item> expected = new ArrayList<>(Arrays.asList(item1, item3));
        assertThat(result, is(expected));
    }

}

package ru.start;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Tracker.
 * Created by Алексей on 25.09.2017.
 */
public class StartsUiTest {
    /**
     * Тест добавление.
     */
    @Test
    public void addTest() {
    Tracker tracker = new Tracker();
    StubInput sInput = new StubInput(new String[] {"0", "Name1", "Desc1", "6"});
    StartsUi startsUi = new StartsUi(sInput, tracker);
    startsUi.init();
    assertThat(tracker.findAll()[0].getName(), is("Name1"));
    }
    /**
     * Тест измененине.
     */
    @Test
    public void updateTest() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("Name1", "Desc1", 1201L);
        tracker.add(item1);
        StubInput sInput = new StubInput(new String[] {"2", item1.getId(), "NameUpd", "DescUpd", "0", "6"});
        StartsUi startsUi = new StartsUi(sInput, tracker);
        startsUi.init();
        assertThat(tracker.findAll()[0].getName(), is("NameUpd"));
    }
    /**
     * Тест удаление.
     */
    @Test
    public void deleteTest() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("Name1", "Desc1", 1201L);
        Item item2 = new Item("Name2", "Desc2", 1202L);
        Item item3 = new Item("Name3", "Desc3", 1203L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        StubInput sInput = new StubInput(new String[]{"3", item1.getId(), "0", "6"});
        StartsUi startsUi = new StartsUi(sInput, tracker);
        startsUi.init();
        assertThat(tracker.findAll().length, is(2));
    }
}

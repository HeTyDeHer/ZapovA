package ru.start;

import java.util.Arrays;
import java.util.UUID;

/**
 * Трекер.
 * @author Алексей on 21.09.2017.
 */
public class Tracker {
    /**
     * Массив item.
     */
    private Item[] items = new Item[100];
    /**
     * Индекс последнего item.
     */
    private int index = 0;
    /**
     * добавление заявок.
     * @param item - заявка для добавления.
     * @return item (зачем?)
     */
    public Item add(Item item) {
        item.setId(String.valueOf(UUID.randomUUID()));
        this.items[index++] = item;
        return item;
    }

    /**
     * редактирование заявок.
     * @param item - заявка для редактирования.
     */
    public void update(Item item) {
        for (int i = 0; i < index; i++) {
            if (item.getId().equals(this.items[i].getId())) {
                this.items[i] = item;
            }
        }
    }

    /**
     * удаление заявок.
     * @param item - заявка для удаления.
     */
    public void delete(Item item) {
        for (int i = 0; i < index; i++) {
            if (item.getId().equals(this.items[i].getId())) {
                this.items[i] = this.items[index - 1];
                this.items[index - 1] = null;
                index--;
                break;
            }
        }

    }

    /**
     * получение списка всех заявок.
     * @return массив всех заявок
     */
    public Item[] findAll() {
        return Arrays.copyOf(this.items, index);
    }

    /**
     * получение списка по имени.
     * @param key имя.
     * @return список по имени.
     */
    public Item[] findByName(String key) {
        Item[] itemsByKey = new Item[index];
        int j = 0;
        for (int i = 0; i < index; i++) {
            if (this.items[i].getName().equals(key)) {
                itemsByKey[j++] = this.items[i];
            }
        }
        return Arrays.copyOf(itemsByKey, j);
    }

    /**
     * получение заявки по id.
     * @param id - id заявки.
     * @return заявка по id.
     */
    public Item findById(String id) {
        Item result = null;
        for (int i = 0; i < index; i++) {
            if (this.items[i].getId().equals(id)) {
               result = this.items[i];
               break;
            }
        }
        return result;
    }
}

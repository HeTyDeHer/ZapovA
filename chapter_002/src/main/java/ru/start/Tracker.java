package ru.start;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Трекер.
 * @author Алексей on 21.09.2017.
 */
public class Tracker {
    /**
     * Массив item.
     */
    private List<Item> items = new ArrayList<>();
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
        items.add(index++, item);
        return item;
    }

    /**
     * редактирование заявок.
     * @param item - заявка для редактирования.
     */
    public void update(Item item) {
        for (int i = 0; i < index; i++) {
            if (item.getId().equals(this.items.get(i).getId())) {
                this.items.set(i, item);
            }
        }
    }

    /**
     * удаление заявок.
     * @param item - заявка для удаления.
     */
    public void delete(Item item) {
        for (int i = 0; i < index; i++) {
            if (item.getId().equals(this.items.get(i).getId())) {
                this.items.set(i, this.items.get(index - 1));
                this.items.remove(index - 1);
                index--;
                break;
            }
        }

    }

    /**
     * получение списка всех заявок.
     * @return массив всех заявок
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * получение списка по имени.
     * @param key имя.
     * @return список по имени.
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < index; i++) {
            if (this.items.get(i).getName().equals(key)) {
                result.add(this.items.get(i));
            }
        }
        return result;
    }

    /**
     * получение заявки по id.
     * @param id - id заявки.
     * @return заявка по id.
     */
    public Item findById(String id) {
        Item result = null;
        for (int i = 0; i < index; i++) {
            if (this.items.get(i).getId().equals(id)) {
               result = this.items.get(i);
               break;
            }
        }
        return result;
    }
}

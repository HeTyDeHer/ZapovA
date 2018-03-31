package todo.service;

import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.model.Item;

import java.util.ArrayList;
import java.util.List;
/**
 * Создать TO DO list [#3786].
 * CRUD Item.
 */
public class ItemDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDao.class);

    /**
     * add Item to db.
     * @param item Item.
     * @return id of created Item or -1 if error.
     */
    public int create(Item item) {
        int id = -1;
        try (Session session = DBConnection.getSession()) {
            id = (int) session.save(item);
        } catch (JDBCException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return id;
    }

    /**
     * Read Item by id.
     * @param id id.
     * @return Item by id or empty Item if none or error.
     */
    public Item read(int id) {
        Item item = null;
        try (Session session = DBConnection.getSession()) {
            item = session.get(Item.class, id);
        } catch (JDBCException e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (item == null) {
            item = new Item();
        }
        return item;
    }

    /**
     * Get all items.
     * @return all items or empty list if error.
     */
    public List<Item> readAll() {
        List<Item> item = new ArrayList<>();
        try (Session session = DBConnection.getSession()) {
            item = session.createQuery("from Item").list();
        } catch (JDBCException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return item;
    }

    /**
     * Update item.
     * @param item Item.
     */
    public void update(Item item) {
        try (Session session = DBConnection.getSession()) {
            session.update(item);
        } catch (JDBCException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Delete item by id.
     * @param id id.
     */
    public void delete(int id) {
        Item item = new Item();
        item.setId(id);
        try (Session session = DBConnection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        } catch (JDBCException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        ItemDao itemDao = new ItemDao();
        System.out.println(itemDao.readAll());
    }

}

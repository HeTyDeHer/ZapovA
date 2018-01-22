package tracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Трекер.
 * @author Алексей on 21.09.2017.
 */
public class Tracker {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";

    /**
     * Creating tables if not exists.
     */
    public void init(Connection conn) {
        try (Statement statement = conn.createStatement()){
            statement.execute(SQLQuery.CREATE_TABLE_ITEMS);
            statement.execute(SQLQuery.CREATE_TABLE_COMMENTS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * добавление заявок.
     * @param item - заявка для добавления.
     * @return item.
     */
    public Item add(Item item, Connection conn) {
        try (PreparedStatement addItem = conn.prepareStatement(SQLQuery.ADD_ITEM, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement addComment = conn.prepareStatement(SQLQuery.ADD_COMMENT)) {
            conn.setAutoCommit(false);
            addItem.setString(1, item.getName());
            addItem.setString(2, item.getDescription());
            addItem.setObject(3, item.getCreated());
            addItem.execute();
            try (ResultSet rs = addItem.getGeneratedKeys()) {
                rs.next();
                int id = rs.getInt(ID);
                item.setId(id);
                if (item.getComments() != null) {
                    for (String s : item.getComments()) {
                        addComment.setInt(1, id);
                        addComment.setString(2, s);
                        addComment.execute();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * редактирование заявок.
     * @param item - заявка для редактирования.
     */
    public void update(Item item, Connection conn) {
        try (PreparedStatement updateItem = conn.prepareStatement(SQLQuery.UPDATE_ITEM)) {
            updateItem.setString(1, item.getName());
            updateItem.setString(2, item.getDescription());
            updateItem.setInt(3, item.getId());
            updateItem.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаление заявок.
     * @param item - заявка для удаления.
     */
    public void delete(Item item, Connection conn) {
        try (PreparedStatement delete = conn.prepareStatement(SQLQuery.DELETE_FROM_ITEMS_BY_ID)) {
            delete.setInt(1, item.getId());
            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * получение списка всех заявок.
     * @return массив всех заявок
     */
    public List<Item> findAll(Connection conn) {
        ArrayList<Item> result = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQLQuery.SELECT_ALL_ENTRIES_ITEMS)) {

            while (rs.next()) {
                Item item = new Item(rs.getInt(ID), rs.getString(NAME),
                        rs.getString(DESCRIPTION), rs.getTimestamp(CREATED).toLocalDateTime());
                result.add(item);
            }

            for (Item item : result) {
                item.setComments(getCommentsById(item.getId(), conn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * получение списка по имени.
     * @param name имя.
     * @return список по имени.
     */
    public List<Item> findByName(String name, Connection conn) {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement select = conn.prepareStatement(SQLQuery.SELECT_ITEMS_BY_NAME)) {
            select.setString(1, name);
            try (ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(rs.getInt(ID), rs.getString(NAME),
                            rs.getString(DESCRIPTION), rs.getTimestamp(CREATED).toLocalDateTime());
                    item.setComments(getCommentsById(rs.getInt(ID), conn));
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * получение заявки по id.
     * @param id - id заявки.
     * @return заявка по id.
     */
    public Item findById(int id, Connection conn) {
        Item result = null;
        try (PreparedStatement select = conn.prepareStatement(SQLQuery.SELECT_ITEMS_BY_ID)){
            select.setInt(1, id);
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    result = new Item(rs.getInt(ID), rs.getString(NAME),
                            rs.getString(DESCRIPTION), rs.getTimestamp(CREATED).toLocalDateTime());
                    result.setComments(getCommentsById(id, conn));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<String> getCommentsById(int id, Connection conn) {
        List<String> result = new ArrayList<>();
        try (PreparedStatement selectComments = conn.prepareStatement(SQLQuery.SELECT_COMMENTS_BY_ID)) {
            selectComments.setInt(1, id);
            try (ResultSet rscomments = selectComments.executeQuery()) {
                while (rscomments.next()) {
                    result.add(rscomments.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

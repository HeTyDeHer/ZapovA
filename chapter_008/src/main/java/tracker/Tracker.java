package tracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Трекер.
 * @author Алексей on 21.09.2017.
 */
public class Tracker {
    private final Connection conn;
    private PreparedStatement ps;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";

    public Tracker(Connection conn) {
        this.conn = conn;
        this.init();
    }

    /**
     * Creating tables if not exists.
     */
    private void init() {
        try {
            ps = conn.prepareStatement(SQLQuery.CREATE_TABLE_ITEMS);
            ps.execute();
            ps = conn.prepareStatement(SQLQuery.CREATE_TABLE_COMMENTS);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * добавление заявок.
     * @param item - заявка для добавления.
     * @return item.
     */
    public Item add(Item item) {
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(SQLQuery.ADD_ITEM, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setObject(3, item.getCreated());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(ID);
            item.setId(id);
            if (item.getComments() != null) {
                for (String s : item.getComments()) {
                    ps = conn.prepareStatement(SQLQuery.ADD_COMMENT);
                    ps.setInt(1, id);
                    ps.setString(2, s);
                    ps.execute();
                }
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
    public void update(Item item) {

        try {
            ps = conn.prepareStatement(SQLQuery.UPDATE_ITEM);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setInt(3, item.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаление заявок.
     * @param item - заявка для удаления.
     */
    public void delete(Item item) {
        try {
            ps = conn.prepareStatement(SQLQuery.DELETE_FROM_ITEMS_BY_ID);
            ps.setInt(1, item.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * получение списка всех заявок.
     * @return массив всех заявок
     */
    public List<Item> findAll() {
        ArrayList<Item> result = new ArrayList<>();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SQLQuery.SELECT_ALL_ENTRIES_ITEMS);
            while (rs.next()) {
               Item item = new Item(rs.getInt(ID), rs.getString(NAME),
                        rs.getString(DESCRIPTION), rs.getTimestamp(CREATED).toLocalDateTime());
                result.add(item);
            }
            for (Item item : result) {
                item.setComments(getCommentsById(item.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * получение списка по имени.
     * @param name имя.
     * @return список по имени.
     */
    public List<Item> findByName(String name) {
        List<Item> result = new ArrayList<>();
        try {
            ps = conn.prepareStatement(SQLQuery.SELECT_ITEMS_BY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getInt(ID), rs.getString(NAME),
                        rs.getString(DESCRIPTION), rs.getTimestamp(CREATED).toLocalDateTime());
                item.setComments(getCommentsById(rs.getInt(ID)));
                result.add(item);
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
    public Item findById(int id) {
        Item result = null;
        try {
            ps = conn.prepareStatement(SQLQuery.SELECT_ITEMS_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new Item(rs.getInt(ID), rs.getString(NAME),
                        rs.getString(DESCRIPTION), rs.getTimestamp(CREATED).toLocalDateTime());
                result.setComments(getCommentsById(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<String> getCommentsById(int id) {
        List<String> result = new ArrayList<>();
        try {
            ps = conn.prepareStatement(SQLQuery.SELECT_COMMENTS_BY_ID);
            ps.setInt(1, id);
            ResultSet rscomments = ps.executeQuery();
            while (rscomments.next()) {
                result.add(rscomments.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Connection getConn() {
        return conn;
    }
}

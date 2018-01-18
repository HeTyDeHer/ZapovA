package tracker;

public class SQLQuery {
    public static final String CREATE_TABLE_ITEMS = "CREATE TABLE IF NOT EXISTS items (id serial PRIMARY KEY NOT NULL, "
            + "name varchar NOT NULL, description varchar NOT NULL, created timestamp)";
    public static final String ADD_ITEM = "INSERT INTO items (name, description, created) VALUES (?, ?, ?)";
    public static final String UPDATE_ITEM = "UPDATE items SET name = ?, description = ? WHERE id = ?";
    public static final String DELETE_FROM_ITEMS_BY_ID = "DELETE FROM items WHERE id = ? ";
    public static final String SELECT_ALL_ENTRIES_ITEMS = "SELECT * FROM items";
    public static final String SELECT_ITEMS_BY_NAME = "SELECT * FROM items WHERE name = ?";
    public static final String SELECT_ITEMS_BY_ID = "SELECT * FROM items WHERE id = ? ";
    public static final String CREATE_TABLE_COMMENTS = "CREATE TABLE IF NOT EXISTS comments (id serial PRIMARY KEY NOT NULL, "
            + "item_id integer REFERENCES items (id) ON DELETE CASCADE NOT NULL,"
            + "comment varchar NOT NULL)";
    public static final String ADD_COMMENT = "INSERT INTO comments (item_id, comment) VALUES (?, ?)";
    public static final String SELECT_COMMENTS_BY_ID = "SELECT comment FROM comments WHERE item_id = ? ";

    private SQLQuery() {
    }
}

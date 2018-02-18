package common;

/**
 * Queries for JDBC.
 */
public final class SQLQuery {
    public static final String INSERT = "INSERT INTO users (login, name, email, created) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET name = ?, email = ? WHERE login = ? ";
    public static final String GET = "SELECT * FROM users WHERE login = ? ";
    public static final String GET_ALL = "SELECT * FROM users";
    public static final String DELETE = "DELETE FROM users WHERE login = ? ";
    private SQLQuery() {
    }
}

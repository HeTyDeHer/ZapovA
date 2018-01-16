package jdbc;

import java.sql.*;

public class SQLiteConnection {
    /**
     * Connection.
     */
    private Connection connection;

    /**
     * Creating connection.
     * @param url connection url.
     * @param user username.
     * @param pwd password.
     * @return connection.
     */
    public Connection setConnection(String url, String user, String pwd) {
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Creating table "test" with single column "field" in current database.
     */
    public void createTable() {
        Statement st = null;
        try {
            st = connection.createStatement();
            st.execute("DROP TABLE IF EXISTS test");
            st.execute("CREATE TABLE IF NOT EXISTS test(field integer UNIQUE)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * filling table "test" with a sequence of numbers from 1 to elementsNumber.
     * @param elementsNumber number of elements.
     */
    public void fillTable(int elementsNumber) {
        PreparedStatement statement = null;
//        Statement statement = null;
        try {

            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO test VALUES (?)");
            for (int i = 1; i <= elementsNumber; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.setAutoCommit(true);

            /*StringBuilder query = new StringBuilder("INSERT INTO test VALUES ");
            for (int i = 1; i <= elementsNumber; i++) {
                if (i == elementsNumber) {
                    query.append(String.format("(%d)", i));
                    break;
                }
                query.append(String.format("(%d),", i));
            }
            statement = connection.createStatement();
            statement.execute(query.toString());*/

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

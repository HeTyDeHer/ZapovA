package jdbc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class SQLiteConnection {

    private String url;
    private String user;
    private String pwd;

    /**
     * Constructor.
     * Creating clean table.
     * @param properties filename.
     */
    public SQLiteConnection(String properties) {
        this.getProperties(properties);
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pwd);
             Statement st = connection.createStatement()) {
            st.execute("DROP TABLE IF EXISTS test");
            st.execute("CREATE TABLE test(field integer UNIQUE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * filling table "test" with a sequence of numbers from 1 to elementsNumber.
     * @param elementsNumber number of elements.
     */
    public void fillTable(int elementsNumber) {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pwd);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO test VALUES (?)")) {
            connection.setAutoCommit(false);
            for (int i = 1; i <= elementsNumber; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting properties from file.
     * @param properties filename.
     */
    private void getProperties(String properties) {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(SQLiteConnection.class.getClassLoader().getResourceAsStream(properties)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.url = prop.getProperty("database.url");
        this.user = prop.getProperty("username");
        this.pwd = prop.getProperty("password");
    }

    /**
     * Create XML from database.
     * @param tableName tableName in database.
     * @param output output file.
     * @param column names of columns.
     */
    public void createXMLFromDB(String tableName,
                                File output, String column) {

        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.pwd);
             Statement statement = connection.createStatement()){

            String toSelect = "SELECT " + column + " FROM " + tableName;
            try (ResultSet rs = statement.executeQuery(toSelect)) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                Document doc = factory.newDocumentBuilder().newDocument();
                Element root = doc.createElement("entries");
                doc.appendChild(root);
                while (rs.next()) {
                    Element entry = doc.createElement("entry");
                    root.appendChild(entry);
                    Element field = doc.createElement(column);
                    field.appendChild(doc.createTextNode(rs.getString(column)));
                    entry.appendChild(field);
                }
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(doc), new StreamResult(output));
            }
        } catch (SQLException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

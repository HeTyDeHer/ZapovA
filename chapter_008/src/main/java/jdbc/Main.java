package jdbc;


import java.io.File;
import java.sql.Connection;

/**
 * XML XSLT JDBC Оптимизация [#20459].
 * Основной класс.
 */

public class Main {
    /** URL connection. */
    private String url;
    /** Username for connection. */
    private String user;
    /** Password for connection. */
    private String pwd;
    /** Number of elements in table. */
    private int elementsNumber;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main mc = new Main();
        mc.setConnectionData("jdbc:sqlite:testJDBC.db", "user", "pwd");
        mc.setElementsNumber(10);
        mc.init();
        System.out.println((System.currentTimeMillis() - start) / 1000);
    }

    /**
     * Database connection,
     * Creating table with one column (field),
     * And filling with a sequence of numbers from 1 to elementsNumber.
     */
    public void init() {
        long start = System.currentTimeMillis();
        SQLiteConnection conn = new SQLiteConnection();
        Connection connection = conn.setConnection(this.getUrl(), this.getUser(), this.getPwd());
        conn.createTable();
        conn.fillTable(elementsNumber);
        long fillingTime = System.currentTimeMillis();
        System.out.println((fillingTime - start) / 1000);
        DBtoXML toXML = new DBtoXML();
        toXML.createXMLFromDB("test", connection, new File("1.xml"), "field");
        toXML.transformXML(new File("1.xml"), new File("JDBC4Test.xsl"), new File("2.xml"));
        toXML.parseXML(new File("2.xml"), new HandlerJDBCTask());
        System.out.println((System.currentTimeMillis() - fillingTime) / 1000);
    }

    /**
     * Set data for connection.
     * @param url connection url.
     * @param user username.
     * @param pwd password.
     */
    public void setConnectionData(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * Set number of elements in table.
     * @param elementsNumber number of elements in table.
     */
    public void setElementsNumber(int elementsNumber) {
        this.elementsNumber = elementsNumber;
    }

    /**
     * Url getter.
     * @return url.
     */
    public String getUrl() {
        return url;
    }
    /**
     * username getter.
     * @return username.
     */
    public String getUser() {
        return user;
    }
    /**
     * password getter.
     * @return password.
     */
    public String getPwd() {
        return pwd;
    }
    /**
     * elements Number getter.
     * @return elementsNumber.
     */
    public int getElementsNumber() {
        return elementsNumber;
    }
}

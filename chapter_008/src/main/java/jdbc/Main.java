package jdbc;


import java.io.File;

/**
 * XML XSLT JDBC Оптимизация [#20459].
 * Основной класс.
 */

public class Main {
    /** Number of elements in table. */
    private int elementsNumber;
    private File firstOutput;
    private File secondOutput;
    private String dbProperties;


    public Main(int elementsNumber, String firstOutputPath, String secondOutputPath, String dbProperties) {
        this.elementsNumber = elementsNumber;
        this.firstOutput = new File(firstOutputPath);
        this.secondOutput = new File(secondOutputPath);
        this.dbProperties = dbProperties;
    }

    public static void main(String[] args) {
        Main mc = new Main(1000, "1.xml", "2.xml", "jdbc.properties");
        mc.init();
    }

    /**
     * Database connection,
     * Creating table with one column (field),
     * And filling with a sequence of numbers from 1 to elementsNumber.
     */
    public void init() {
        SQLiteConnection conn = new SQLiteConnection(dbProperties);
        conn.fillTable(elementsNumber);
        conn.createXMLFromDB("test", this.firstOutput, "field");
        XMLTransform xmlTransform = new XMLTransform();
        xmlTransform.transformXML(this.firstOutput, new File("JDBC4Test.xsl"), this.secondOutput);
        xmlTransform.parseXML(this.secondOutput, new HandlerJDBCTask());

    }
}

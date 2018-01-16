package jdbc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * XML XSLT JDBC Оптимизация [#20459].
 * Class for work with XML - creating, transforming.
 */
public class DBtoXML {
    /**
     * Create XML from database.
     * @param tableName tableName in database.
     * @param connection connection to database.
     * @param output output file.
     * @param column names of columns.
     */
    public void createXMLFromDB(String tableName, Connection connection,
                                File output, String ... column) {
        Statement statement = null;
        ResultSet rs;

        try {
            statement = connection.createStatement();
            StringBuilder toSelect = new StringBuilder();
            for (int i = 0; i < column.length; i++) {
                if (i == column.length - 1) {
                    toSelect.append(column[i]);
                    break;
                }
                toSelect.append(String.format("%s, ", column[i]));
            }
            rs = statement.executeQuery("SELECT " + toSelect.toString()
                    + " FROM " + tableName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().newDocument();


            Element root = doc.createElement("entries");
            doc.appendChild(root);

            while (rs.next()) {
                Element entry = doc.createElement("entry");
                root.appendChild(entry);
                for (String s : column) {
                    Element field = doc.createElement(s);
                    field.appendChild(doc.createTextNode(rs.getString(s)));
                    entry.appendChild(field);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(output));
        } catch (SQLException | ParserConfigurationException | TransformerException e) {
           e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Transform xml throught xslt.
     * @param input xml for transforming.
     * @param xsl for tranforming.
     * @param output file.
     */
    public void transformXML(File input, File xsl, File output) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(xsl));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new StreamSource(input), new StreamResult(output));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parsing xml.
     * @param file xml for parsing.
     * @param handler Handler for saxparser.
     */
    public void parseXML(File file, DefaultHandler handler) {
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(file, handler);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


}

package jdbc;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

/**
 * XML XSLT JDBC Оптимизация [#20459].
 * Class for work with XML - creating, transforming.
 */
public class XMLTransform {

    /**
     * Transform xml through xslt.
     * @param input xml for transforming.
     * @param xsl for transforming.
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

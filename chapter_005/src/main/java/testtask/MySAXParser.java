package testtask;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Тестовое задание. [#1001].
 * Created by Алексей on 07.11.2017.
 */
public class MySAXParser {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        long start = System.currentTimeMillis();
        SAXParserFactory.newInstance().newSAXParser().parse(new File("orders.xml"), new SAXParserHandler());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

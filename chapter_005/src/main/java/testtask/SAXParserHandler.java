package testtask;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Тестовое задание. [#1001].
 * Created by Алексей on 06.11.2017.
 */
public class SAXParserHandler extends DefaultHandler {
    private final String ADD_ORDER = "AddOrder";
    private final String DELETE_ORDER = "DeleteOrder";
    private Books books = new Books();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(ADD_ORDER)) {
            books.setBook(attributes.getValue("book"));
            String action = attributes.getValue("operation");
            int id = Integer.parseInt(attributes.getValue("orderId"));
            double price = Double.parseDouble(attributes.getValue("price"));
            int volume = Integer.parseInt(attributes.getValue("volume"));
            books.add(action, id, price, volume);
        } else if (qName.equals(DELETE_ORDER)) {
            books.setBook(attributes.getValue("book"));
            int id = Integer.parseInt(attributes.getValue("orderId"));
            books.actionDel(id);
        }
    }
    @Override
    public void endDocument() {
        books.sout();
    }

}

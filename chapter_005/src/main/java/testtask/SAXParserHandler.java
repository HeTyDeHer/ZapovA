package testtask;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Тестовое задание. [#1001].
 * Created by Алексей on 06.11.2017.
 */
public class SAXParserHandler extends DefaultHandler {
    private static final String ADD_ORDER = "AddOrder";
    private static final String DELETE_ORDER = "DeleteOrder";
    private Books books = new Books();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (ADD_ORDER.equals(qName)) {
            String bookName = attributes.getValue("book");
            String action = attributes.getValue("operation");
            int id = Integer.parseInt(attributes.getValue("orderId"));
            double price = Double.parseDouble(attributes.getValue("price"));
            int volume = Integer.parseInt(attributes.getValue("volume"));
            Order order = new Order(bookName, action, id, price, volume);
            books.actionAdd(order);
        } else if (DELETE_ORDER.equals(qName)) {
            String bookName = attributes.getValue("book");
            int id = Integer.parseInt(attributes.getValue("orderId"));
            books.actionDelete(bookName, id);
        }
    }
    @Override
    public void endDocument() {
        books.sout();
    }

}

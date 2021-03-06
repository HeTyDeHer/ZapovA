package jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class HandlerJDBCTask extends DefaultHandler {

    private long sum = 0;
    private static final String ENTRY = "entry";
    private static final String FIELD = "field";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (ENTRY.equals(qName)) {
            sum += Long.parseLong(attributes.getValue(FIELD));
        }
    }

    @Override
    public void endDocument() {
        System.out.println(sum);

    }
}

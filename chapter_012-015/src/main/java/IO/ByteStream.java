package IO;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 1. Проверить байтовый поток [#858].
 */

public class ByteStream {

    private static final Logger logger = LoggerFactory.getLogger(ByteStream.class);

    /**
     * Check, if number in inputstream is even.
     * @param is inputstream.
     * @return true/false.
     * @throws IOException ex.
     */
    public boolean isNumberEven(InputStream is) throws IOException {
        int number;
        try {
            number = Integer.parseInt(IOUtils.toString(is));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return number % 2 == 0;
    }
}

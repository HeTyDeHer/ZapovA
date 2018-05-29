package IO;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByteStreamTest {

    private final static Logger logger = LoggerFactory.getLogger(ByteStreamTest.class);

    @Test
    void ifEvenShouldReturnTrue() {
        ByteStream bs = new ByteStream();
        String even = "2";
        try (InputStream is = new ByteArrayInputStream(even.getBytes())){
            assertTrue(bs.isNumberEven(is));
        } catch (IOException e) {
           logger.error(e.getMessage(), e);
        }
    }
    @Test
    void ifOddShouldReturnFalse() {
        ByteStream bs = new ByteStream();
        String odd = "1";
        try (InputStream is = new ByteArrayInputStream(odd.getBytes())){
            assertFalse(bs.isNumberEven(is));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Test
    void ifNotIntShouldReturnFalse() {
        ByteStream bs = new ByteStream();
        String notInt = "test";
        try (InputStream is = new ByteArrayInputStream(notInt.getBytes())){
            assertFalse(bs.isNumberEven(is));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
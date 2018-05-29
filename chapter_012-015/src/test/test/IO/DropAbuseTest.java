package IO;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DropAbuseTest {

    @Test
    void ifContainsAbuseShouldDeleteIt() {
        DropAbuse dropAbuse = new DropAbuse();
        InputStream in = new ByteArrayInputStream("definitely not abuse".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        String[] abuse = new String[]{"not", "abuse"};
        dropAbuse.dropAbuses(in, out, abuse);
        String result = out.toString();
        for (String s : abuse) {
            assertFalse(result.contains(s));
        }
    }
}
package trietask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class WordIndexTest {

    private final String path = "test.txt";

    @Test
    void getIndexes4Word() {
        WordIndex wordIndex = new WordIndex();
        Integer[] expected = this.createFileForTest();
        wordIndex.loadFile(path);
        Set<Integer> result = wordIndex.getIndexes4Word("line");
        Integer[] actual = result.toArray(new Integer[result.size()]);
        Assertions.assertArrayEquals(expected, actual);
        Assertions.assertNull(wordIndex.getIndexes4Word("lines"));
    }

    private Integer[] createFileForTest() {
        List<String> lines = Arrays.asList("line zero", " w   line", "line second", "linelast", "latest line");
        try {
            Files.write(Paths.get(path), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Integer[]{0, 1, 2, 4};
    }
}
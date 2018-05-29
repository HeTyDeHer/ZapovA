package IO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortTextFileTest {

    private static final Logger logger = LoggerFactory.getLogger(SortTextFile.class);
    /** tempDir for app. */
    private String tempDir;
    private long bufferSize;
    /** Directory for testFiles. */
    private File testDir;
    private File testFile;
    private File resultFile;

    /**
     * Create all files, directories.
     * Fill test file with string of different lengths, total 3Gb approx.
     */
    @BeforeEach
    void setUp() {
        this.tempDir = "temp";
        this.bufferSize = 100 * 1024 * 1024;
        this.testDir = new File("testDir");
        if (!testDir.exists()) {
            System.out.println(testDir.mkdirs());
        }
        this.testFile = new File(testDir, "toSort.txt");
        testDir.deleteOnExit();
        try (FileWriter fileWriter = new FileWriter(testFile)) {
            long i = 0;
            while (i < 75000000) {
                StringBuilder s = new StringBuilder();
                int max = (int)(1 + Math.random()*30);
                for (int k = 0; k < max; k++) {
                    Random r = new Random();
                    char c = (char)(r.nextInt(26) + 'a');
                    s.append(c);
                    if(r.nextBoolean()) {
                        s.append(" ");
                    }
                    if (r.nextBoolean()) {
                        c = (char)(r.nextInt(32) + 'а');
                        s.append(c);
                    }
                }
                s.append(System.lineSeparator());
                fileWriter.write(s.toString());
                i++;
            }
            fileWriter.flush();
            this.resultFile = new File(testDir, "result.txt");
            this.resultFile.createNewFile();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Test
    void sort() {
        SortTextFile sort = new SortTextFile(bufferSize, tempDir);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resultFile), "UTF-8"))) {
            sort.sort(testFile, resultFile);
            String first = br.readLine();
            assertNotNull(first);
            while (br.ready()) {
                String second = br.readLine();
                assertTrue(first.length() <= second.length());
                first = second;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
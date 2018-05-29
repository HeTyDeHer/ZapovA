package IO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * 3. Сортировка >3G файл [#860].
 *  Необходимо отсортировать файл по возрастанию длин строк.
 */
public class SortTextFile {

    private static final Logger logger = LoggerFactory.getLogger(SortTextFile.class);
    private final long bufferSize;
    private final File tempDir;

    public SortTextFile(long bufferSize, String tempDir) {
        this.bufferSize = bufferSize;
        File dir = new File(tempDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.tempDir = dir;
    }

    public void sort(File source, File distance) throws IOException {
        List<File> files = this.splitFile(source);
        this.mergeFiles(files, distance);
    }

    /**
     * Read fileto buffer. When buffer is full, sort it and write it to temp file.
     * @param file File to sort.
     * @return list with temporary files.
     * @throws IOException io.
     */
    private List<File> splitFile(File file) throws IOException {
        List<File> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                File tempFile = File.createTempFile("split", ".txt", tempDir);
                tempFile.deleteOnExit();
                result.add(tempFile);
                try (BufferedWriter write = new BufferedWriter(new FileWriter(tempFile))) {
                    ArrayList<String> temp = new ArrayList<>();
                    int size = 0;
                    while (size < bufferSize / 2 && reader.ready()) {
                        String line = reader.readLine();
                        size += line.length();
                        temp.add(line);
                    }
                    temp.sort(Comparator.comparingInt(String::length));
                    for (String s : temp) {
                        write.write(s);
                        write.write(System.lineSeparator());
                    }
                    temp.clear();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
            }
        }
        return result;
    }

    /**
     * Merge sorted files to one file.
     * @param files sorted files.
     * @param output result file.
     * @throws IOException io.
     */
    private void mergeFiles(List<File> files, File output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            Queue<FileIterator> queue = new PriorityQueue<>((o1, o2) -> {
                String first = o1.peek();
                String second = o2.peek();
                if (first == null) {return -1;}
                if (second == null) {return 1;}
                return first.length() - second.length();
            });
            for (File file : files) {
                queue.add(new FileIterator(file, "UTF-8"));
            }
            while (!queue.isEmpty()) {
                FileIterator top = queue.poll();
                writer.write(top.poll());
                if (top.isEmpty()) {
                    top.close();
                } else {
                    queue.add(top);
                }
                if (!queue.isEmpty()) {
                    writer.write(System.lineSeparator());
                }
            }
        }
    }

    /**
     * File wrapper.
     * Allows to work with sorted text file as with the priority queue.
     */
    private class FileIterator {

        private BufferedReader reader;
        private String nextElement;

        public FileIterator(File file, String charset) throws IOException {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            this.loadNextElement();
        }

        public String peek() {
            return nextElement;
        }

        public String poll() throws IOException {
            String result = this.nextElement;
            this.loadNextElement();
            return result;
        }

        public boolean isEmpty() {
            return this.peek() == null;
        }

        public void close() throws IOException {
            this.reader.close();
        }

        private void loadNextElement() throws IOException {
            if (this.reader.ready()) {
                this.nextElement = this.reader.readLine();
            } else {
                this.nextElement = null;
            }
        }
    }
}


package IO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 2. Удаление запрещенных слов [#859].
 * Задан символьным поток. и символьный выходной поток. надо удалить все слова abuse.
 * Важно все преобразования нужно делать в потоке. нельзя зачитать весь поток в память, удалить слова и потом записать.
 * нужно все делать в потоке.
 */
public class DropAbuse {
    private static final Logger logger = LoggerFactory.getLogger(DropAbuse.class);
    private final LinkedList<Integer> inputQueue = new LinkedList<>();
    private final LinkedList<Integer> outputQueue = new LinkedList<>();

    /**
     * Filter inputStream from forbidden words and write it to output stream.
     * @param in inputStream.
     * @param out outputStream.
     * @param abuse forbidden words.
     */
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        try (Scanner scanner = new Scanner(in);
             Writer writer = new BufferedWriter(new OutputStreamWriter(out))) {
            while (scanner.hasNext()) {
                String check = scanner.next();
                for (String s : abuse) {
                    if (check.contains(s)) {
                        check = check.replace(s, "");
                    }
                }
                writer.write(check + " ");
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

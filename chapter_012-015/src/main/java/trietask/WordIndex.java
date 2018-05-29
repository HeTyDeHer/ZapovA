package trietask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * Нужно сделать класс WordIndex (можно создавать и другие сопутствующие классы, если это необходимо),
 * который по сути будет являться индексом.
 * Он должен позволять по заданному слову находить все вхождения (позиции) его в файле.
 */
public class WordIndex {

    private final Trie trie = new Trie();
    private static final Logger logger = LoggerFactory.getLogger(WordIndex.class);

    /**
     * Load file. Read file, create trie index.
     * @param filename file.
     */
    public void loadFile(String filename){
        try (Scanner scanner = new Scanner(new File(filename))){
            int index = 0;
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                String[] words = currentLine.split(" ");
                for (String word : words) {
                    trie.addWord(word, index);
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Search searchWord in previously created trie.
     * @param searchWord searchWord
     * @return indexes of the searchWord.
     */
    public Set<Integer> getIndexes4Word(String searchWord) {
        return trie.containsWord(searchWord);
    }

}

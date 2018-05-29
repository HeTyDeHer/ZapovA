package trietask;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Trie {

    private final HashMap<Character, Trie> children = new HashMap<>();
    private final Set<Integer> positions = new TreeSet<>();
    private boolean isWord;

    public void addWord(String word, Integer position) {
        if(word.isEmpty()) {
            return;
        }
        word = word.toLowerCase();
        char first = word.charAt(0);
        Trie node = children.get(first);
        if (node == null) {
            node = new Trie();
            children.put(first, node);
        }
        word = word.substring(1);
        if (word.isEmpty()) {
            node.isWord = true;
            node.positions.add(position);
        } else {
            node.addWord(word, position);
        }
    }

    public Set<Integer> containsWord(String word) {
        if(word.isEmpty()) {
            return null;
        }
        word = word.toLowerCase();
        Trie node = children.get(word.charAt(0));
        if (node == null) {
            return null;
        }
        word = word.substring(1);
        Set<Integer> result;
        if (word.isEmpty() && node.isWord) {
            result = node.positions;
        } else {
            result = node.containsWord(word);
        }
        return result;
    }
}

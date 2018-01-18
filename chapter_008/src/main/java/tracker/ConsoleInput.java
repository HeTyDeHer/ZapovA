package tracker;

import java.util.List;
import java.util.Scanner;

/**
 * Ввод.
 * Created by Алексей on 22.09.2017.
 */
public class ConsoleInput implements Input {
    /**
     * Scanner.
     */
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String ask(String question) {
        System.out.printf(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, List<Integer> range) {
        boolean valid = false;
        int key = Integer.valueOf(ask(question));
        for (int i : range) {
            if (key == i) {
                valid = true;
                break;
                }
            }
        if (valid) {
        return key;
        } else {
            throw new MenuOutExeption("Out of menu range");
        }
    }

    public int ask(String question, int min) {
        int key = Integer.valueOf(ask(question));
        if (key < min) {
            throw new MenuOutExeption("Out of menu range");
        } else {
            return key;
        }

    }
}

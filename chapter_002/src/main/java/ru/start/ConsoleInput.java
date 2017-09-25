package ru.start;

import java.util.Scanner;

/**
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
}

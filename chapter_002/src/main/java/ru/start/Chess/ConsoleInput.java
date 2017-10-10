package ru.start.chess;
import java.util.Scanner;

/**
 * Ввод для шахмат.
 * Created by Алексей on 27.09.2017.
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

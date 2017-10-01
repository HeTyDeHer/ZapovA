package ru.start.vendingmachine;
import java.util.Scanner;

/**
 * Ввод для автомата.
 * Created by Алексей on 27.09.2017.
 */
public class ConsoleInput implements Input {
    /**
     * Scanner.
     */
    private Scanner scanner = new Scanner(System.in);
    @Override
    public int ask(String question) {
        int answer = -999999;
        do {
            try {
                System.out.printf(question);
                answer = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Число!");
            }

        } while (answer == -999999);
        return answer;
    }
}

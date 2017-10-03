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
    public String ask(String question) {
        System.out.printf(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, int[] range) {
        boolean valid = false;
        System.out.printf(question);
        int answer = Integer.parseInt(scanner.nextLine());
        for (int i : range) {
            if (i == answer) {
                valid = true;
            }
        }
        if (!valid) {
            throw new InvalidChoiceExeption("Неправильный выбор.");
        } else {
            return answer;
        }
    }
}

package ru.start.vendingmachine;

/**
 * Валидация ввода.
 * Created by Алексей on 02.10.2017.
 */
public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, int[] range) {
        int answer = -1;
        boolean invalid = true;
        do {
            try {
                answer = super.ask(question, range);
                invalid = false;
            } catch (InvalidChoiceExeption e) {
                System.out.println("Введено неверное число.");
            } catch (NumberFormatException e) {
                System.out.println("Должно быть введено число.");
            }
        } while (invalid);
        return answer;
    }
}

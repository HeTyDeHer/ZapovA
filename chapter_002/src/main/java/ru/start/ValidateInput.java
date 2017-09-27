package ru.start;

/**
 * Проверка ввода.
 * Created by Алексей on 26.09.2017.
 */
public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value =  super.ask(question, range);
                invalid = false;
            } catch (MenuOutExeption e) {
                System.out.println("Число должно быть в требуемом диапазоне.");
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        } while (invalid);
        return value;
    }
}
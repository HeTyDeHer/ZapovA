package tracker;

import java.util.List;

/**
 * Проверка ввода.
 * Created by Алексей on 26.09.2017.
 */
public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, List<Integer> range) {
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

    @Override
    public int ask(String question, int min) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value =  super.ask(question, min);
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

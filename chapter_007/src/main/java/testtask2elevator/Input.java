package testtask2elevator;

import java.util.Scanner;

public interface Input {
    int askFrom();
    int askWhere();
}

class ManualInput implements Input {

    @Override
    public int askFrom() {
        System.out.println("Где нажата кнопка? (1) Внутри лифта (2) Снаружи");
        int choise = 0;
        while (true) {
            try {
                choise = new Scanner(System.in).nextInt();
            } catch (Exception e) {
                System.out.println("Неверный ввод.");
            }
            if (choise == 1 || choise == 2) {
                break;
            }
            System.out.println("Выберите 1 или 2");
        }
        return choise;
    }

    @Override
    public int askWhere() {
        System.out.println("Выберите этаж, от 0 до 20");
        boolean invalid = true;
        int choise = 0;
        while (true) {
            try {
                choise = new Scanner(System.in).nextInt();
            } catch (Exception e) {
                System.out.println("Неверный ввод.");
            }
            if (choise > 0 && choise <= 20) {
                break;
            }
            System.out.println("Выберите этаж, от 0 до 20");
        }
        return choise;
    }
}

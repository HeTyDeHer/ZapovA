package threads;

/**
 * 2. Сделать ожидание вывода. [#1017].
 * Необходимо, что бы вначале запуска программы из первой части на экране выводилась информация о программа.
 * А после вычисления данных, выводилась запись о завершении программы.
 * Важно, эта информация всегда должна выводиться в начале и в конце вычисления.
 * Created by Алексей on 22.11.2017.
 */
public class WordsAndSpaces {

    public void spaceCounter(String phrase) {
        int counter = 0;
        char[] chars = phrase.toCharArray();
        for (char ch : chars) {
            if (ch == ' ') {
                counter++;
            }
        }
        System.out.printf("Пробелов: %d%n", counter);
    }

    public static void wordsCounter(String phrase) {
        int counter = 0;
        boolean wasLetter = false;
        char[] chars = phrase.toCharArray();
        for (char ch : chars) {
            if (!Character.isLetter(ch) && wasLetter) {
                counter++;
                wasLetter = false;
            }
            if (Character.isLetter(ch)) {
                wasLetter = true;
            }
        }
        if (wasLetter) {
            counter++;
        }
        System.out.printf("Слов: %d%n", counter);
    }

    public void count(String phrase) {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                wordsCounter(phrase);
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                spaceCounter(phrase);
            }
        };
        System.out.println("Информация о программе.");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Завершение программы.");
    }
}

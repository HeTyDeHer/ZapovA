package threads;

/**
 * 1. Cчитать количество слов и пробелов в тексте [#1016].
 * Создать программу, которая будет считать количество слов и пробелов в тексте.
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
        thread1.start();
        thread2.start();
    }
}

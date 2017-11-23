package threads;

/**
 * 3. Реализовать механизм программнной остановки потока. [#1019].
 * В потоке этого класса считаем символы.
 * Created by Алексей on 23.11.2017.
 */
public class CountChar implements Runnable {
    /** Строка, в которой считаем символы. */
    private String text;
    /** Счетчик символов. */
    private int counter;

    public CountChar(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        char[] textArr = text.toCharArray();
        for (char c : textArr) {
            counter++;
            if (Thread.interrupted()) {
                System.out.println("Подсчет прерван");
                break;
            }
        }
        System.out.printf("Посчитано символов: %d%n", counter);
    }
}

package threads;

/**
 * 3. Реализовать механизм программнной остановки потока. [#1019].
 * Основной класс. Запускаем оба потока.
 * Created by Алексей on 23.11.2017.
 */
public class CharCounterLimitedTime {
    /** поток, Который засекает время. */
    private Thread t1;
    /** поток, который считает символы.*/
    private Thread t2;

    /**
     * Конструктор.
     * @param time время на выполнение программы.
     * @param text строка, в которой считаются символы.
     */
    public CharCounterLimitedTime(int time, String text) {
        this.t1 = new Thread(new Time(time));
        this.t2 = new Thread(new CountChar(text));
    }

    /**
     * Основно метод.
     * Запускаем 2 потока, если второй не завершен к моменту окончания первого,
     * завершаем его.
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        this.t1.start();
        this.t2.start();
        t1.join();
        if (t2.isAlive()) {
            t2.interrupt();
            t2.join();
        }
        System.out.println("End.");
    }
}

package threads;

/**
 * 3. Реализовать механизм программнной остановки потока. [#1019].
 * В потоке этого класса ждем необходимое время.
 * Created by Алексей on 23.11.2017.
 */
public class Time implements Runnable {
    /**
     * Время, которое ждем.
     */
    private int wait;

    public Time(int wait) {
        this.wait = wait;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}

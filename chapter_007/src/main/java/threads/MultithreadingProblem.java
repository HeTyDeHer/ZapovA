package threads;

/**
 * 1. Проиллюстрировать проблемы с многопоточностью. [#1096].
 * Created by Алексей on 24.11.2017.
 */
public class MultithreadingProblem {
    private int z = 0;

    public static void main(String[] args) {
        MultithreadingProblem mp = new MultithreadingProblem();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 20000; i++) {
                    mp.z++;
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 20000; i++) {
                    mp.z++;
                }
            }
        };
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(mp.z);

    }
}

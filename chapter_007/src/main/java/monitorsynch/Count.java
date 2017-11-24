package monitorsynch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. класс Count и метод int incremant() [#1102].
 * Created by Алексей on 24.11.2017.
 */
@ThreadSafe
public class Count {
    private int counter;
    private Lock incremantLock = new ReentrantLock();

    @GuardedBy("incremantLock")
    public int incremant() {
        incremantLock.lock();
        try {
            counter++;
            return counter;
        } finally {
            incremantLock.unlock();
        }
    }


    public static void main(String[] args) {
        Count count = new Count();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    System.out.println(count.incremant());
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    System.out.println(count.incremant());
                }
            }
        };
        t1.start();
        t2.start();

    }


}

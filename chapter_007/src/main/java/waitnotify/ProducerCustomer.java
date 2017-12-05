package waitnotify;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;

/**
 * 1. Реализовать шаблон Producer Customer. [#1098].
 * Created by Алексей on 03.12.2017.
 */
@ThreadSafe
public class ProducerCustomer<T> {
    /** Хранилище данных. */
    @GuardedBy("this")
    private LinkedList<T> deque = new LinkedList<>();

    /**
     * Добавление данных. Если места нет, ждем.
     * @param data данные.
     * @throws InterruptedException
     */
    public synchronized void addData(T data) throws InterruptedException {
        while (getSize() > 3) {
            System.out.println("Нет места, жду");
            this.wait();
        }
        deque.add(data);
        this.notifyAll();
    }

    /**
     * Получение размера.
     * @return размер.
     */
    @GuardedBy("this")
    public synchronized int getSize() {
        return deque.size();
    }

    /**
     * Получение данных. Если данных нет, ждем.
     * @return данные.
     * @throws InterruptedException
     */
    @GuardedBy("this")
    public synchronized T getData() throws InterruptedException {
        while (getSize() == 0) {
            System.out.println("Нет данных, жду");
            this.wait();
        }
        T result = deque.poll();
        this.notifyAll();
        return result;
    }

    private class Producer implements Runnable {
        ProducerCustomer<Integer> pc;
        int data = 0;

        public Producer(ProducerCustomer<Integer> pc) {
            this.pc = pc;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    pc.addData(data);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Добавил " + data++);
            }
        }
    }

    private class Consumer implements Runnable {
        ProducerCustomer<Integer> pc;

        public Consumer(ProducerCustomer<Integer> pc) {
            this.pc = pc;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Получил " + pc.getData());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}




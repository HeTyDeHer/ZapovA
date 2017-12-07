package waitnotify;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 2. Реализовать ThreadPool [#1099]
 * Created by Алексей on 07.12.2017.
 */

public class MyThreadPool {
    private final LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
    private volatile boolean isFinished = false;


    public MyThreadPool() {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(new InnerThread()).start();
        }
    }

    public void execute (Runnable command) {
        if (!isFinished) {
            queue.add(command);
        }
    }

    public void shutdown() {
        isFinished = true;
    }

    private class InnerThread implements Runnable {

        @Override
        public void run() {
            while (!queue.isEmpty() || !isFinished) {
                Runnable task = queue.poll();
                if (task != null) {
                    task.run();
                }
            }

        }
    }
}

class SomeUselessWorkForTest implements Runnable {
    private String id;

    public SomeUselessWorkForTest(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(id + " is Working");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(id + " is Finished");
    }

    public static void main(String[] args) {
        MyThreadPool mtp = new MyThreadPool();

        for (int i = 0; i < 20; i++) {
            mtp.execute(new SomeUselessWorkForTest(String.valueOf(i)));
        }

        mtp.shutdown();

    }
}

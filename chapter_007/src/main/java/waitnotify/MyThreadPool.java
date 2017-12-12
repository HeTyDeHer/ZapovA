package waitnotify;

import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 2. Реализовать ThreadPool [#1099]
 * Created by Алексей on 07.12.2017.
 */

@ThreadSafe
public class MyThreadPool implements Executor {
    /** Очередь заданий. */
    private final LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
    /** Запущенные треды. */
    private final List<Thread> currentThreads = new LinkedList<>();
    /** Установлен ли флаг "finished". */
    private volatile boolean isFinished = false;
    /** Остановлен ли ThreadPool. */
    private volatile boolean isTerminated = false;
    /** Прерваны ли потоки. */
    private volatile boolean isInterrupted = false;

    /**
     * Монитор1.
     * Использован для выставления флага isTerminated в методе setTerminated.
     * Поток передаёт notify когда завершается.
     */
    private final static Object monitor1 = new Object();
    /**
     * Монитор2.
     * Использован для уведомления потока,
     * ждущего остановку ThreadPool в методе awaitTermination.
     * setTerminated передаёт notify после установки isTerminated.
     */
    private final static Object monitor2 = new Object();


    /**
     * Конструктор.
     * Создаем потоки по количеству доступных процессоров и стартуем их.
     */
    public MyThreadPool() {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread(new InnerThread());
            t.start();
            currentThreads.add(t);
        }
    }

    /**
     * Добавляем задание в очередь, если не выставлен флаг isFinished.
     * @param command задание.
     */
    @Override
    public void execute (Runnable command) {
        if (!isFinished) {
            queue.add(command);
        }
    }

    /**
     * Завершение работы ThreadPool с ожиданием заверщения задач.
     * Выставляем флаг isFinished и запускаем выставление флага isTerminated.
     * После выставления флага isFinished ThreadPool перестает принимать новые задания,
     * заканчивает запланированные задания (без прерывания их работы),
     * после чего завершается.
     */
    public void shutdown() {
        isFinished = true;
        new Thread (() -> setTerminated()).start();
    }

    /**
     * Устанавливает флаг isTerminated.
     * Флаг устанавливается после завершения всех внутренних потоков ThreadPool.
     */
    private void setTerminated() {
        synchronized (monitor1) {
            boolean terminated = false;
            while (!terminated) {
                for (Thread t : currentThreads) {
                    if (t.isAlive()) {
                        terminated = false;
                        try {
                            monitor1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    terminated = true;
                }
            }
            this.isTerminated = true;
            synchronized (monitor2) {
                monitor2.notifyAll();
            }

        }
    }

    /**
     * Завершает работу ThreadPool без ожидания завершения задач.
     * @return List задач из очереди, которые не были взяты в работу.
     */
    public List<Runnable> shutdownNow() {
        isFinished = true;
        isInterrupted = true;
        new Thread (() -> setTerminated()).start();

        for (Thread t : currentThreads) {
            t.interrupt();
        }
        return new LinkedList<>(queue);
    }

    /**
     * Проверка, выставлен ли флаг isFinished.
     * @return true/false.
     */
    public boolean isShutdown() {
        return isFinished;
    }

    /**
     * Проверка, завершен ли ThreadPool.
     * @return true/false.
     */
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * Ждем завершения работы ThreadPool.
     * Возвращает true, если ThreadPool завершается за указанное время,
     * либо уже был завершен к моменту запуска метода.
     * @param timeout заданное время.
     * @param unit еденицы заданного времени.
     * @return завершен за указанное время/нет.
     * @throws InterruptedException InterruptedException.
     */
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        if(isTerminated) {
            return true;
        }
        synchronized (monitor2) {
            monitor2.wait(unit.toMillis(timeout));
        }
        return isTerminated;
    }



    private class InnerThread implements Runnable {

        @Override
        public void run() {
            while (!isInterrupted && (!queue.isEmpty() || !isFinished)) {
                Runnable task = queue.poll();
                if (task != null) {
                    task.run();
                }
            }
            synchronized (monitor1) {
                monitor1.notifyAll();
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
            System.out.println(id + " is Interrupted");
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println(id + " is Finished");
    }


    public static void main(String[] args) throws InterruptedException {
        MyThreadPool mtp = new MyThreadPool();
        for (int i = 0; i < 20; i++) {
            mtp.execute(new SomeUselessWorkForTest(String.valueOf(i)));
        }
        System.out.println(mtp.isShutdown());
 //       Thread.sleep(1500);
        List<Runnable> t = mtp.shutdownNow();
        System.out.println(mtp.isShutdown());
        System.out.println(mtp.isTerminated());
        try {
            mtp.awaitTermination(1000000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(mtp.isTerminated());

        for (Runnable r : t) {
            new Thread(r).start();
        }


    }
}

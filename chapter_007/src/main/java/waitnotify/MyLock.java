package waitnotify;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * 3. Реализовать собственный механизм блокировок Lock.  [#1100].
 * Created by Алексей on 08.12.2017.
 */
public class MyLock implements Lock {
    /** Захачена ли блокировка. */
    private volatile boolean isLocked = false;

    /**
     * Если блокировка уже захвачена, ждем.
     * Если нет - захватываем.
     */
    @Override
    public synchronized void lock() {
        while (isLocked) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLocked = true;
    }

    /**
     * Не уверен, что я правильно понял этот метод =).
     * @throws InterruptedException InterruptedException
     */
    @Override
    public synchronized void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        this.lock();
    }

    /**
     * Пробуем получить блокировку.
     * Если уже захвачена - возвращаем false,
     * если нет - захватываем и возвращаем true.
     * @return захватили/нет.
     */
    @Override
    public synchronized boolean tryLock() {
        if(isLocked) {
            return false;
        } else {
            this.lock();
            return true;
        }

    }

    /**
     * Пробуем получить блокировку.
     * Если уже захвачена - пробуем в течении указанного времени.
     * Если не удалось - возвращаем false.
     * Если удалось - true.
     * @param time время.
     * @param unit Единицы измерения.
     * @return захватили/нет.
     * @throws InterruptedException InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if(!this.isLocked) {
            this.lock();
            return true;
        } else {
            long timeout = unit.toMillis(time);
            long begin = System.currentTimeMillis();
            wait(timeout);
            long waited = System.currentTimeMillis() - begin;
            tryLock((timeout - waited), TimeUnit.MILLISECONDS);
        }
        return false;
    }

    /**
     * Снимаем блокировку.
     */
    @Override
    public synchronized void unlock() {
        this.isLocked = false;
        notifyAll();
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

}

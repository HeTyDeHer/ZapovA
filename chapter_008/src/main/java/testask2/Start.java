package testask2;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Testask JDBC.
 * Start class for testtask.
 */
public class Start implements Runnable {
    private final Logger logger = Logger.getLogger(Start.class.getName());
    private final SQLContact sql;
    public static final ScheduledExecutorService SERVICE = new ScheduledThreadPoolExecutor(1);

    public Start() {
        this.sql = new SQLContact("testask.properties");
    }

    @Override
    public void run() {
        ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        Future<ArrayList<Vacancy>> future = ex.submit(new ParseIndexPages("http://www.sql.ru/forum/job-offers/", sql.getLastUpdate(), ex));
        try {
            sql.addVacancies(future.get());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        Start.SERVICE.scheduleAtFixedRate(new Start(), 0, 1, TimeUnit.DAYS);
    }
}

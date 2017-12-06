package monitorsynch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * 4. Поиск текста в файле. [#1106].
 * Created by Алексей on 02.12.2017.
 */
@ThreadSafe
public class ParallerSearch {
    /** Корневая папка поиска. */
    private final String root;
    /** Текст для поиска. */
    private final String text;
    /** Расширения, среди которых надо искать. */
    private final List<String> extentions;
    /** Пути к файлам, удовлетворяющим условиям поиска. */
    @GuardedBy("this")
    private final List<String> result = new ArrayList<>();

    /**
     * Конструктор.
     * @param root Корневая папка поиска.
     * @param text Текст для поиска.
     * @param extentions Расширения, среди которых надо искать.
     */
    public ParallerSearch(String root, String text, List<String> extentions) {
        this.root = root;
        this.text = text;
        this.extentions = extentions;
    }

    /**
     * Геттер.
     * @return Массив с путями к файлам, удовлетворяющим условиям поиска.
     */
    private List<String> getResult() {
        return result;
    }

    /**
     * Добавление путей к файлам, удовлетворяющим условиям поиска в результирующий массив.
     * @param result Путь к файлу, удовлетворяющим условиям поиска.
     */
    @GuardedBy("this")
    private synchronized void addResult(String result) {
        this.result.add(result);
    }

    /**
     * Начало парсинга. Проверяем правильность введенного пути и запускаем поток парсинга директории.
     * Получаем List с найденными файлами нужного расширения, запускаем их парсинг отдельными потоками.
     * @throws InterruptedException InterruptedException
     */

    public void parsing() throws InterruptedException {
        File dir = new File(this.root);
        if (!dir.isDirectory()) {
            System.out.println("Wrong root");
            return;
        }

        FutureTask<List<File>> task = new FutureTask<>(new DirParser(dir));
        new Thread(task).start();

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            for (File file : task.get()) {
                executor.submit(new ParseFile(file));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        executor.awaitTermination(100000000, TimeUnit.SECONDS); // todo разобраться с shutdown

        for (String s: this.getResult()) {
            System.out.println(s);
        }
    }


    /**
     * Парсинг файла. Проверяем наличие искомого текста.
     */
    private class ParseFile implements Runnable {
        /** Файл. */

        private File file;

        /**
         * Конструктор.
         * @param file файл.
         */
        public ParseFile(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.contains(text)) {
                        addResult(file.getAbsolutePath());
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Парсинг папки.
     * Если находим вложенную папку - запускаем отдельный поток.
     * Возвращаем List с файлами.
     */
    public class DirParser implements Callable<List<File>> {
         /** Папка для поиска. */
        private File dir;
        /** Лист с найденными файлами. */
        private List<File> resultList;

        public DirParser(File dir) {
            this.dir = dir;
            this.resultList = new ArrayList<>();
        }

        @Override
        public List<File> call() throws Exception {
            File [] files = dir.listFiles();
            if (files == null) {
                return resultList;
            }

            ArrayList<Future> results = new ArrayList<>();
            for (File file : files) {
                if (file.isDirectory()) {
                    FutureTask<List<File>> task = new FutureTask<>(new DirParser(file));
                    results.add(task);
                    Thread t = new Thread(task);
                    t.start();
                } else if (file.isFile()){
                    for (String ext : extentions) {
                        if (file.getName().endsWith(ext)) {
                            resultList.add(file);
                        }
                    }
                }
            }
            for (Future f : results) {
                resultList.addAll((Collection<? extends File>) f.get());
            }
            return resultList;
        }
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        ParallerSearch ps = new ParallerSearch("C:/", "test", new ArrayList<>(Arrays.asList("txt")));
        try {
            ps.parsing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - time)/1000);

    }
}



package monitorsynch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    /** Запущенные треды. */
    @GuardedBy("threads")
    private final ArrayList<Thread> threads = new ArrayList<>();

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
    public List<String> getResult() {
        return result;
    }

    /**
     * Добавление путей к файлам, удовлетворяющим условиям поиска в результирующий массив.
     * @param result Путь к файлу, удовлетворяющим условиям поиска.
     */
    @GuardedBy("this")
    public synchronized void addResult(String result) {
        this.result.add(result);
    }

    /**
     * Начало парсинга. Проверяем правильность введенного пути и запускаем поток парсинга директории.
     * @throws InterruptedException InterruptedException
     */
    @GuardedBy("threads")
    public void beginParsing() throws InterruptedException {
        File dir = new File(this.root);
        if (!dir.isDirectory()) {
            System.out.println("Wrong root");
            return;
        } else {
            Thread thread = new Thread(new ParseDir(dir));
            synchronized (threads) {
                threads.add(thread);
            }
            thread.start();
        }
    }

    /**
     * Мне не нравится этот метод, но ничего умнее я не придумал.
     * Проверяем, все ли треды завершились.
     * В массиве запущенных тредов threads проверяем, есть ли живые треды. Если обнаруживаем модификацию массива
     * во время работы метода (ConcurentModification), то перезапускаем метод.
     * @throws InterruptedException InterruptedException
     */
    private void checkIfAllThreadsAreFinished() throws InterruptedException {
        boolean finished = false;
        while (!finished) {
            try {
                for (Thread t : this.threads) {
                    if(t.isAlive()) {
                        finished = false;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else {
                        finished = true;
                    }
                }
            } catch (Exception e) {
                Thread.sleep(1000);
                checkIfAllThreadsAreFinished();
            }
        }
    }

    /**
     * Класс, который парсит директории на наличие файлов искомого расширения.
     * Если таковой находится, запускает парсинг файла отдельным потоком.
     */
    private class ParseDir implements Runnable {
        /** Директория для парсинга. */
        private File dir;

        /**
         * Конструктор.
         * @param dir Директория для парсинга.
         */
        private ParseDir(File dir) {
            this.dir = dir;
        }

        /**
         * Добавление созданного треда в массив тредов.
         * @param t созданный тред.
         */
        private void addThread(Thread t) {
            synchronized (threads) {
                threads.add(t);
            }
        }

        /**
         * Если обнаруживаем папку, запускаем её парсинг отдельным тредом.
         * Если обнаруживаем файл нужного расширения, запускаем его парсинг отдельным тредом.
         * Все созданные треды добавляем в массив тредов.
         */
        @Override
        public void run() {
            File [] files = dir.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    Thread t = new Thread(new ParseDir(file));
                    addThread(t);
                    t.start();
                } else if (file.isFile()){
                    for (String ext : extentions) {
                        if (file.getName().endsWith(ext)) {
                            Thread t = new Thread(new ParseFile(file));
                            addThread(t);
                            t.start();
                            break;
                        }
                    }
                }
            }
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


    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        ParallerSearch ps = new ParallerSearch("C:/", "test", new ArrayList<>(Arrays.asList("txt")));
        try {
            ps.beginParsing();
            ps.checkIfAllThreadsAreFinished();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - time)/1000);
        System.out.println(ps.getResult().size());

        for (String s: ps.getResult()) {
            System.out.println(s);
        }
    }

}



package testtask;

import java.util.*;

/**
 * Тестовое задание. [#1001].
 * Книга.
 * Created by Алексей on 06.11.2017.
 */
public class Book {
    private class PriceAndVolumeKeeper {
        /** Price. */
        private final double price;
        /** Volume. */
        private final int volume;

        /**
         * Конструктор.
         * @param price price.
         * @param volume volume.
         */
        private PriceAndVolumeKeeper(double price, int volume) {
            this.price = price;
            this.volume = volume;
        }

        /**
         * Геттер price.
         * @return price.
         */
        private int getVolume() {
            return volume;
        }

        /**
         * Геттер volume.
         * @return volume.
         */
        private double getPrice() {
            return price;
        }
    }

    private String bookName;

    /**
     * Геттер название книги.
     * @return название книги.
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Маp sell.
     * Храним id операции как ключ,
     * price и volume - первое и второе значения соответственно.
     */
    private HashMap<Integer, PriceAndVolumeKeeper> sell;
    /**
     * Маp buy.
     * Храним id операции как ключ,
     * price и volume - первое и второе значения соответственно.
     */
    private HashMap<Integer, PriceAndVolumeKeeper> buy;
    /**
     * Sorted Маp sell.
     * price ключ, volume - значениe.
     */
    private TreeMap<Double, Integer> sellOut;
    /**
     * Sorted Маp buy.
     * price ключ, volume - значениe.
     */
    private TreeMap<Double, Integer> buyOut;

    /**
     * Конструктор. Для buyOut сразу задаем обратную сортировку.
     */
    public Book(String bookName) {
        this.bookName = bookName;
        this.sell = new HashMap<>();
        this.buy = new HashMap<>();
        this.sellOut = new TreeMap<>();
        this.buyOut = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return -o1.compareTo(o2);
            }
        });
    }

    /**
     * Добавляем элемент в sell.
     * @param id id.
     * @param price price.
     * @param volume volume.
     */
    public void putSell(Integer id, Double price, Integer volume) {
        this.sell.put(id, new PriceAndVolumeKeeper(price, volume));
    }
    /**
     * Добавляем элемент в buy.
     * @param id id.
     * @param price price.
     * @param volume volume.
     */
    public void putBuy(Integer id, Double price, Integer volume) {
        this.buy.put(id, new PriceAndVolumeKeeper(price, volume));
    }

    /**
     * Удаляем элемент.
     * @param id id.
     */
    public void delete(Integer id) {
        if (sell.remove(id) == null) {
            buy.remove(id);
        }
    }

    /**
     * Конвертируем получившуюся коллекцию.
     * Удаляем id, для вывода они не нужны.
     * Складываем объемы для повторяющихся значений цены.
     */
    public void convert() {
        for (Map.Entry<Integer, PriceAndVolumeKeeper> entry : sell.entrySet()) {
            Double price = entry.getValue().getPrice();
            Integer volume = entry.getValue().getVolume();
            sellOut.merge(price, volume, Integer::sum);
        }
        for (Map.Entry<Integer, PriceAndVolumeKeeper> entry : buy.entrySet()) {
            Double price = entry.getValue().getPrice();
            Integer volume = entry.getValue().getVolume();
            buyOut.merge(price, volume, Integer::sum);
        }
    }

    /**
     * Если bid>=ask, удаляем предложение.
     */
    public void matching() {
        while (sellOut.firstEntry().getValue() != 0) {
            if (sellOut.firstKey() > buyOut.firstKey()) {
                return;
            }
            if (sellOut.firstEntry().getValue() >= buyOut.firstEntry().getValue()) {
                sellOut.put(sellOut.firstKey(), sellOut.firstEntry().getValue() - buyOut.firstEntry().getValue());
                buyOut.remove(buyOut.firstKey());
            } else {
                buyOut.put(buyOut.firstKey(), buyOut.firstEntry().getValue() - sellOut.firstEntry().getValue());
                sellOut.remove(sellOut.firstKey());
            }
        }

    }

    /**
     * Вывод в консоль.
     */
    public void output() {
        ArrayList<String> sellOutList = new ArrayList<>(sellOut.size() + 1);
        ArrayList<String> buyOutList = new ArrayList<>(buyOut.size() + 1);
        for (Map.Entry<Double, Integer> entry : sellOut.entrySet()) {
            String s = String.format("%8d @ %6.2f", entry.getValue(), entry.getKey());
            sellOutList.add(s);
        }
        for (Map.Entry<Double, Integer> entry : buyOut.entrySet()) {
            buyOutList.add(entry.getValue() + " @ " + entry.getKey());
        }
        int minSize = Math.min(sellOutList.size(), buyOutList.size());
        for (int i = 0; i < minSize; i++) {
            System.out.println(sellOutList.get(i) + " - " + buyOutList.get(i));
        }
        if (sellOutList.size() > buyOutList.size()) {
            for (int i = minSize; i < sellOutList.size(); i++) {
                System.out.println(sellOutList.get(i) + " - -------");
            }
        } else if (sellOutList.size() < buyOutList.size()) {
            for (int i = minSize; i < buyOutList.size(); i++) {
                System.out.printf("%17s - %s %n", "-----", buyOutList.get(i));
            }
        }
    }
}

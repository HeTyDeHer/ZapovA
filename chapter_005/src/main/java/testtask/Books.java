package testtask;

import java.util.ArrayList;

/**
 * Тестовое задание. [#1001].
 * Хранилище книг.
 * Created by Алексей on 06.11.2017.
 */
public class Books {
    /** Книга текущая. */
    private Book currentBook;
    /** Все книги. */
    private ArrayList<Book> allBooks = new ArrayList<>();

    /**
     * Задаем текущую книгу.
     * @param bookName имя книги.
     */
    public void setBook(String bookName) {
        for (Book book : allBooks) {
            if (book.getBookName().equals(bookName)) {
                currentBook = book;
                return;
            }
        }
        Book newBook = new Book(bookName);
        allBooks.add(newBook);
        currentBook = newBook;
    }

    /**
     * Добавляем запись в книгу.
     * @param action тип записи.
     * @param id id.
     * @param price price.
     * @param volume volume.
     */
    public void add(String action, Integer id, Double price, Integer volume) {
        final String BUY = "BUY";
        final String SELL = "SELL";
        if (action.equals(BUY)) {
            currentBook.putBuy(id, price, volume);
        } else if (action.equals(SELL)) {
            currentBook.putSell(id, price, volume);
        }
    }

    /**
     * Удаляем запись из книги.
     * @param id id.
     */
    public void actionDel(Integer id) {
        currentBook.delete(id);
    }

    /**
     * Конвертация и вывод всех книг.
     */
    public void sout() {
        for (Book book : allBooks) {
            System.out.printf("%nOrder book: ${%s}%n", book.getBookName());
            System.out.printf("%11s  %14s %n", "BID", "ASK");
            book.convert();
            book.matching();
            book.output();
        }
    }
}

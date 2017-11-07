package testtask;

/**
 * Тестовое задание. [#1001].
 * Хранилище книг.
 * Created by Алексей on 06.11.2017.
 */
public class Books {
    /** Книга текущая. */
    private Book currentBook;
    /** Книга 1. */
    private Book book1;
    /** Книга 2. */
    private Book book2;
    /** Книга 3. */
    private Book book3;

    /**
     * Конструктор. Создаем три книги.
     */
    public Books() {
        this.book1 = new Book();
        this.book2 = new Book();
        this.book3 = new Book();
    }

    /**
     * Задаем текущую книгу.
     * @param bookName имя книги.
     */
    public void setBook(String bookName) {
        if (bookName.equals("book-1")) {
            currentBook = book1;
        } else if (bookName.equals("book-2")) {
            currentBook = book2;
        } else {
            currentBook = book3;
        }
    }

    /**
     * Добавляем запись в книгу.
     * @param action тип записи.
     * @param id id.
     * @param price price.
     * @param volume volume.
     */
    public void add(String action, Integer id, Double price, Integer volume) {
        if (action.equals("BUY")) {
            currentBook.putBuy(id, price, volume);
        } else {
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
        System.out.printf("%nOrder book: ${book-1}%n");
        System.out.printf("%11s  %14s %n", "BID", "ASK");
        book1.convert();
        book1.matching();
        book1.output();
        System.out.printf("%nOrder book: ${book-2}%n");
        System.out.printf("%11s  %14s %n", "BID", "ASK");
        book2.convert();
        book2.matching();
        book2.output();
        System.out.printf("%nOrder book: ${book-3}%n");
        System.out.printf("%11s  %14s %n", "BID", "ASK");
        book3.convert();
        book3.matching();
        book3.output();

    }
}

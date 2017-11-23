package testtask;


import java.util.HashSet;

/**
 * Тестовое задание. [#1001].
 * Хранилище книг.
 * Created by Алексей on 06.11.2017.
 */
public class Books {

    /** Все книги. */
    private HashSet<Book> allBooks = new HashSet<>();

    /**
     * Выбираем книгу... Блин, так тот же старый currentBook и получается. Но а как еще?
     * @param bookName имя книги.
     * @return книга, если есть. Если нет - null.
     */
    private Book chooseBook(String bookName) {
        Book book = null;
        for (Book b : allBooks) {
            if (b.getBookName().equals(bookName)) {
                book = b;
                break;
            }
        }
        return book;
    }

    /**
     * Добавляем order в книгу.
     * @param order order.
     */
    public void actionAdd(Order order) {
        Book book = chooseBook(order.getBookName());
        if (book == null) {
            book = new Book(order.getBookName());
            allBooks.add(book);
        }
        book.addOrder(order);
    }

    /**
     * Удаляем запись из книги.
     * @param id id.
     */
    public void actionDelete(String bookName, Integer id) {
        Book book = chooseBook(bookName);
        if(book != null) {
            book.delete(id);
        }
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

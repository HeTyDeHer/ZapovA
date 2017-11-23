package testtask;

/**
 * Тестовое задание. [#1001].
 * Order.
 * Created by Алексей on 23.11.2017.
 */
public class Order {
    /** Имя книги. */
    private String bookName;
    /** Действие (купить/продать). */
    private String action;
    /** Id.*/
    private int id;
    /** price. */
    private Double price;
    /** volume. */
    private Integer volume;


    public Order(String bookName, String action, int id, double price, int volume) {
        this.bookName = bookName;
        this.id = id;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    public String getAction() {
        return action;
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public int getVolume() {
        return this.volume;
    }

    public double getPrice() {
        return this.price;
    }
}

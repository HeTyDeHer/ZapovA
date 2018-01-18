package tracker;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Заявка.
 * @author Алексей on 21.09.2017.
 */
public class Item {
    /**     * Уникальный идентификатор.     */
    private int id;
    /**     * Имя.     */
    private String name;
    /**     * Описание.     */
    private String description;
    /** Создан. */
    private LocalDateTime created;
    /**     * Комментарии.     */
    private List<String> comments = new ArrayList<>();

    /**
     * @param id set id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Коструктор.
     * @param name имя.
     * @param description описание.
     * @param created создано.
     */
    public Item(int id, String name, String description, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        comments.add("testComment1" + Math.random());
        comments.add("testComment2" + Math.random());
    }

    /**
     * @param name set имя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param description set описание.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param created set создано.
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * @param comments set комментарии.
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    /**
     * @return get Id.
     */
    public int getId() {

        return id;
    }

    /**
     * @return get имя.
     */
    public String getName() {
        return name;
    }

    /**
     * @return get описание.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return get создано.
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * @return get комментарии.
     */
    public List<String> getComments() {
        return comments;
    }

}

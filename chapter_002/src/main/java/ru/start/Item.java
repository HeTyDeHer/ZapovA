package ru.start;

/**
 * Заявка.
 * @author Алексей on 21.09.2017.
 */
public class Item {
    /**     * Уникальный идентификатор.     */
    private String id;
    /**     * Имя.     */
    private String name;
    /**     * Описание.     */
    private String description;
    /** Создан. */
    private long created;
    /**     * Комментарии.     */
    private String[] comments;

    /**
     * @param id set id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Коструктор.
     * @param name имя.
     * @param description описание.
     * @param created создано.
     */
    public Item(String name, String description, long created) {
        this.name = name;
        this.description = description;
        this.created = created;
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
    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * @param comments set комментарии.
     */
    public void setComments(String[] comments) {
        this.comments = comments;
    }

    /**
     * @return get Id.
     */
    public String getId() {

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
    public long getCreated() {
        return created;
    }

    /**
     * @return get комментарии.
     */
    public String[] getComments() {
        return comments;
    }
}

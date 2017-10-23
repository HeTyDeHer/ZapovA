package generic;

/**
 * Base - это абстрактный класс для моделей c методами String getId(); void setId(String id).
 * Created by Алексей on 22.10.2017.
 */
public abstract class Base {
    /** ID. */
    private String id;
    /** Дополнительное поле. */
    private String name;

    /**
     * Сеттер.
     * @param id id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Геттер.
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Геттер.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Конструктор.
     * @param id id.
     * @param name name.
     */
    public Base(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Equals на основе id.
     * @param o объект для сравнения.
     * @return да/нет.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Base base = (Base) o;

        return getId().equals(base.getId());
    }

    /**
     * Hash на основе id.
     * @return hash.
     */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}

package cars.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractPart {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AbstractPart() {
    }

    public AbstractPart(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'';
    }
}

package cars.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
public class Model {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "make_id", nullable = false)
    private Make make;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(SAVE_UPDATE)
    private List<Engine> engines = new ArrayList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(SAVE_UPDATE)
    private List<Gearbox> gearboxes = new ArrayList<>();
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(SAVE_UPDATE)
    private List<Body> bodies = new ArrayList<>();

    public Model() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return getId() == model.getId() &&
                Objects.equals(getMake(), model.getMake()) &&
                Objects.equals(getName(), model.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getMake(), getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Engine> getEngines() {
        return engines;
    }

    public void setEngines(List<Engine> engines) {
        this.engines = engines;
    }

    public List<Gearbox> getGearboxes() {
        return gearboxes;
    }

    public void setGearboxes(List<Gearbox> gearboxes) {
        this.gearboxes = gearboxes;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
   //             ", make=" + make +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Model(Make make, String name, String description) {
        this.make = make;
        this.name = name;
        this.description = description;
    }
}

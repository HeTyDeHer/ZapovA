package cars.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CarOffer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(optional = false)
    private Model model;
    @ManyToOne(optional = false)
    private Body body;
    @ManyToOne(optional = false)
    private Engine engine;
    @ManyToOne(optional = false)
    private Gearbox gearbox;
    @Column(nullable = false)
    private String description;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner")
    private User offer_owner;
    @Column
    private boolean sold;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> images = new HashSet<>();
    @Column(nullable = false)
    private Timestamp created;

    public CarOffer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    public String getDescrition() {
        return description;
    }

    public void setDescrition(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getOffer_owner() {
        return offer_owner;
    }

    public void setOffer_owner(User offer_owner) {
        this.offer_owner = offer_owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "CarOffer{" +
                "id=" + id +
                ", model=" + model +
                ", body=" + body +
                ", engine=" + engine +
                ", gearbox=" + gearbox +
                ", description='" + description + '\'' +
//                ", offer_owner=" + offer_owner +
                ", sold=" + sold +
                '}';
    }

    public CarOffer(Model model, Body body, Engine engine, Gearbox gearbox, String description, User offer_owner) {
        this.model = model;
        this.body = body;
        this.engine = engine;
        this.gearbox = gearbox;
        this.description = description;
        this.offer_owner = offer_owner;
        this.sold = false;
        this.created = Timestamp.valueOf(LocalDateTime.now());
    }
}

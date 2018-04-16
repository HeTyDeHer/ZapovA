package cars.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Engine extends AbstractPart {

    @Column(nullable = false)
    private int displacement;
    @Column(nullable = false)
    private String model;

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Engine() {
    }

    public Engine(String type, String description, int displacement, String model) {
        super(type, description);
        this.displacement = displacement;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Engine{" +
                super.toString() +
                "displacement=" + displacement +
                ", model='" + model + '\'' +
                '}';
    }
}

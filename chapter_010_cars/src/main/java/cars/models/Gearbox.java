package cars.models;

import javax.persistence.Entity;

@Entity
public class Gearbox extends AbstractPart {

    public Gearbox() {
    }

    public Gearbox(String type, String description) {
        super(type, description);
    }

    @Override
    public String toString() {
        return "Gearbox{" +
                super.toString() +
                '}';
    }
}

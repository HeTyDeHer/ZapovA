package cars.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Body extends AbstractPart {

    public Body() {
    }

    public Body(String type, String description) {
        super(type, description);
    }

    @Override
    public String toString() {
        return "Body{" +
                super.toString() +
                '}';
    }
}

package pizzaco.domain.entities.pizza;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "spices")
public class Spice extends Ingredient {

    public Spice() {
    }
}

package pizzaco.domain.entities.ingredients;

import javax.persistence.*;

@Entity
@Table(name = "spices")
public class Spice extends Ingredient {

    public Spice() {
    }
}

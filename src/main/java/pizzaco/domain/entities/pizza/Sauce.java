package pizzaco.domain.entities.pizza;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sauces")
public class Sauce extends Ingredient {

    public Sauce() {
    }
}

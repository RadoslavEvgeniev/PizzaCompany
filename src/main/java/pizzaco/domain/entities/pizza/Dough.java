package pizzaco.domain.entities.pizza;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "doughs")
public class Dough extends Ingredient {

    public Dough() {
    }
}

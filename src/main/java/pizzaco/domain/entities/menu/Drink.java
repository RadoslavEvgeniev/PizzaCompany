package pizzaco.domain.entities.menu;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drinks")
public class Drink extends MenuItem {

    public Drink() {
    }
}

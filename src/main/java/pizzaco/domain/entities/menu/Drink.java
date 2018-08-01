package pizzaco.domain.entities.menu;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "drinks")
public class Drink extends MenuItem {

    private BigDecimal price;

    public Drink() {
    }
}

package pizzaco.domain.entities.menu;

import org.hibernate.annotations.GenericGenerator;
import pizzaco.domain.entities.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dips")
public class Dip extends MenuItem {

    public Dip() {
    }
}

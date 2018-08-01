package pizzaco.domain.entities.pizza;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cheeses")
public class Cheese extends Ingredient {

    private String name;
    private BigDecimal price;

    public Cheese() {
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

package pizzaco.domain.entities.ingredients;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cheeses")
public class Cheese extends Ingredient {

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

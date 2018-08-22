package pizzaco.domain.entities.ingredients;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vegetables")
public class Vegetable extends Ingredient {

    private BigDecimal price;

    public Vegetable() {
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

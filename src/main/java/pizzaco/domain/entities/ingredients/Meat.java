package pizzaco.domain.entities.ingredients;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "meats")
public class Meat extends Ingredient {

    private BigDecimal price;

    public Meat() {
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

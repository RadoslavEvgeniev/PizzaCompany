package pizzaco.domain.entities;

import pizzaco.domain.entities.ingredients.Size;
import pizzaco.domain.entities.menu.Dip;
import pizzaco.domain.entities.menu.Drink;
import pizzaco.domain.entities.menu.Pizza;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    private Pizza pizza;
    private Size size;
    private Dip dip;
    private Drink drink;
    private BigDecimal price;

    public Offer() {
    }

    @ManyToOne(targetEntity = Pizza.class)
    @JoinColumn(name = "pizza_id")
    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    @ManyToOne(targetEntity = Size.class)
    @JoinColumn(name = "size_id")
    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @ManyToOne(targetEntity = Dip.class)
    @JoinColumn(name = "dip_id")
    public Dip getDip() {
        return this.dip;
    }

    public void setDip(Dip dip) {
        this.dip = dip;
    }

    @ManyToOne(targetEntity = Drink.class)
    @JoinColumn(name = "drink_id")
    public Drink getDrink() {
        return this.drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

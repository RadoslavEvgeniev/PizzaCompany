package pizzaco.domain.entities.order;

import pizzaco.domain.entities.BaseEntity;
import pizzaco.domain.entities.ingredients.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ordered_pizza")
public class OrderedPizza extends BaseEntity {

    private BigDecimal price;
    private String description;
    private Size size;
    private Dough dough;
    private Sauce sauce;
    private Set<Spice> spices;
    private Set<Cheese> cheeses;
    private Set<Meat> meats;
    private Set<Vegetable> vegetables;

    public OrderedPizza() {
    }

    @Column(name = "price", nullable = false)
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = Size.class)
    @JoinTable(name = "ordered_pizzas_sizes"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "size_id"))
    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @ManyToOne(targetEntity = Dough.class)
    @JoinTable(name = "ordered_pizzas_doughs"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "size_id"))
    public Dough getDough() {
        return this.dough;
    }

    public void setDough(Dough dough) {
        this.dough = dough;
    }

    @ManyToOne(targetEntity = Sauce.class)
    @JoinTable(name = "ordered_pizzas_sauces"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "sauce_id"))
    public Sauce getSauce() {
        return this.sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    @ManyToMany(targetEntity = Spice.class)
    @JoinTable(name = "ordered_pizzas_spices"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "spice_id"))
    public Set<Spice> getSpices() {
        return this.spices;
    }

    public void setSpices(Set<Spice> spices) {
        this.spices = spices;
    }

    @ManyToMany(targetEntity = Cheese.class)
    @JoinTable(name = "ordered_pizzas_cheeses"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "cheese_id"))
    public Set<Cheese> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(Set<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    @ManyToMany(targetEntity = Meat.class)
    @JoinTable(name = "ordered_pizzas_meats"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "meat_id"))
    public Set<Meat> getMeats() {
        return this.meats;
    }

    public void setMeats(Set<Meat> meats) {
        this.meats = meats;
    }

    @ManyToMany(targetEntity = Vegetable.class)
    @JoinTable(name = "ordered_pizzas_vegetables"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "vegetable_id"))
    public Set<Vegetable> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(Set<Vegetable> vegetables) {
        this.vegetables = vegetables;
    }
}

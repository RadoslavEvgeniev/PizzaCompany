package pizzaco.domain.entities.menu;

import org.hibernate.annotations.GenericGenerator;
import pizzaco.domain.entities.BaseEntity;
import pizzaco.domain.entities.pizza.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza extends MenuItem {

    private String description;
    private Sauce sauce;
    private Set<Spice> spices;
    private Set<Cheese> cheeses;
    private Set<Meat> meats;
    private Set<Vegetable> vegetables;

    public Pizza() {
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = Sauce.class)
    @JoinTable(name = "pizzas_sauces"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "sauce_id"))
    public Sauce getSauce() {
        return this.sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    @ManyToMany(targetEntity = Spice.class)
    @JoinTable(name = "pizzas_spices"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "spice_id"))
    public Set<Spice> getSpices() {
        return this.spices;
    }

    public void setSpices(Set<Spice> spices) {
        this.spices = spices;
    }

    @ManyToMany(targetEntity = Cheese.class)
    @JoinTable(name = "pizzas_cheeses"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "cheese_id"))
    public Set<Cheese> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(Set<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    @ManyToMany(targetEntity = Meat.class)
    @JoinTable(name = "pizzas_meats"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "meat_id"))
    public Set<Meat> getMeats() {
        return this.meats;
    }

    public void setMeats(Set<Meat> meats) {
        this.meats = meats;
    }

    @ManyToMany(targetEntity = Vegetable.class)
    @JoinTable(name = "pizzas_vegetables"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "vegetable_id"))
    public Set<Vegetable> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(Set<Vegetable> vegetables) {
        this.vegetables = vegetables;
    }
}

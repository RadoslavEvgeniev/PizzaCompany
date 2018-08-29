package pizzaco.domain.entities;

import pizzaco.domain.entities.menu.Dip;
import pizzaco.domain.entities.menu.Drink;
import pizzaco.domain.entities.menu.Pasta;
import pizzaco.domain.entities.menu.Pizza;
import pizzaco.domain.entities.order.OrderedPizza;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private User user;
    private Address address;
    private boolean isFinished;
    private LocalDateTime finishDateTime;
    private List<Drink> drinks;
    private List<Dip> dips;
    private List<Pasta> pastas;
    private List<OrderedPizza> pizzas;
    private BigDecimal totalPrice;

    public Order() {

    }

    @ManyToOne(targetEntity = User.class)
    @JoinTable(name = "orders_users"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "is_finished")
    public boolean isFinished() {
        return this.isFinished;
    }

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Column(name = "finish_date_time")
    public LocalDateTime getFinishDateTime() {
        return this.finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    @ManyToMany(targetEntity = Drink.class)
    @JoinTable(name = "orders_drinks"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "drink_id"))
    public List<Drink> getDrinks() {
        return this.drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    @ManyToMany(targetEntity = Dip.class)
    @JoinTable(name = "orders_dips"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "dip_id"))
    public List<Dip> getDips() {
        return this.dips;
    }

    public void setDips(List<Dip> dips) {
        this.dips = dips;
    }

    @ManyToMany(targetEntity = Pasta.class)
    @JoinTable(name = "orders_pastas"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "pasta_id"))
    public List<Pasta> getPastas() {
        return this.pastas;
    }

    public void setPastas(List<Pasta> pastas) {
        this.pastas = pastas;
    }

    @ManyToMany(targetEntity = OrderedPizza.class)
    @JoinTable(name = "orders_pizzas"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    public List<OrderedPizza> getPizzas() {
        return this.pizzas;
    }

    public void setPizzas(List<OrderedPizza> pizzas) {
        this.pizzas = pizzas;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

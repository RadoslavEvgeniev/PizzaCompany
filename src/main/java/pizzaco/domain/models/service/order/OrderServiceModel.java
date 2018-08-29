package pizzaco.domain.models.service.order;

import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.domain.models.service.UserServiceModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.order.OrderedPizzaServiceModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private UserServiceModel user;
    private AddressServiceModel address;
    private boolean isFinished;
    private LocalDateTime finishDateTime;
    private List<DrinkServiceModel> drinks;
    private List<DipServiceModel> dips;
    private List<PastaServiceModel> pastas;
    private List<OrderedPizzaServiceModel> pizzas;
    private BigDecimal totalPrice;

    public OrderServiceModel() {
        this.drinks = new ArrayList<>();
        this.dips = new ArrayList<>();
        this.pastas = new ArrayList<>();
        this.pizzas = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserServiceModel getUser() {
        return this.user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public AddressServiceModel getAddress() {
        return this.address;
    }

    public void setAddress(AddressServiceModel address) {
        this.address = address;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public LocalDateTime getFinishDateTime() {
        return this.finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public List<DrinkServiceModel> getDrinks() {
        return this.drinks;
    }

    public void setDrinks(List<DrinkServiceModel> drinks) {
        this.drinks = drinks;
    }

    public List<DipServiceModel> getDips() {
        return this.dips;
    }

    public void setDips(List<DipServiceModel> dips) {
        this.dips = dips;
    }

    public List<PastaServiceModel> getPastas() {
        return this.pastas;
    }

    public void setPastas(List<PastaServiceModel> pastas) {
        this.pastas = pastas;
    }

    public List<OrderedPizzaServiceModel> getPizzas() {
        return this.pizzas;
    }

    public void setPizzas(List<OrderedPizzaServiceModel> pizzas) {
        this.pizzas = pizzas;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

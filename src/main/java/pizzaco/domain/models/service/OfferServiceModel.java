package pizzaco.domain.models.service;

import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;
import pizzaco.domain.models.view.ingredients.SizeViewModel;

import java.math.BigDecimal;

public class OfferServiceModel {

    private String id;
    private PizzaServiceModel pizza;
    private SizeViewModel size;
    private DipServiceModel dip;
    private DrinkServiceModel drink;
    private BigDecimal price;

    public OfferServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PizzaServiceModel getPizza() {
        return this.pizza;
    }

    public void setPizza(PizzaServiceModel pizza) {
        this.pizza = pizza;
    }

    public SizeViewModel getSize() {
        return this.size;
    }

    public void setSize(SizeViewModel size) {
        this.size = size;
    }

    public DipServiceModel getDip() {
        return this.dip;
    }

    public void setDip(DipServiceModel dip) {
        this.dip = dip;
    }

    public DrinkServiceModel getDrink() {
        return this.drink;
    }

    public void setDrink(DrinkServiceModel drink) {
        this.drink = drink;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

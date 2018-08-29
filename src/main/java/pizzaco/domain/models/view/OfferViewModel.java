package pizzaco.domain.models.view;

import pizzaco.domain.models.view.ingredients.SizeViewModel;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PizzaViewModel;

import java.math.BigDecimal;

public class OfferViewModel {

    private String id;
    private PizzaViewModel pizza;
    private SizeViewModel size;
    private DipViewModel dip;
    private DrinkViewModel drink;
    private BigDecimal price;

    public OfferViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PizzaViewModel getPizza() {
        return this.pizza;
    }

    public void setPizza(PizzaViewModel pizza) {
        this.pizza = pizza;
    }

    public SizeViewModel getSize() {
        return this.size;
    }

    public void setSize(SizeViewModel size) {
        this.size = size;
    }

    public DipViewModel getDip() {
        return this.dip;
    }

    public void setDip(DipViewModel dip) {
        this.dip = dip;
    }

    public DrinkViewModel getDrink() {
        return this.drink;
    }

    public void setDrink(DrinkViewModel drink) {
        this.drink = drink;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

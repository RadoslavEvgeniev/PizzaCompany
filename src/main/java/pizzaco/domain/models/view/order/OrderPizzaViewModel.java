package pizzaco.domain.models.view.order;

import pizzaco.domain.models.view.AllIngredientsViewModel;
import pizzaco.domain.models.view.menu.PizzaViewModel;

public class OrderPizzaViewModel {

    private PizzaViewModel pizza;
    private AllIngredientsViewModel ingredients;

    public OrderPizzaViewModel() {
    }

    public PizzaViewModel getPizza() {
        return this.pizza;
    }

    public void setPizza(PizzaViewModel pizza) {
        this.pizza = pizza;
    }

    public AllIngredientsViewModel getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(AllIngredientsViewModel ingredients) {
        this.ingredients = ingredients;
    }
}

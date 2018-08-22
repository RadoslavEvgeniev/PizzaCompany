package pizzaco.service;

import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;

import java.util.List;

public interface MenuService {

    boolean addDrink(DrinkServiceModel drinkServiceModel);

    boolean addDip(DipServiceModel dipServiceModel);

    boolean addPasta(PastaServiceModel pastaServiceModel);

    boolean addPizza(PizzaServiceModel pizzaServiceModel);

    List<PizzaServiceModel> getPizzaOrderedByName();

    List<PastaServiceModel> getPastaOrderedByName();

    List<DipServiceModel> getDipsOrderedByName();

    List<DrinkServiceModel> getDrinksOrderedByName();
}

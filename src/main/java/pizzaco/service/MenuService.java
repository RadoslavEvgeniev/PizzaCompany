package pizzaco.service;

import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;

import java.util.List;

public interface MenuService {

    boolean addPizza(PizzaServiceModel pizzaServiceModel);

    boolean addPasta(PastaServiceModel pastaServiceModel);

    boolean addDip(DipServiceModel dipServiceModel);

    boolean addDrink(DrinkServiceModel drinkServiceModel);

    List<PizzaServiceModel> getPizzaOrderedByName();

    List<PastaServiceModel> getPastaOrderedByName();

    List<DipServiceModel> getDipsOrderedByName();

    List<DrinkServiceModel> getDrinksOrderedByName();

    PizzaServiceModel getPizzaByName(String name);

    PastaServiceModel getPastaByName(String name);

    DipServiceModel getDipByName(String name);

    DrinkServiceModel getDrinkByName(String name);
}

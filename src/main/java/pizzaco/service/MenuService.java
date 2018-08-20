package pizzaco.service;

import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;

public interface MenuService {

    boolean addDrink(DrinkServiceModel drinkServiceModel);

    boolean addDip(DipServiceModel dipServiceModel);

    boolean addPasta(PastaServiceModel pastaServiceModel);

    boolean addPizza(PizzaServiceModel pizzaServiceModel);
}

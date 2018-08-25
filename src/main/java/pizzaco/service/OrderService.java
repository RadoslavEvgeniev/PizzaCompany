package pizzaco.service;

import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.domain.models.service.OrderServiceModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;

public interface OrderService {

    OrderServiceModel getUserUnfinishedOrder(String email);

    OrderServiceModel getOrderById(String id);

    boolean setOrderAddress(OrderServiceModel orderServiceModel, AddressServiceModel addressServiceModel);

    boolean addPizzaToOrder(OrderServiceModel orderServiceModel, PizzaServiceModel pizzaServiceModel);

    boolean removePizzaFromOrder(OrderServiceModel orderServiceModel, PizzaServiceModel pizzaServiceModel);

    boolean addPastaToOrder(OrderServiceModel orderServiceModel, PastaServiceModel pastaServiceModel);

    boolean removePastaFromOrder(OrderServiceModel orderServiceModel, PastaServiceModel pastaServiceModel);

    boolean addDipToOrder(OrderServiceModel orderServiceModel, DipServiceModel dipServiceModel);

    boolean removeDipFromOrder(OrderServiceModel orderServiceModel, DipServiceModel dipServiceModel);

    boolean addDrinkToOrder(OrderServiceModel orderServiceModel, DrinkServiceModel drinkServiceModel);

    boolean removeDrinkFromOrder(OrderServiceModel orderServiceModel, DrinkServiceModel drinkServiceModel);

    boolean finishOrder(OrderServiceModel orderServiceModel);
}

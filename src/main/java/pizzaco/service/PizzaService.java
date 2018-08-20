package pizzaco.service;

import pizzaco.domain.models.service.ingredients.*;

public interface PizzaService {

    boolean addSize(SizeServiceModel sizeServiceModel);

    boolean addDough(DoughServiceModel doughServiceModel);

    boolean addSauce(SauceServiceModel sauceServiceModel);

    boolean addSpice(SpiceServiceModel spiceServiceModel);

    boolean addCheese(CheeseServiceModel cheeseServiceModel);

    boolean addMeat(MeatServiceModel meatServiceModel);

    boolean addVegetable(VegetableServiceModel vegetableServiceModel);
}

package pizzaco.service;

import pizzaco.domain.models.service.ingredients.*;
import pizzaco.domain.models.view.ingredients.SpiceViewModel;

import java.util.List;

public interface IngredientService {

    boolean addSize(SizeServiceModel sizeServiceModel);

    boolean addDough(DoughServiceModel doughServiceModel);

    boolean addSauce(SauceServiceModel sauceServiceModel);

    boolean addSpice(SpiceServiceModel spiceServiceModel);

    boolean addCheese(CheeseServiceModel cheeseServiceModel);

    boolean addMeat(MeatServiceModel meatServiceModel);

    boolean addVegetable(VegetableServiceModel vegetableServiceModel);

    List<SizeServiceModel> getSizesOrderedByNumberOfSlices();

    List<DoughServiceModel> getDoughsOrderedByName();

    List<SauceServiceModel> getSaucesOrderedByName();

    List<SpiceServiceModel> getSpicesOrderedByName();

    List<CheeseServiceModel> getCheesesOrderedByName();

    List<MeatServiceModel> getMeatsOrderedByName();

    List<VegetableServiceModel> getVegetablesOrderedByName();

    SizeServiceModel getSizeBySize(String size);

    DoughServiceModel getDoughByName(String name);

    SauceServiceModel getSauceByName(String name);

    SpiceServiceModel getSpiceByName(String name);

    CheeseServiceModel getCheeseByName(String name);

    MeatServiceModel getMeatByName(String name);

    VegetableServiceModel getVegetableByName(String name);
}

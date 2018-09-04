package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizzaco.common.Constants;
import pizzaco.domain.entities.ingredients.*;
import pizzaco.domain.models.service.ingredients.*;
import pizzaco.errors.IngredientAlreadyExistsException;
import pizzaco.errors.NameNotFoundException;
import pizzaco.repository.ingredients.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final SizeRepository sizeRepository;
    private final DoughRepository doughRepository;
    private final SauceRepository sauceRepository;
    private final SpiceRepository spiceRepository;
    private final CheeseRepository cheeseRepository;
    private final MeatRepository meatRepository;
    private final VegetableRepository vegetableRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientServiceImpl(SizeRepository sizeRepository, DoughRepository doughRepository, SauceRepository sauceRepository, SpiceRepository spiceRepository, CheeseRepository cheeseRepository, MeatRepository meatRepository, VegetableRepository vegetableRepository, ModelMapper modelMapper) {
        this.sizeRepository = sizeRepository;
        this.doughRepository = doughRepository;
        this.sauceRepository = sauceRepository;
        this.spiceRepository = spiceRepository;
        this.cheeseRepository = cheeseRepository;
        this.meatRepository = meatRepository;
        this.vegetableRepository = vegetableRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addSize(SizeServiceModel sizeServiceModel) {
        Size sizeEntity = this.sizeRepository.findBySize(sizeServiceModel.getSize()).orElse(null);

        this.checkIngredientExistence(sizeEntity, Size.class.getSimpleName());

        sizeEntity = this.modelMapper.map(sizeServiceModel, Size.class);

        this.sizeRepository.save(sizeEntity);

        return true;
    }

    @Override
    public boolean addDough(DoughServiceModel doughServiceModel) {
        Dough doughEntity = this.doughRepository.findByName(doughServiceModel.getName()).orElse(null);

        this.checkIngredientExistence(doughEntity, Dough.class.getSimpleName());

        doughEntity = this.modelMapper.map(doughServiceModel, Dough.class);

        this.doughRepository.save(doughEntity);

        return true;
    }

    @Override
    public boolean addSauce(SauceServiceModel sauceServiceModel) {
        Sauce sauceEntity = this.sauceRepository.findByName(sauceServiceModel.getName()).orElse(null);

        this.checkIngredientExistence(sauceEntity, Sauce.class.getSimpleName());

        sauceEntity = this.modelMapper.map(sauceServiceModel, Sauce.class);

        this.sauceRepository.save(sauceEntity);

        return true;
    }

    @Override
    public boolean addSpice(SpiceServiceModel spiceServiceModel) {
        Spice spiceEntity = this.spiceRepository.findByName(spiceServiceModel.getName()).orElse(null);

        this.checkIngredientExistence(spiceEntity, Spice.class.getSimpleName());

        spiceEntity = this.modelMapper.map(spiceServiceModel, Spice.class);

        this.spiceRepository.save(spiceEntity);

        return true;
    }

    @Override
    public boolean addCheese(CheeseServiceModel cheeseServiceModel) {
        Cheese cheeseEntity = this.cheeseRepository.findByName(cheeseServiceModel.getName()).orElse(null);

        this.checkIngredientExistence(cheeseEntity, Cheese.class.getSimpleName());

        cheeseEntity = this.modelMapper.map(cheeseServiceModel, Cheese.class);

        this.cheeseRepository.save(cheeseEntity);

        return true;
    }

    @Override
    public boolean addMeat(MeatServiceModel meatServiceModel) {
        Meat meatEntity = this.meatRepository.findByName(meatServiceModel.getName()).orElse(null);

        this.checkIngredientExistence(meatEntity, Meat.class.getSimpleName());

        meatEntity = this.modelMapper.map(meatServiceModel, Meat.class);

        this.meatRepository.save(meatEntity);

        return true;
    }

    @Override
    public boolean addVegetable(VegetableServiceModel vegetableServiceModel) {
        Vegetable vegetableEntity = this.vegetableRepository.findByName(vegetableServiceModel.getName()).orElse(null);

        this.checkIngredientExistence(vegetableEntity, Vegetable.class.getSimpleName());

        vegetableEntity = this.modelMapper.map(vegetableServiceModel, Vegetable.class);

        this.vegetableRepository.save(vegetableEntity);

        return true;
    }

    @Override
    public List<SizeServiceModel> getSizesOrderedByNumberOfSlices() {
        return this.sizeRepository.findAllOrderByNumberOfSlices()
                .stream()
                .map(size -> this.modelMapper.map(size, SizeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoughServiceModel> getDoughsOrderedByName() {
        return this.doughRepository.findAllOrderedAlphabetically()
                .stream()
                .map(dough -> this.modelMapper.map(dough, DoughServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SauceServiceModel> getSaucesOrderedByName() {
        return this.sauceRepository.findAllOrderedAlphabetically()
                .stream()
                .map(sauce -> this.modelMapper.map(sauce, SauceServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SpiceServiceModel> getSpicesOrderedByName() {
        return this.spiceRepository.findAllOrderedAlphabetically()
                .stream()
                .map(spice -> this.modelMapper.map(spice, SpiceServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CheeseServiceModel> getCheesesOrderedByName() {
        return this.cheeseRepository.findAllOrderedAlphabetically()
                .stream()
                .map(cheese -> this.modelMapper.map(cheese, CheeseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeatServiceModel> getMeatsOrderedByName() {
        return this.meatRepository.findAllOrderedAlphabetically()
                .stream()
                .map(meat -> this.modelMapper.map(meat, MeatServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VegetableServiceModel> getVegetablesOrderedByName() {
        return this.vegetableRepository.findAllOrderedAlphabetically()
                .stream()
                .map(vegetable -> this.modelMapper.map(vegetable, VegetableServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public SizeServiceModel getSizeBySize(String size) {
        Size sizeEntity = this.sizeRepository.findBySize(size).orElse(null);

        this.checkIngredientExistence(sizeEntity);

        return this.modelMapper.map(sizeEntity, SizeServiceModel.class);
    }

    @Override
    public DoughServiceModel getDoughByName(String name) {
        Dough doughEntity = this.doughRepository.findByName(name).orElse(null);

        this.checkIngredientExistence(doughEntity);

        return this.modelMapper.map(doughEntity, DoughServiceModel.class);
    }

    @Override
    public SauceServiceModel getSauceByName(String name) {
        Sauce sauceEntity = this.sauceRepository.findByName(name).orElse(null);

        this.checkIngredientExistence(sauceEntity);

        return this.modelMapper.map(sauceEntity, SauceServiceModel.class);
    }

    @Override
    public SpiceServiceModel getSpiceByName(String name) {
        Spice spiceEntity = this.spiceRepository.findByName(name).orElse(null);

        this.checkIngredientExistence(spiceEntity);

        return this.modelMapper.map(spiceEntity, SpiceServiceModel.class);
    }

    @Override
    public CheeseServiceModel getCheeseByName(String name) {
        Cheese cheeseEntity = this.cheeseRepository.findByName(name).orElse(null);

        this.checkIngredientExistence(cheeseEntity);

        return this.modelMapper.map(cheeseEntity, CheeseServiceModel.class);
    }

    @Override
    public MeatServiceModel getMeatByName(String name) {
        Meat meatEntity = this.meatRepository.findByName(name).orElse(null);

        this.checkIngredientExistence(meatEntity);

        return this.modelMapper.map(meatEntity, MeatServiceModel.class);
    }

    @Override
    public VegetableServiceModel getVegetableByName(String name) {
        Vegetable vegetableEntity = this.vegetableRepository.findByName(name).orElse(null);

        this.checkIngredientExistence(vegetableEntity);

        return this.modelMapper.map(vegetableEntity, VegetableServiceModel.class);
    }

    private void checkIngredientExistence(Object ingredientEntity, String ingredientName) {
        if (ingredientEntity != null) {
            throw new IngredientAlreadyExistsException(String.format(Constants.INGREDIENT_ALREADY_EXISTS, ingredientName));
        }
    }

    private void checkIngredientExistence(Object ingredientEntity) {
        if (ingredientEntity == null) {
            throw new NameNotFoundException(Constants.WRONG_NON_EXISTENT_NAME);
        }
    }
}

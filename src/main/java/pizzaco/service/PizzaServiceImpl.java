package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.pizza.*;
import pizzaco.domain.models.service.ingredients.*;
import pizzaco.errors.IngredientAlreadyExistsException;
import pizzaco.repository.ingredients.*;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final SizeRepository sizeRepository;
    private final DoughRepository doughRepository;
    private final SauceRepository sauceRepository;
    private final SpiceRepository spiceRepository;
    private final CheeseRepository cheeseRepository;
    private final MeatRepository meatRepository;
    private final VegetableRepository vegetableRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PizzaServiceImpl(SizeRepository sizeRepository, DoughRepository doughRepository, SauceRepository sauceRepository, SpiceRepository spiceRepository, CheeseRepository cheeseRepository, MeatRepository meatRepository, VegetableRepository vegetableRepository, ModelMapper modelMapper) {
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

        if (sizeEntity != null) {
            throw new IngredientAlreadyExistsException("Size already exists.");
        }

        sizeEntity = this.modelMapper.map(sizeServiceModel, Size.class);

        this.sizeRepository.save(sizeEntity);

        return true;
    }

    @Override
    public boolean addDough(DoughServiceModel doughServiceModel) {
        Dough doughEntity = this.doughRepository.findByName(doughServiceModel.getName()).orElse(null);

        if (doughEntity != null) {
            throw new IngredientAlreadyExistsException("Dough already exists.");
        }

        doughEntity = this.modelMapper.map(doughServiceModel, Dough.class);

        this.doughRepository.save(doughEntity);

        return true;
    }

    @Override
    public boolean addSauce(SauceServiceModel sauceServiceModel) {
        Sauce sauceEntity = this.sauceRepository.findByName(sauceServiceModel.getName()).orElse(null);

        if (sauceEntity != null) {
            throw new IngredientAlreadyExistsException("Sauce already exists.");
        }

        sauceEntity = this.modelMapper.map(sauceServiceModel, Sauce.class);

        this.sauceRepository.save(sauceEntity);

        return true;
    }

    @Override
    public boolean addSpice(SpiceServiceModel spiceServiceModel) {
        Spice spiceEntity = this.spiceRepository.findByName(spiceServiceModel.getName()).orElse(null);

        if (spiceEntity != null) {
            throw new IngredientAlreadyExistsException("Spice already exists.");
        }

        spiceEntity = this.modelMapper.map(spiceServiceModel, Spice.class);

        this.spiceRepository.save(spiceEntity);

        return true;
    }

    @Override
    public boolean addCheese(CheeseServiceModel cheeseServiceModel) {
        Cheese cheeseEntity = this.cheeseRepository.findByName(cheeseServiceModel.getName()).orElse(null);

        if (cheeseEntity != null) {
            throw new IngredientAlreadyExistsException("Cheese already exists.");
        }

        cheeseEntity = this.modelMapper.map(cheeseServiceModel, Cheese.class);

        this.cheeseRepository.save(cheeseEntity);

        return true;
    }

    @Override
    public boolean addMeat(MeatServiceModel meatServiceModel) {
        Meat meatEntity = this.meatRepository.findByName(meatServiceModel.getName()).orElse(null);

        if (meatEntity != null) {
            throw new IngredientAlreadyExistsException("Meat already exists.");
        }

        meatEntity = this.modelMapper.map(meatServiceModel, Meat.class);

        this.meatRepository.save(meatEntity);

        return true;
    }

    @Override
    public boolean addVegetable(VegetableServiceModel vegetableServiceModel) {
        Vegetable vegetableEntity = this.vegetableRepository.findByName(vegetableServiceModel.getName()).orElse(null);

        if (vegetableEntity != null) {
            throw new IngredientAlreadyExistsException("Vegetable already exists.");
        }

        vegetableEntity = this.modelMapper.map(vegetableServiceModel, Vegetable.class);

        this.vegetableRepository.save(vegetableEntity);

        return true;
    }
}

package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return null;
    }

    @Override
    public DoughServiceModel getDoughByName(String name) {

        return null;
    }

    @Override
    public SauceServiceModel getSauceByName(String name) {
        Sauce sauceEntity = this.sauceRepository.findByName(name).orElse(null);

        if (sauceEntity == null) {
            throw new NameNotFoundException("");
        }

        return this.modelMapper.map(sauceEntity, SauceServiceModel.class);
    }

    @Override
    public SpiceServiceModel getSpiceByName(String name) {
        Spice spiceEntity = this.spiceRepository.findByName(name).orElse(null);

        if (spiceEntity == null) {
            throw new NameNotFoundException("");
        }

        return this.modelMapper.map(spiceEntity, SpiceServiceModel.class);
    }

    @Override
    public CheeseServiceModel getCheeseByName(String name) {
        Cheese cheeseEntity = this.cheeseRepository.findByName(name).orElse(null);

        if (cheeseEntity == null) {
            throw new NameNotFoundException("");
        }

        return this.modelMapper.map(cheeseEntity, CheeseServiceModel.class);
    }

    @Override
    public MeatServiceModel getMeatByName(String name) {
        Meat meatEntity = this.meatRepository.findByName(name).orElse(null);

        if (meatEntity == null) {
            throw new NameNotFoundException("");
        }

        return this.modelMapper.map(meatEntity, MeatServiceModel.class);
    }

    @Override
    public VegetableServiceModel getVegetableByName(String name) {
        Vegetable vegetableEntity = this.vegetableRepository.findByName(name).orElse(null);

        if (vegetableEntity == null) {
            throw new NameNotFoundException("");
        }

        return this.modelMapper.map(vegetableEntity, VegetableServiceModel.class);
    }
}

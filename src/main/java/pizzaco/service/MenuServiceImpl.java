package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.menu.Dip;
import pizzaco.domain.entities.menu.Drink;
import pizzaco.domain.entities.menu.Pasta;
import pizzaco.domain.entities.menu.Pizza;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;
import pizzaco.errors.ItemAlreadyExistsException;
import pizzaco.repository.menu.DipRepository;
import pizzaco.repository.menu.DrinkRepository;
import pizzaco.repository.menu.PastaRepository;
import pizzaco.repository.menu.PizzaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final DrinkRepository drinkRepository;
    private final DipRepository dipRepository;
    private final PastaRepository pastaRepository;
    private final PizzaRepository pizzaRepository;
    private final ModelMapper modelMapper;

    public MenuServiceImpl(DrinkRepository drinkRepository, DipRepository dipRepository, PastaRepository pastaRepository, PizzaRepository pizzaRepository, ModelMapper modelMapper) {
        this.drinkRepository = drinkRepository;
        this.dipRepository = dipRepository;
        this.pastaRepository = pastaRepository;
        this.pizzaRepository = pizzaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addDrink(DrinkServiceModel drinkServiceModel) {
        Drink drinkEntity = this.drinkRepository.findByName(drinkServiceModel.getName()).orElse(null);

        if (drinkEntity != null) {
            throw new ItemAlreadyExistsException("Drink already exists.");
        }

        drinkEntity = this.modelMapper.map(drinkServiceModel, Drink.class);

        this.drinkRepository.save(drinkEntity);

        return true;
    }

    @Override
    public boolean addDip(DipServiceModel dipServiceModel) {
        Dip dipEntity = this.dipRepository.findByName(dipServiceModel.getName()).orElse(null);

        if (dipEntity != null) {
            throw new ItemAlreadyExistsException("Dip already exists.");
        }

        dipEntity = this.modelMapper.map(dipServiceModel, Dip.class);

        this.dipRepository.save(dipEntity);

        return true;
    }

    @Override
    public boolean addPasta(PastaServiceModel pastaServiceModel) {
        Pasta pastaEntity = this.pastaRepository.findByName(pastaServiceModel.getName()).orElse(null);

        if (pastaEntity != null) {
            throw new ItemAlreadyExistsException("Pasta already exists.");
        }

        pastaEntity = this.modelMapper.map(pastaServiceModel, Pasta.class);

        this.pastaRepository.save(pastaEntity);

        return true;
    }

    @Override
    public boolean addPizza(PizzaServiceModel pizzaServiceModel) {
        Pizza pizzaEntity = this.pizzaRepository.findByName(pizzaServiceModel.getName()).orElse(null);

        if (pizzaEntity != null) {
            throw new ItemAlreadyExistsException("Pizza already exists.");
        }

        pizzaEntity = this.modelMapper.map(pizzaServiceModel, Pizza.class);

        this.pizzaRepository.save(pizzaEntity);

        return true;
    }

    @Override
    public List<PizzaServiceModel> getPizzaOrderedByName() {
        return this.pizzaRepository.findAllOrderedAlphabetically()
                .stream()
                .map(pizza -> this.modelMapper.map(pizza, PizzaServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PastaServiceModel> getPastaOrderedByName() {
        return this.pastaRepository.findAllOrderedAlphabetically()
                .stream()
                .map(pasta -> this.modelMapper.map(pasta, PastaServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DipServiceModel> getDipsOrderedByName() {
        return this.dipRepository.findAllOrderedAlphabetically()
                .stream()
                .map(dip -> this.modelMapper.map(dip, DipServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DrinkServiceModel> getDrinksOrderedByName() {
        return this.drinkRepository.findAllOrderedAlphabetically()
                .stream()
                .map(drink -> this.modelMapper.map(drink, DrinkServiceModel.class))
                .collect(Collectors.toList());
    }
}

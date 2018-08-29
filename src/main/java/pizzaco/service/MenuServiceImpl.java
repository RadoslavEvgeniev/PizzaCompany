package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.Offer;
import pizzaco.domain.entities.ingredients.Size;
import pizzaco.domain.entities.menu.Dip;
import pizzaco.domain.entities.menu.Drink;
import pizzaco.domain.entities.menu.Pasta;
import pizzaco.domain.entities.menu.Pizza;
import pizzaco.domain.models.service.OfferServiceModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;
import pizzaco.errors.ItemAlreadyExistsException;
import pizzaco.errors.NameNotFoundException;
import pizzaco.repository.OfferRepository;
import pizzaco.repository.ingredients.SizeRepository;
import pizzaco.repository.menu.DipRepository;
import pizzaco.repository.menu.DrinkRepository;
import pizzaco.repository.menu.PastaRepository;
import pizzaco.repository.menu.PizzaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final PizzaRepository pizzaRepository;
    private final PastaRepository pastaRepository;
    private final DipRepository dipRepository;
    private final DrinkRepository drinkRepository;
    private final OfferRepository offerRepository;
    private final SizeRepository sizeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuServiceImpl(PizzaRepository pizzaRepository, PastaRepository pastaRepository, DipRepository dipRepository, DrinkRepository drinkRepository, OfferRepository offerRepository, SizeRepository sizeRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.pastaRepository = pastaRepository;
        this.dipRepository = dipRepository;
        this.drinkRepository = drinkRepository;
        this.offerRepository = offerRepository;
        this.sizeRepository = sizeRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public PizzaServiceModel getPizzaByName(String name) {
        Pizza pizzaEntity = this.pizzaRepository.findByName(name).orElse(null);

        if (pizzaEntity == null) {
            throw new NameNotFoundException("Wrong or non-existent name.");
        }

        return this.modelMapper.map(pizzaEntity, PizzaServiceModel.class);
    }

    @Override
    public PastaServiceModel getPastaByName(String name) {
        Pasta pastaEntity = this.pastaRepository.findByName(name).orElse(null);

        if (pastaEntity == null) {
            throw new NameNotFoundException("Wrong or non-existent name.");
        }

        return this.modelMapper.map(pastaEntity, PastaServiceModel.class);
    }

    @Override
    public DipServiceModel getDipByName(String name) {
        Dip dipEntity = this.dipRepository.findByName(name).orElse(null);

        if (dipEntity == null) {
            throw new NameNotFoundException("Wrong or non-existent name.");
        }

        return this.modelMapper.map(dipEntity, DipServiceModel.class);
    }

    @Override
    public DrinkServiceModel getDrinkByName(String name) {
        Drink drinkEntity = this.drinkRepository.findByName(name).orElse(null);

        if (drinkEntity == null) {
            throw new NameNotFoundException("Wrong or non-existent name.");
        }

        return this.modelMapper.map(drinkEntity, DrinkServiceModel.class);
    }

    @Override
    public List<OfferServiceModel> getOffers() {
        return this.offerRepository.findAll()
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 300000)
    private void getRandomMenuItems() {
        this.offerRepository.deleteAll();

        for (int i = 0; i < 4; i++) {
            Random rnd = new Random();

            int index = rnd.nextInt((int)this.pizzaRepository.count());
            Pizza pizzaEntity = this.pizzaRepository.findAll().get(index);

            index = rnd.nextInt((int)this.sizeRepository.count());
            Size sizeEntity = this.sizeRepository.findAll().get(index);

            index = rnd.nextInt((int)this.dipRepository.count());
            Dip dipEntity = this.dipRepository.findAll().get(index);

            index = rnd.nextInt((int)this.drinkRepository.count());
            Drink drinkEntity = this.drinkRepository.findAll().get(index);

            Offer offer = new Offer();
            offer.setPizza(pizzaEntity);
            offer.setSize(sizeEntity);
            offer.setDip(dipEntity);
            offer.setDrink(drinkEntity);
            this.calculateOfferPrice(offer);

            this.offerRepository.save(offer);
        }
    }

    private void calculateOfferPrice(Offer offer) {
        offer.setPrice(BigDecimal.ZERO);
        offer.setPrice(offer.getPrice().add(offer.getPizza().getPrice()));
        offer.setPrice(offer.getPrice().add(offer.getSize().getPrice()));
        offer.setPrice(offer.getPrice().add(offer.getDip().getPrice()));
        offer.setPrice(offer.getPrice().add(offer.getDrink().getPrice()));
        offer.setPrice(offer.getPrice().multiply(BigDecimal.valueOf(0.8)));
    }
}

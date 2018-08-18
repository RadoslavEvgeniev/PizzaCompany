package pizzaco.service;

import org.springframework.stereotype.Service;
import pizzaco.domain.entities.menu.Drink;
import pizzaco.domain.models.service.DrinkServiceModel;
import pizzaco.repository.DrinkRepository;

@Service
public class MenuServiceImpl implements MenuService {

    private final DrinkRepository drinkRepository;

    public MenuServiceImpl(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    public boolean addDrink(DrinkServiceModel drinkServiceModel) {
        Drink drinkEntity = this.drinkRepository.findByName(drinkServiceModel.getName()).orElse(null);

        if (drinkEntity != null) {

        }
        return false;
    }
}

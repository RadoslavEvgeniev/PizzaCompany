package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.menu.AddDipBindingModel;
import pizzaco.domain.models.binding.menu.AddDrinkBindingModel;
import pizzaco.domain.models.binding.menu.AddPastaBindingModel;
import pizzaco.domain.models.binding.menu.AddPizzaBindingModel;
import pizzaco.domain.models.service.ingredients.CheeseServiceModel;
import pizzaco.domain.models.service.ingredients.MeatServiceModel;
import pizzaco.domain.models.service.ingredients.SpiceServiceModel;
import pizzaco.domain.models.service.ingredients.VegetableServiceModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;
import pizzaco.domain.models.view.AllIngredientsViewModel;
import pizzaco.domain.models.view.ingredients.*;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;
import pizzaco.domain.models.view.menu.PizzaViewModel;
import pizzaco.errors.ItemAddFailureException;
import pizzaco.service.CloudinaryService;
import pizzaco.service.MenuService;
import pizzaco.service.IngredientService;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final IngredientService ingredientService;
    private final CloudinaryService cloudinaryService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuController(MenuService menuService, IngredientService ingredientService, CloudinaryService cloudinaryService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.ingredientService = ingredientService;
        this.cloudinaryService = cloudinaryService;
        this.jmsTemplate = jmsTemplate;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/addMenuItems")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addMenuItems(@ModelAttribute(name = "allIngredientsViewModel") AllIngredientsViewModel allIngredientsViewModel) {
        prepareIngredientsViewModel(allIngredientsViewModel);

        return super.view("menu/add-menu-items", "allIngredientsViewModel", allIngredientsViewModel);
    }

    @PostMapping("/drinks/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDrink(@Valid @ModelAttribute(name = "addDrinkBindingModel") AddDrinkBindingModel addDrinkBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addDrinkBindingModel.getDrinkImage());

        DrinkServiceModel drinkServiceModel = this.modelMapper.map(addDrinkBindingModel, DrinkServiceModel.class);
        drinkServiceModel.setImageUrl(imageUrl);

        boolean result = this.menuService.addDrink(drinkServiceModel);

        if (!result) {
            throw new ItemAddFailureException("Adding drink " + drinkServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added drink " + drinkServiceModel.getName() + " with price " + drinkServiceModel.getPrice() + "$.");

        return super.redirect("/menu/addMenuItems");
    }

    @PostMapping("/dips/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDip(@Valid @ModelAttribute(name = "addDipBindingModel") AddDipBindingModel addDipBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addDipBindingModel.getDipImage());

        DipServiceModel dipServiceModel = this.modelMapper.map(addDipBindingModel, DipServiceModel.class);
        dipServiceModel.setImageUrl(imageUrl);

        boolean result = this.menuService.addDip(dipServiceModel);

        if (!result) {
            throw new ItemAddFailureException("Adding dip " + dipServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added dip " + dipServiceModel.getName() + " with price " + dipServiceModel.getPrice() + "$.");

        return super.redirect("/menu/addMenuItems");
    }

    @PostMapping("/pasta/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addPasta(@Valid @ModelAttribute(name = "addPastaBindingModel") AddPastaBindingModel addPastaBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addPastaBindingModel.getPastaImage());

        PastaServiceModel pastaServiceModel = this.modelMapper.map(addPastaBindingModel, PastaServiceModel.class);
        pastaServiceModel.setImageUrl(imageUrl);

        boolean result = this.menuService.addPasta(pastaServiceModel);

        if (!result) {
            throw new ItemAddFailureException("Adding pasta " + pastaServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added pasta " + pastaServiceModel.getName() + " with price " + pastaServiceModel.getPrice() + "$.");

        return super.redirect("/menu/addMenuItems");
    }

    @PostMapping("/pizza/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addPizza(@ModelAttribute(name = "addPizzaBindingModel") AddPizzaBindingModel addPizzaBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addPizzaBindingModel.getPizzaImage());

        PizzaServiceModel pizzaServiceModel = this.preparePizzaServiceModel(addPizzaBindingModel);
        pizzaServiceModel.setImageUrl(imageUrl);

        boolean result = this.menuService.addPizza(pizzaServiceModel);

        if (!result) {
            throw new ItemAddFailureException("Adding pizza " + pizzaServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added pizza " + pizzaServiceModel.getName() + " with description " + pizzaServiceModel.getDescription() + ".");

        return super.redirect("/menu/addMenuItems");
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }

    private void prepareIngredientsViewModel(AllIngredientsViewModel allIngredientsViewModel) {
        allIngredientsViewModel
                .setDoughs(
                        this.ingredientService.getDoughsOrderedByName()
                                .stream()
                                .map(dough -> this.modelMapper.map(dough, DoughViewModel.class))
                                .collect(Collectors.toList())
                );

        allIngredientsViewModel
                .setSauces(
                        this.ingredientService.getSaucesOrderedByName()
                                .stream()
                                .map(sauce -> this.modelMapper.map(sauce, SauceViewModel.class))
                                .collect(Collectors.toList())
                );

        allIngredientsViewModel
                .setSpices(
                        this.ingredientService.getSpicesOrderedByName()
                                .stream()
                                .map(spice -> this.modelMapper.map(spice, SpiceViewModel.class))
                                .collect(Collectors.toList())
                );

        allIngredientsViewModel
                .setCheeses(
                        this.ingredientService.getCheesesOrderedByName()
                                .stream()
                                .map(cheese -> this.modelMapper.map(cheese, CheeseViewModel.class))
                                .collect(Collectors.toList())
                );

        allIngredientsViewModel
                .setMeats(
                        this.ingredientService.getMeatsOrderedByName()
                                .stream()
                                .map(meat -> this.modelMapper.map(meat, MeatViewModel.class))
                                .collect(Collectors.toList())
                );

        allIngredientsViewModel
                .setVegetables(
                        this.ingredientService.getVegetablesOrderedByName()
                                .stream()
                                .map(vegetable -> this.modelMapper.map(vegetable, VegetableViewModel.class))
                                .collect(Collectors.toList())
                );
    }

    private PizzaServiceModel preparePizzaServiceModel(AddPizzaBindingModel addPizzaBindingModel) {
        PizzaServiceModel pizzaServiceModel = new PizzaServiceModel();

        pizzaServiceModel.setName(addPizzaBindingModel.getName());
        pizzaServiceModel.setSauce(this.ingredientService.getSauceByName(addPizzaBindingModel.getSauce()));

        pizzaServiceModel.setSpices(addPizzaBindingModel.getSpices()
                .stream()
                .map(this.ingredientService::getSpiceByName)
                .collect(Collectors.toList()));
        pizzaServiceModel.setCheeses(addPizzaBindingModel.getCheeses()
                .stream()
                .map(this.ingredientService::getCheeseByName)
                .collect(Collectors.toList()));
        pizzaServiceModel.setMeats(addPizzaBindingModel.getMeats()
                .stream()
                .map(this.ingredientService::getMeatByName)
                .collect(Collectors.toList()));
        pizzaServiceModel.setVegetables(addPizzaBindingModel.getVegetables()
                .stream()
                .map(this.ingredientService::getVegetableByName)
                .collect(Collectors.toList()));

        pizzaServiceModel.setDescription(String.format("%s sauce %s %s %s %s"
                , pizzaServiceModel.getSauce().getName()
                , pizzaServiceModel.getSpices()
                        .stream()
                        .map(SpiceServiceModel::getName)
                        .collect(Collectors.joining(" "))
                , pizzaServiceModel.getCheeses()
                        .stream()
                        .map(CheeseServiceModel::getName)
                        .collect(Collectors.joining(" "))
                , pizzaServiceModel.getMeats()
                        .stream()
                        .map(MeatServiceModel::getName)
                        .collect(Collectors.joining(" "))
                , pizzaServiceModel.getVegetables()
                        .stream()
                        .map(VegetableServiceModel::getName)
                        .collect(Collectors.joining(" "))

        ));

        for (CheeseServiceModel cheese : pizzaServiceModel.getCheeses()) {
            pizzaServiceModel.setPrice(pizzaServiceModel.getPrice().add(cheese.getPrice()));
        }

        for (MeatServiceModel meat : pizzaServiceModel.getMeats()) {
            pizzaServiceModel.setPrice(pizzaServiceModel.getPrice().add(meat.getPrice()));
        }

        for (VegetableServiceModel vegetable : pizzaServiceModel.getVegetables()) {
            pizzaServiceModel.setPrice(pizzaServiceModel.getPrice().add(vegetable.getPrice()));
        }

        return pizzaServiceModel;
    }
}

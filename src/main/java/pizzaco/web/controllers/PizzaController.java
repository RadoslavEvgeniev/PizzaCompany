package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.ingredients.*;
import pizzaco.domain.models.service.ingredients.*;
import pizzaco.errors.IngredientAddFailureException;
import pizzaco.service.IngredientService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class PizzaController extends BaseController {

    private final IngredientService ingredientService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public PizzaController(IngredientService ingredientService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.ingredientService = ingredientService;
        this.jmsTemplate = jmsTemplate;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/pizza/addPizzaIngredients")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addPizzaIngredients() {
        return super.view("ingredients/add-pizza-ingredients");
    }

    @PostMapping("/pizza/sizes/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addSize(@Valid @ModelAttribute(name = "addSizeBindingModel") AddSizeBindingModel addSizeBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        SizeServiceModel sizeServiceModel = this.modelMapper.map(addSizeBindingModel, SizeServiceModel.class);

        boolean result = this.ingredientService.addSize(sizeServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding size " + sizeServiceModel.getSize() + " failed.");
        }

        this.logAction(principal.getName(), "Added size " + sizeServiceModel.getSize() + " with price " + sizeServiceModel.getPrice() + "$ and " + sizeServiceModel.getNumberOfSlices() + " slices.");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/doughs/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDough(@Valid @ModelAttribute(name = "addDoughBindingModel") AddDoughBindingModel addDoughBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        DoughServiceModel doughServiceModel = this.modelMapper.map(addDoughBindingModel, DoughServiceModel.class);

        boolean result = this.ingredientService.addDough(doughServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding dough " + doughServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added dough " + doughServiceModel.getName() + ".");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/sauces/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addSauce(@Valid @ModelAttribute(name = "addSauceBindingModel") AddSauceBindingModel addSauceBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        SauceServiceModel sauceServiceModel = this.modelMapper.map(addSauceBindingModel, SauceServiceModel.class);

        boolean result = this.ingredientService.addSauce(sauceServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding sauce " + sauceServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added sauce " + sauceServiceModel.getName() + ".");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/spices/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addSpice(@Valid @ModelAttribute(name = "addSpiceBindingModel") AddSpiceBindingModel addSpiceBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        SpiceServiceModel spiceServiceModel = this.modelMapper.map(addSpiceBindingModel, SpiceServiceModel.class);

        boolean result = this.ingredientService.addSpice(spiceServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding spice " + spiceServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added spice " + spiceServiceModel.getName() + ".");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/cheeses/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCheese(@Valid @ModelAttribute(name = "addCheeseBindingModel") AddCheeseBindingModel addCheeseBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        CheeseServiceModel cheeseServiceModel = this.modelMapper.map(addCheeseBindingModel, CheeseServiceModel.class);

        boolean result = this.ingredientService.addCheese(cheeseServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding cheese " + cheeseServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added cheese " + cheeseServiceModel.getName() + " with price " + cheeseServiceModel.getPrice() + "$.");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/meats/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addMeat(@Valid @ModelAttribute(name = "addMeatBindingModel") AddMeatBindingModel addMeatBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        MeatServiceModel meatServiceModel = this.modelMapper.map(addMeatBindingModel, MeatServiceModel.class);

        boolean result = this.ingredientService.addMeat(meatServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding meat " + meatServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added meat " + meatServiceModel.getName() + " with price " + meatServiceModel.getPrice() + "$.");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/vegetables/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addVegetable(@Valid @ModelAttribute(name = "addVegetableBindingModel") AddVegetableBindingModel addVegetableBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("ingredients/add-pizza-ingredients");
        }

        VegetableServiceModel vegetableServiceModel = this.modelMapper.map(addVegetableBindingModel, VegetableServiceModel.class);

        boolean result = this.ingredientService.addVegetable(vegetableServiceModel);

        if (!result) {
            throw new IngredientAddFailureException("Adding vegetable " + vegetableServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added vegetable " + vegetableServiceModel.getName() + " with price " + vegetableServiceModel.getPrice() + "$.");

        return super.redirect("/pizza/addPizzaIngredients");
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }
}

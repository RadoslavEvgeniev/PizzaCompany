package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import pizzaco.service.PizzaService;

import javax.validation.Valid;

@Controller
public class PizzaController extends BaseController {

    private final PizzaService pizzaService;
    private final ModelMapper modelMapper;

    @Autowired
    public PizzaController(PizzaService pizzaService, ModelMapper modelMapper) {
        this.pizzaService = pizzaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/pizza/addPizzaIngredients")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addPizzaIngredients() {
        return super.view("pizza/add-pizza-ingredients");
    }

    @PostMapping("/pizza/sizes/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addSize(@Valid @ModelAttribute(name = "addSizeBindingModel") AddSizeBindingModel addSizeBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addSize(this.modelMapper.map(addSizeBindingModel, SizeServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding size " + addSizeBindingModel.getSize() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/doughs/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDough(@Valid @ModelAttribute(name = "addDoughBindingModel") AddDoughBindingModel addDoughBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addDough(this.modelMapper.map(addDoughBindingModel, DoughServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding dough " + addDoughBindingModel.getName() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/sauces/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addSauce(@Valid @ModelAttribute(name = "addSauceBindingModel") AddSauceBindingModel addSauceBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addSauce(this.modelMapper.map(addSauceBindingModel, SauceServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding sauce " + addSauceBindingModel.getName() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/spices/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addSpice(@Valid @ModelAttribute(name = "addSpiceBindingModel") AddSpiceBindingModel addSpiceBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addSpice(this.modelMapper.map(addSpiceBindingModel, SpiceServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding spice " + addSpiceBindingModel.getName() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/cheeses/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCheese(@Valid @ModelAttribute(name = "addCheeseBindingModel") AddCheeseBindingModel addCheeseBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addCheese(this.modelMapper.map(addCheeseBindingModel, CheeseServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding cheese " + addCheeseBindingModel.getName() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/meats/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addMeat(@Valid @ModelAttribute(name = "addMeatBindingModel") AddMeatBindingModel addMeatBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addMeat(this.modelMapper.map(addMeatBindingModel, MeatServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding meat " + addMeatBindingModel.getName() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }

    @PostMapping("/pizza/vegetables/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addVegetable(@Valid @ModelAttribute(name = "addVegetableBindingModel") AddVegetableBindingModel addVegetableBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("pizza/add-pizza-ingredients");
        }

        boolean result = this.pizzaService.addVegetable(this.modelMapper.map(addVegetableBindingModel, VegetableServiceModel.class));

        if (!result) {
            throw new IngredientAddFailureException("Adding vegetable " + addVegetableBindingModel.getName() + " failed.");
        }

        return super.redirect("/pizza/addPizzaIngredients");
    }
}

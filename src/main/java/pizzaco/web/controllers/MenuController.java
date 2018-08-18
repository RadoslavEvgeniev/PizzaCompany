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
import pizzaco.domain.models.binding.AddDrinkBindingModel;
import pizzaco.domain.models.service.DrinkServiceModel;
import pizzaco.service.MenuService;

import javax.validation.Valid;

@Controller
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuController(MenuService menuService, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/menu/addMenuItems")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addMenuItems() {
        return super.view("menu/add-menu-items");
    }

    @PostMapping("/menu/drinks/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDrink(@Valid @ModelAttribute(name = "addDrinkBindingModel")AddDrinkBindingModel addDrinkBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }
        this.menuService.addDrink(this.modelMapper.map(addDrinkBindingModel, DrinkServiceModel.class));

        return super.redirect("/menu");
    }
}

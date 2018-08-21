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
import pizzaco.domain.models.binding.menu.AddDipBindingModel;
import pizzaco.domain.models.binding.menu.AddDrinkBindingModel;
import pizzaco.domain.models.binding.menu.AddPastaBindingModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.errors.ItemAddFailureException;
import pizzaco.service.CloudinaryService;
import pizzaco.service.MenuService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final CloudinaryService cloudinaryService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuController(MenuService menuService, CloudinaryService cloudinaryService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.cloudinaryService = cloudinaryService;
        this.jmsTemplate = jmsTemplate;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/menu/addMenuItems")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addMenuItems() {
        return super.view("menu/add-menu-items");
    }

    @PostMapping("/menu/drinks/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDrink(@Valid @ModelAttribute(name = "addDrinkBindingModel")AddDrinkBindingModel addDrinkBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addDrinkBindingModel.getDrinkImage());

        DrinkServiceModel drinkServiceModel = this.modelMapper.map(addDrinkBindingModel, DrinkServiceModel.class);
        drinkServiceModel.setImageUrl(imageUrl);

        if (!this.menuService.addDrink(drinkServiceModel)) {
            throw new ItemAddFailureException("Adding drink " + drinkServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added drink " + drinkServiceModel.getName() + " with price " + drinkServiceModel.getPrice() + "$.");

        return super.redirect("/menu/addMenuItems");
    }

    @PostMapping("/menu/dips/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addDip(@Valid @ModelAttribute(name = "addDipBindingModel") AddDipBindingModel addDipBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addDipBindingModel.getDipImage());

        DipServiceModel dipServiceModel = this.modelMapper.map(addDipBindingModel, DipServiceModel.class);
        dipServiceModel.setImageUrl(imageUrl);

        if (!this.menuService.addDip(dipServiceModel)) {
            throw new ItemAddFailureException("Adding dip " + dipServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added dip " + dipServiceModel.getName() + " with price " + dipServiceModel.getPrice() + "$.");

        return super.redirect("/menu/addMenuItems");
    }

    @PostMapping("/menu/pasta/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addPasta(@Valid @ModelAttribute(name = "addPastaBindingModel") AddPastaBindingModel addPastaBindingModel
            , BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return super.view("menu/add-menu-items");
        }

        String imageUrl = this.cloudinaryService.uploadImage(addPastaBindingModel.getPastaImage());

        PastaServiceModel pastaServiceModel = this.modelMapper.map(addPastaBindingModel, PastaServiceModel.class);
        pastaServiceModel.setImageUrl(imageUrl);

        if (!this.menuService.addPasta(pastaServiceModel)) {
            throw new ItemAddFailureException("Adding pasta " + pastaServiceModel.getName() + " failed.");
        }

        this.logAction(principal.getName(), "Added pasta " + pastaServiceModel.getName() + " with price " + pastaServiceModel.getPrice() + "$.");

        return super.redirect("/menu/addMenuItems");
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }
}

package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.UserRegisterBindingModel;
import pizzaco.domain.models.view.OfferViewModel;
import pizzaco.service.MenuService;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/")
public class HomeController extends BaseController {

    private final MenuService menuService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(MenuService menuService, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ModelAndView index(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        List<OfferViewModel> offerViewModels = this.menuService.getOffers()
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferViewModel.class))
                .collect(Collectors.toList());

        return super.view("index", "offers", offerViewModels);
    }
}

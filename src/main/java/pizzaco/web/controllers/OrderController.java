package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.order.OrderBindingModel;
import pizzaco.domain.models.view.AddressViewModel;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;
import pizzaco.domain.models.view.menu.PizzaViewModel;
import pizzaco.service.AddressService;
import pizzaco.service.MenuService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final MenuService menuService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(MenuService menuService, AddressService addressService, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView initOrder() {
        return super.view("order/order-pane");
    }

    @GetMapping(value = "/address", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<AddressViewModel> addresses(Principal principal) {
        return this.addressService.getUserAddressesOrderedByName(principal.getName())
                .stream()
                .map(address -> this.modelMapper.map(address, AddressViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/pizza", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<PizzaViewModel> pizza() {
        return this.menuService.getPizzaOrderedByName()
                .stream()
                .map(pizza -> this.modelMapper.map(pizza, PizzaViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/pasta", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<PastaViewModel> pasta() {
        return this.menuService.getPastaOrderedByName()
                .stream()
                .map(pasta -> this.modelMapper.map(pasta, PastaViewModel.class))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/pasta", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> addPastaToOrder(@RequestBody OrderBindingModel orderBindingModel) {


        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/dips", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<DipViewModel> dips() {
        return this.menuService.getDipsOrderedByName()
                .stream()
                .map(dip -> this.modelMapper.map(dip, DipViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/drinks", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<DrinkViewModel> drinks() {
        return this.menuService.getDrinksOrderedByName()
                .stream()
                .map(drink -> this.modelMapper.map(drink, DrinkViewModel.class))
                .collect(Collectors.toList());
    }
}

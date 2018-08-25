package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.order.OrderAddressBindingModel;
import pizzaco.domain.models.binding.order.OrderItemBindingModel;
import pizzaco.domain.models.service.OrderServiceModel;
import pizzaco.domain.models.view.AddressViewModel;
import pizzaco.domain.models.view.OrderViewModel;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;
import pizzaco.domain.models.view.menu.PizzaViewModel;
import pizzaco.service.AddressService;
import pizzaco.service.MenuService;
import pizzaco.service.OrderService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final MenuService menuService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(MenuService menuService, AddressService addressService, OrderService orderService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.addressService = addressService;
        this.orderService = orderService;
        this.jmsTemplate = jmsTemplate;
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

    @PostMapping(value = "/address", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> addAddressToOrder(@RequestBody OrderAddressBindingModel orderAddressBindingModel, Principal principal) {
        this.orderService
                .setOrderAddress(this.orderService.getUserUnfinishedOrder(principal.getName()), this.addressService.getAddressById(orderAddressBindingModel.getId()));

        return ResponseEntity.ok("success");
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
    public ResponseEntity<String> addPastaToOrder(@RequestBody OrderItemBindingModel orderItemBindingModel, Principal principal) {
        OrderServiceModel orderServiceModel = this.orderService.getUserUnfinishedOrder(principal.getName());

        if (orderItemBindingModel.getChecked()) {
            this.orderService.addPastaToOrder(orderServiceModel, this.menuService.getPastaByName(orderItemBindingModel.getName()));
        } else {
            this.orderService.removePastaFromOrder(orderServiceModel, this.menuService.getPastaByName(orderItemBindingModel.getName()));
        }


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

    @PostMapping(value = "/dips", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> addDipsToOrder(@RequestBody OrderItemBindingModel orderItemBindingModel, Principal principal) {
        OrderServiceModel orderServiceModel = this.orderService.getUserUnfinishedOrder(principal.getName());

        if (orderItemBindingModel.getChecked()) {
            this.orderService.addDipToOrder(orderServiceModel, this.menuService.getDipByName(orderItemBindingModel.getName()));
        } else {
            this.orderService.removeDipFromOrder(orderServiceModel, this.menuService.getDipByName(orderItemBindingModel.getName()));
        }

        return ResponseEntity.ok("success");
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

    @PostMapping(value = "/drinks", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> addDrinksToOrder(@RequestBody OrderItemBindingModel orderItemBindingModel, Principal principal) {
        OrderServiceModel orderServiceModel = this.orderService.getUserUnfinishedOrder(principal.getName());

        if (orderItemBindingModel.getChecked()) {
            this.orderService.addDrinkToOrder(orderServiceModel, this.menuService.getDrinkByName(orderItemBindingModel.getName()));
        } else {
            this.orderService.removeDrinkFromOrder(orderServiceModel, this.menuService.getDrinkByName(orderItemBindingModel.getName()));
        }

        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/order", produces = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public OrderViewModel order(Principal principal) {
        return this.modelMapper.map(this.orderService.getUserUnfinishedOrder(principal.getName()), OrderViewModel.class);
    }

    @PostMapping(value = "/order", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> orderConfirm(@RequestBody String body, Principal principal) {
        this.orderService.finishOrder(this.orderService.getOrderById(body.split("=")[1]));

        // TODO : Log

        return ResponseEntity.ok("success");
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }
}

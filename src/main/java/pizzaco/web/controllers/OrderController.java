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
import pizzaco.domain.models.binding.order.OrderPizzaBindingModel;
import pizzaco.domain.models.service.order.OrderServiceModel;
import pizzaco.domain.models.service.order.OrderedPizzaServiceModel;
import pizzaco.domain.models.view.AddressViewModel;
import pizzaco.domain.models.view.AllIngredientsViewModel;
import pizzaco.domain.models.view.order.OrderPizzaViewModel;
import pizzaco.domain.models.view.order.OrderViewModel;
import pizzaco.domain.models.view.ingredients.*;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;
import pizzaco.domain.models.view.menu.PizzaViewModel;
import pizzaco.service.AddressService;
import pizzaco.service.IngredientService;
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
    private final IngredientService ingredientService;
    private final OrderService orderService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(MenuService menuService, AddressService addressService, IngredientService ingredientService, OrderService orderService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.addressService = addressService;
        this.ingredientService = ingredientService;
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

    @GetMapping(value = "/prepare-pizza", produces = "application/json", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public OrderPizzaViewModel orderPizzaDetails(@RequestParam(name = "pizzaName") String pizzaName) {
        OrderPizzaViewModel orderPizzaViewModel = new OrderPizzaViewModel();
        orderPizzaViewModel.setPizza(this.modelMapper.map(this.menuService.getPizzaByName(pizzaName), PizzaViewModel.class));

        AllIngredientsViewModel allIngredientsViewModel = new AllIngredientsViewModel();
        this.prepareIngredientsViewModel(allIngredientsViewModel);
        orderPizzaViewModel.setIngredients(allIngredientsViewModel);

        return orderPizzaViewModel;
    }

    @PostMapping(value = "/pizza", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> orderPizzaConfirm(@RequestBody OrderPizzaBindingModel orderPizzaBindingModel, Principal principal) {
        OrderServiceModel orderServiceModel = this.orderService.getUserUnfinishedOrder(principal.getName());

        if (orderPizzaBindingModel.isAdded()) {
            this.orderService.addPizzaToOrder(orderServiceModel, this.preparePizzaServiceModel(orderPizzaBindingModel));
        } else {
            this.orderService.removePizzaFromOrder(orderServiceModel, this.modelMapper.map(orderPizzaBindingModel, OrderedPizzaServiceModel.class));
        }

        return ResponseEntity.ok("success");
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

        if (orderItemBindingModel.isAdded()) {
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

        if (orderItemBindingModel.isAdded()) {
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

        if (orderItemBindingModel.isAdded()) {
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
        OrderServiceModel orderServiceModel = this.orderService.getOrderById(body.split("&")[0].split("=")[1]);

        if (Boolean.parseBoolean(body.split("&")[1].split("=")[1])) {
            this.orderService.finishOrder(orderServiceModel);

            String message = String.format("Order no.%s for %s"
                    , orderServiceModel.getId(), String.valueOf(orderServiceModel.getTotalPrice()));

            this.logAction(principal.getName(), message);
        } else {
            this.orderService.cancelOrder(orderServiceModel);
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("/{id}")
    public ModelAndView orderOffer(@PathVariable(name = "id") String id, Principal principal) {
        OrderServiceModel orderServiceModel = this.orderService.getUserUnfinishedOrder(principal.getName());


        return super.redirect("/order");
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }

    private void prepareIngredientsViewModel(AllIngredientsViewModel allIngredientsViewModel) {
        allIngredientsViewModel
                .setSizes(
                        this.ingredientService.getSizesOrderedByNumberOfSlices()
                                .stream()
                                .map(size -> this.modelMapper.map(size, SizeViewModel.class))
                                .collect(Collectors.toList())
                );

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

    private OrderedPizzaServiceModel preparePizzaServiceModel(OrderPizzaBindingModel orderPizzaBindingModel) {
        OrderedPizzaServiceModel orderedPizzaServiceModel = this.modelMapper.map(orderPizzaBindingModel, OrderedPizzaServiceModel.class);

        orderedPizzaServiceModel.setSize(this.ingredientService.getSizeBySize(orderPizzaBindingModel.getSize()));
        orderedPizzaServiceModel.setDough(this.ingredientService.getDoughByName(orderPizzaBindingModel.getDough()));
        orderedPizzaServiceModel.setSauce(this.ingredientService.getSauceByName(orderPizzaBindingModel.getSauce()));
        orderedPizzaServiceModel.setSpices(
                orderPizzaBindingModel.getSpices()
                .stream()
                .map(this.ingredientService::getSpiceByName)
                .collect(Collectors.toList())
        );
        orderedPizzaServiceModel.setCheeses(
                orderPizzaBindingModel.getCheeses()
                .stream()
                .map(this.ingredientService::getCheeseByName)
                .collect(Collectors.toList())
        );
        orderedPizzaServiceModel.setVegetables(
                orderPizzaBindingModel.getVegetables()
                .stream()
                .map(this.ingredientService::getVegetableByName)
                .collect(Collectors.toList())
        );
        orderedPizzaServiceModel.setMeats(
                orderPizzaBindingModel.getMeats()
                .stream()
                .map(this.ingredientService::getMeatByName)
                .collect(Collectors.toList())
        );

        return orderedPizzaServiceModel;
    }
}

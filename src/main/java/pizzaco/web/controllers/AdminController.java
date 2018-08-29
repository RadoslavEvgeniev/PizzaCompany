package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.service.UserRoleServiceModel;
import pizzaco.domain.models.service.UserServiceModel;
import pizzaco.domain.models.service.order.OrderServiceModel;
import pizzaco.domain.models.view.AllUsersViewModel;
import pizzaco.domain.models.view.LogViewModel;
import pizzaco.domain.models.view.UserViewModel;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;
import pizzaco.domain.models.view.order.OrderViewModel;
import pizzaco.domain.models.view.order.OrderedPizzaViewModel;
import pizzaco.errors.UserEditFailureException;
import pizzaco.service.LogService;
import pizzaco.service.OrderService;
import pizzaco.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private final UserService userService;
    private final LogService logService;
    private final OrderService orderService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, LogService logService, OrderService orderService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.userService = userService;
        this.logService = logService;
        this.orderService = orderService;
        this.jmsTemplate = jmsTemplate;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profiles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allProfiles() {
        return super.view("users/all-profiles-user", "profiles",
                this.userService.extractAllUsersOrderedAlphabetically()
                .stream()
                .map(u -> {
                    AllUsersViewModel allUsersViewModel = this.modelMapper.map(u, AllUsersViewModel.class);
                    allUsersViewModel.setFullName(String.format("%s %s", u.getFirstName(), u.getLastName()));

                    return allUsersViewModel;
                })
                .collect(Collectors.toList()));
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView profile(@PathVariable(name = "id") String id) {
        UserServiceModel userServiceModel = this.userService.extractUserById(id);
        UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
        userViewModel.setRoles(userServiceModel.getAuthorities().stream().map(UserRoleServiceModel::getAuthority).collect(Collectors.toList()));

        return super.view("users/details-user", "userViewModel", userViewModel);
    }

    @PostMapping("/profile/roleEdit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String roleEditConfirm(@RequestBody String body, Principal principal) {
        String email = body.split("&")[0].split("=")[1].replace("%40", "@");
        String role = body.split("&")[1].split("=")[1];

        boolean result = this.userService.editUserRole(email, role);

        if (!result) {
            throw new UserEditFailureException("Editing user role" + email + " failed.");
        }

        this.logAction(principal.getName(), "Successfully changed " + email + " role to " + role);

        return "Success";
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView logs() {
        return super.view(
                "logs", "logViewModel",
                this.logService.getLogsOrderedByDate()
                        .stream()
                        .map(log -> this.modelMapper.map(log, LogViewModel.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/orders/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allOrders() {
        List<OrderServiceModel> orderServiceModels = this.orderService.getAllFinishedOrdersOrderedByDate();

        return super.view("order/orders-all", "orderViewModels", orderServiceModels
                .stream()
                .map(order -> this.modelMapper.map(order, OrderViewModel.class))
                .map(order -> {
                    order.setDescription(order.getPizzas()
                            .stream()
                            .map(OrderedPizzaViewModel::getDescription)
                            .collect(Collectors.joining(" "))
                            + " "
                            + order.getPastas()
                            .stream()
                            .map(PastaViewModel::getDescription)
                            .collect(Collectors.joining(" "))
                            + " "
                            + order.getDips()
                            .stream()
                            .map(DipViewModel::getName)
                            .collect(Collectors.joining(" "))
                            + " "
                            + order.getDrinks()
                            .stream()
                            .map(DrinkViewModel::getName)
                            .collect(Collectors.joining(" ")));

                    return order;
                })
                .collect(Collectors.toList()));
    }

    private void logAction(UserServiceModel userServiceModel, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), userServiceModel.getEmail(), event));
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }
}

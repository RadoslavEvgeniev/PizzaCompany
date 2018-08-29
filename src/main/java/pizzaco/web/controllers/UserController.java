package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.UserEditBindingModel;
import pizzaco.domain.models.binding.UserRegisterBindingModel;
import pizzaco.domain.models.service.order.OrderServiceModel;
import pizzaco.domain.models.service.UserServiceModel;
import pizzaco.domain.models.view.menu.DipViewModel;
import pizzaco.domain.models.view.menu.DrinkViewModel;
import pizzaco.domain.models.view.menu.PastaViewModel;
import pizzaco.domain.models.view.order.OrderViewModel;
import pizzaco.domain.models.view.order.OrderedPizzaViewModel;
import pizzaco.errors.UserEditFailureException;
import pizzaco.errors.UserRegisterFailureException;
import pizzaco.service.OrderService;
import pizzaco.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final OrderService orderService;
    private final JmsTemplate jmsTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, OrderService orderService, JmsTemplate jmsTemplate, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.orderService = orderService;
        this.jmsTemplate = jmsTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel
            , BindingResult bindingResult) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userRegisterBindingModel", "password", "Passwords don't match."));
        }

        if (bindingResult.hasErrors()) {
            return super.view("index", "userRegisterBindingModel", userRegisterBindingModel);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        if (!this.userService.registerUser(userServiceModel)) {
            throw new UserRegisterFailureException("Registering user " + userServiceModel.getEmail() + " failed.");
        }

        this.logAction(userServiceModel, "Registered successfully.");

        return super.redirect("/");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel, Principal principal) {
        userEditBindingModel = this.modelMapper.map(this.userService.extractUserByEmail(principal.getName()), UserEditBindingModel.class);

        return super.view("users/profile-user", "userEditBindingModel", userEditBindingModel);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@Valid @ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel, BindingResult bindingResult) {
        UserServiceModel userServiceModel = this.userService.extractUserByEmail(userEditBindingModel.getEmail());

        if (!this.bCryptPasswordEncoder.matches(userEditBindingModel.getPassword(), userServiceModel.getPassword())) {
            bindingResult.addError(new FieldError("userEditBindingModel", "password", "Incorrect password."));
        } else if (!userEditBindingModel.getNewPassword().equals(userEditBindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userEditBindingModel", "newPassword", "Passwords don't match."));
        }

        if (bindingResult.hasErrors()) {
            return super.view("users/profile-user", "userEditBindingModel", userEditBindingModel);
        }

        if (!userEditBindingModel.getNewPassword().equals("")) {
            userEditBindingModel.setPassword(userEditBindingModel.getNewPassword());
        }

        if (!this.userService.editUser(this.modelMapper.map(userEditBindingModel, UserServiceModel.class))) {
            throw new UserEditFailureException("Editing user " + userServiceModel.getEmail() + " failed.");
        }

        this.logAction(userServiceModel, "Edited profile successfully.");

        return super.redirect("/profiles/my");
    }

    @GetMapping("/orders/my")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myOrders(Principal principal) {
        List<OrderServiceModel> orderServiceModels = this.orderService.getUserFinishedOrdersOrderedByDate(principal.getName());

        return super.view("order/orders-user", "orderViewModels", orderServiceModels
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
                    order.getUser().setEmail(principal.getName());

                    return order;
                })
                .collect(Collectors.toList()));
    }

    private void logAction(UserServiceModel userServiceModel, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), userServiceModel.getEmail(), event));
    }
}

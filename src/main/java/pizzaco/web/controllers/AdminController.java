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
import pizzaco.domain.models.view.AllUsersViewModel;
import pizzaco.domain.models.view.LogViewModel;
import pizzaco.domain.models.view.UserViewModel;
import pizzaco.errors.UserEditFailureException;
import pizzaco.service.LogService;
import pizzaco.service.UserService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private final UserService userService;
    private final LogService logService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, LogService logService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.userService = userService;
        this.logService = logService;
        this.jmsTemplate = jmsTemplate;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profiles/all")
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

    @GetMapping("/profiles/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView profile(@PathVariable(name = "id") String id) {
        UserServiceModel userServiceModel = this.userService.extractUserById(id);
        UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
        userViewModel.setRoles(userServiceModel.getAuthorities().stream().map(UserRoleServiceModel::getAuthority).collect(Collectors.toList()));

        return super.view("users/details-user", "userViewModel", userViewModel);
    }

    @GetMapping("/profiles/roleEdit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String roleEditConfirm(@RequestParam(name = "email") String email, @RequestParam(name = "role") String role) {
        boolean result = this.userService.editUserRole(email, role);

        if (!result) {
            throw new UserEditFailureException("Editing user role" + email + " failed.");
        }



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

    private void logAction(UserServiceModel userServiceModel, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), userServiceModel.getEmail(), event));
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }
}

package pizzaco.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.AddEditAddressBindingModel;
import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.domain.models.service.UserServiceModel;
import pizzaco.domain.models.view.AddressViewModel;
import pizzaco.errors.UserEditFailureException;
import pizzaco.service.AddressService;
import pizzaco.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/addresses")
public class AddressController extends BaseController {

    private final UserService userService;
    private final AddressService addressService;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressController(UserService userService, AddressService addressService, JmsTemplate jmsTemplate, ModelMapper modelMapper) {
        this.userService = userService;
        this.addressService = addressService;
        this.jmsTemplate = jmsTemplate;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addresses(Principal principal) {
        UserServiceModel userServiceModel = this.userService.extractUserByEmail(principal.getName());

        return super.view("addresses/addresses-user", "addresses", userServiceModel.getAddresses()
                .stream()
                .map(address -> this.modelMapper.map(address, AddressViewModel.class))
                .sorted(Comparator.comparing(AddressViewModel::getName))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addAddress(@Valid @ModelAttribute(name = "addAddressBindingModel") AddEditAddressBindingModel addAddressBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            // TODO : see what to do if has errors
        }

        AddressServiceModel addressServiceModel = this.modelMapper.map(addAddressBindingModel, AddressServiceModel.class);
        addressServiceModel.setOwner(this.userService.extractUserByEmail(principal.getName()));

        boolean result = this.addressService.addAddress(addressServiceModel);

        if (!result) {
            throw new UserEditFailureException("Failed to add address.");
        }

        this.logAction(principal.getName()
                , String.format("Added address %s, %s %s"
                        , addAddressBindingModel.getMunicipality()
                        , addAddressBindingModel.getStreet()
                        , addAddressBindingModel.getNumber())
        );

        return super.redirect("/addresses");
    }

    @GetMapping(value = "/edit", produces = "application/json", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public AddressViewModel addressEdit(@RequestParam(name = "id") String id) {
        return this.modelMapper.map(this.addressService.getAddressById(id), AddressViewModel.class);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addressEditConfirm(@Valid @ModelAttribute(name = "editAddressBindingModel") AddEditAddressBindingModel editAddressBindingModel
            , BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            // TODO : see what to do if has errors
        }

        AddressServiceModel addressServiceModel = this.modelMapper.map(editAddressBindingModel, AddressServiceModel.class);
        addressServiceModel.setOwner(this.userService.extractUserByEmail(principal.getName()));

        boolean result = this.addressService.editAddress(addressServiceModel);

        if (!result) {
            throw new UserEditFailureException("Failed to edit address.");
        }

        this.logAction(principal.getName()
                , String.format("Edited address %s, %s %s"
                        , editAddressBindingModel.getMunicipality()
                        , editAddressBindingModel.getStreet()
                        , editAddressBindingModel.getNumber())
        );

        return super.redirect("/addresses");
    }

    private void logAction(String email, String event) {
        this.jmsTemplate.convertAndSend(String.format("%s;%s;%s", LocalDateTime.now(), email, event));
    }
}

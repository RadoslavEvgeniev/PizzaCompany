package pizzaco.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import pizzaco.domain.models.binding.UserRegisterBindingModel;

@Controller("/")
public class HomeController extends BaseController {

    @GetMapping("")
    public ModelAndView index(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        // TODO: List some top offers
        return super.view("index");
    }
}

package pizzaco.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController extends BaseController {

    @GetMapping("/menu/addMenuItems")
    public ModelAndView addMenuItems() {
        return super.view("menu/add-menu-items");
    }


}

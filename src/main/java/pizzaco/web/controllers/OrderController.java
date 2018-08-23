package pizzaco.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController extends BaseController {

    @PostMapping(value = "/order/pasta/add")
    public ResponseEntity<String> addPastaToOrder(@ModelAttribute(name = "name")String name) {

        return ResponseEntity.ok("success");
    }
}

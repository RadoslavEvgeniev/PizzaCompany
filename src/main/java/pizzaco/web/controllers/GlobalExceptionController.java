package pizzaco.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController extends BaseController {

//    @ExceptionHandler(RuntimeException.class)
//    public ModelAndView getException(RuntimeException re) {
//
//        return super.view("error-template", re.getClass().isAnnotationPresent(ResponseStatus.class)
//                ? re.getClass().getAnnotation(ResponseStatus.class).reason()
//                : "Something went wrong");
//    }
}

package pizzaco.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pizzaco.domain.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);
}

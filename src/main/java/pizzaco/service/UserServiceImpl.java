package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.User;
import pizzaco.domain.entities.UserRole;
import pizzaco.domain.models.service.UserServiceModel;
import pizzaco.errors.IdNotFoundException;
import pizzaco.repository.RoleRepository;
import pizzaco.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = this.userRepository.findByUsername(email).orElse(null);

        if (userDetails == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        return userDetails;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.seedRolesInDb();

        User userEntity = this.modelMapper.map(userServiceModel, User.class);
        userEntity.setPassword(this.bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setUsername(userServiceModel.getEmail());

        this.setUserRole(userEntity);

        this.userRepository.save(userEntity);
        return true;
    }

    @Override
    public UserServiceModel extractUserByEmail(String email) {
        User userEntity = this.userRepository.findByUsername(email).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userEntity, UserServiceModel.class);
        userServiceModel.setEmail(userEntity.getUsername());

        return userServiceModel;
    }

    @Override
    public UserServiceModel extractUserById(String id) {
        User userEntity = this.userRepository.findById(id).orElse(null);

        if (userEntity == null) {
            throw new IdNotFoundException("Wrong or non-existent id.");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userEntity, UserServiceModel.class);
        userServiceModel.setEmail(userEntity.getUsername());

        return userServiceModel;
    }

    @Override
    public boolean editUser(UserServiceModel userServiceModel) {
        User userEntity = this.userRepository.findByUsername(userServiceModel.getEmail()).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        userEntity = this.modelMapper.map(userServiceModel, User.class);
        userEntity.setId(userServiceModel.getId());
        userEntity.setUsername(userServiceModel.getEmail());
        userEntity.setPassword(this.bCryptPasswordEncoder.encode(userEntity.getPassword()));

        this.userRepository.save(userEntity);

        return true;
    }

    @Override
    public List<UserServiceModel> extractAllUsersOrderedAlphabetically() {
        List<User> userEntities = this.userRepository.findAllOrderedAlphabetically();

        List<UserServiceModel> userServiceModels = userEntities.stream()
                .map(u -> {
                    UserServiceModel userServiceModel = this.modelMapper.map(u, UserServiceModel.class);
                    userServiceModel.setEmail(u.getUsername());

                    return userServiceModel;
                }).collect(Collectors.toList());


        return userServiceModels;
    }

    @Override
    public boolean editUserRole(String email, String role) {
        User userEntity = this.userRepository.findByUsername(email).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        this.changeUserRole(userEntity, role);

        this.userRepository.save(userEntity);

        return true;
    }

    @Override
    public boolean addAddress(UserServiceModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);

        this.userRepository.save(userEntity);

        return true;
    }

    private void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new UserRole("ROLE_ROOT"));
            this.roleRepository.save(new UserRole("ROLE_ADMIN"));
            this.roleRepository.save(new UserRole("ROLE_MODERATOR"));
            this.roleRepository.save(new UserRole("ROLE_USER"));
        }
    }

    private void setUserRole(User userEntity) {
        if (this.userRepository.count() == 0) {
            userEntity.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        } else {
            UserRole roleUser = this.roleRepository.findByAuthority("ROLE_USER").orElse(null);

            if (roleUser == null) {
                throw new IllegalArgumentException("Non-existent role.");
            }

            userEntity.setAuthorities(new HashSet<>());
            userEntity.getAuthorities().add(roleUser);
        }
    }

    private void changeUserRole(User userEntity, String role) {
        userEntity.getAuthorities().clear();


        switch (role) {
            case "admin":
                userEntity.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN").orElse(null));
            case "moderator":
                userEntity.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR").orElse(null));
            case "user":
                userEntity.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER").orElse(null));
                break;
        }

    }
}

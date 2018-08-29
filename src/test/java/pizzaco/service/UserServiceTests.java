package pizzaco.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import pizzaco.domain.entities.User;
import pizzaco.domain.entities.UserRole;
import pizzaco.domain.models.service.UserServiceModel;
import pizzaco.errors.IdNotFoundException;
import pizzaco.repository.RoleRepository;
import pizzaco.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
public class UserServiceTests {

    private User userDummy;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void initUser() {
        Mockito.when(this.bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("hashedPass");
        this.userDummy = new User();
        userDummy.setId("123e4567-e89b-12d3-a456-426655440000");
        userDummy.setFirstName("Test");
        userDummy.setLastName("Testov");
        userDummy.setUsername("test@test.test");
        userDummy.setPassword(this.bCryptPasswordEncoder.encode("123"));
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetails() {
        Mockito.when(this.userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(this.userDummy));

        UserDetails userDetails = this.userService.loadUserByUsername(this.userDummy.getUsername());

        Assert.assertEquals("", this.userDummy.getUsername(), userDetails.getUsername());

        Mockito.verify(this.userRepository).findByUsername("test@test.test");
    }

    @Test
    public void registerUserShouldReturnTrue() {
        ModelMapper modelMapper = new ModelMapper();

        UserServiceModel userServiceModel = modelMapper.map(this.userDummy, UserServiceModel.class);
        userServiceModel.setEmail(this.userDummy.getUsername());

        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(this.userDummy);
        Mockito.when(this.userRepository.save(this.userDummy)).thenReturn(this.userDummy);

        boolean result = this.userService.registerUser(userServiceModel);

        Assert.assertTrue(result);
    }

    @Test
    public void loadUserByUsernameShouldReturnUser() {
        ModelMapper modelMapper = new ModelMapper();

        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(this.userDummy, UserServiceModel.class));
        Mockito.when(this.userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(this.userDummy));

        UserServiceModel userServiceModel = this.userService.extractUserByEmail("test@test.test");

        Assert.assertEquals("", this.userDummy.getUsername(), userServiceModel.getEmail());

        Mockito.verify(this.userRepository).findByUsername("test@test.test");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameShouldThrowException() {
        ModelMapper modelMapper = new ModelMapper();
        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(this.userDummy, UserServiceModel.class));

        this.userService.extractUserByEmail("test@test.test");

        Mockito.verify(this.userRepository).findByUsername("test@test.test");
    }

    @Test
    public void loadUserByIdShouldReturnUser() {
        ModelMapper modelMapper = new ModelMapper();
        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(this.userDummy, UserServiceModel.class));
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(this.userDummy));

        UserServiceModel userServiceModel = this.userService.extractUserById("123e4567-e89b-12d3-a456-426655440000");

        Assert.assertEquals("", "123e4567-e89b-12d3-a456-426655440000", userServiceModel.getId());

        Mockito.verify(this.userRepository).findById("123e4567-e89b-12d3-a456-426655440000");
    }

    @Test(expected = IdNotFoundException.class)
    public void loadUserByIdShouldThrowException() {
        ModelMapper modelMapper = new ModelMapper();
        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(this.userDummy, UserServiceModel.class));

        this.userService.extractUserById("123e4567-e89b-12d3-a456-426655440000");

        Mockito.verify(this.userRepository).findById("123e4567-e89b-12d3-a456-426655440000");
    }

    @Test
    public void editUserShouldReturnTrue() {
        ModelMapper modelMapper = new ModelMapper();

        UserServiceModel userServiceModel = modelMapper.map(this.userDummy, UserServiceModel.class);

        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(userServiceModel, User.class));
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(this.userDummy));


        userServiceModel.setEmail(this.userDummy.getUsername());

        boolean result = this.userService.editUser(userServiceModel);

        Assert.assertTrue("", result);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void editNonExistentUserShouldThrowException() {
        ModelMapper modelMapper = new ModelMapper();

        UserServiceModel userServiceModel = modelMapper.map(this.userDummy, UserServiceModel.class);

        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(this.userDummy, UserServiceModel.class));

        this.userService.editUser(userServiceModel);

        Mockito.verify(this.userRepository).findByUsername("test@test.test");
    }

    @Test
    public void extractingAllUsersShouldReturnSortedAlphabetically() {
        User user = new User();
        user.setFirstName("Pesho");
        user.setLastName("Peshev");

        List<User> users = new ArrayList<>();
        users.add(this.userDummy);
        users.add(user);

//        Mockito.when(this.userRepository.findAllOrderedAlphabetically()).thenReturn(users);
//        Mockito.when(this.modelMapper.map(this.userDummy, UserServiceModel.class)).thenReturn(modelMapper.map(this.userDummy, UserServiceModel.class));
//
//        List<UserServiceModel> result = this.userService.extractAllUsersOrderedAlphabetically();
    }

    @Test
    public void editUserRoleShouldReturnTrue() {
        ModelMapper modelMapper = new ModelMapper();

        UserServiceModel userServiceModel = modelMapper.map(this.userDummy, UserServiceModel.class);

        Mockito.when(this.modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(modelMapper.map(userServiceModel, User.class));
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(this.userDummy));
        Mockito.when(this.roleRepository.findByAuthority(Mockito.anyString())).thenReturn(Optional.of(new UserRole("ROLE_TEST")));

        userServiceModel.setEmail(this.userDummy.getUsername());

        boolean result = this.userService.editUserRole(userServiceModel.getEmail(), "test");

        Assert.assertTrue("", result);
    }
}

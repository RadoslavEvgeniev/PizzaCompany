package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.Order;
import pizzaco.domain.entities.User;
import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.domain.models.service.OrderServiceModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.menu.PizzaServiceModel;
import pizzaco.errors.IdNotFoundException;
import pizzaco.repository.OrderRepository;
import pizzaco.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel getUserUnfinishedOrder(String email) {
        Order orderEntity = this.orderRepository.findByUserUsernameAndFinished(email, false).orElse(null);
        OrderServiceModel orderServiceModel;

        if (orderEntity == null) {
            orderServiceModel = this.startOrder(email);
        } else {
            orderServiceModel = this.modelMapper.map(orderEntity, OrderServiceModel.class);
        }

        return orderServiceModel;
    }

    @Override
    public OrderServiceModel getOrderById(String id) {
        Order orderEntity = this.orderRepository.findById(id).orElse(null);

        if (orderEntity == null) {
            throw new IdNotFoundException("Wrong or non-existent id.");
        }

        return this.modelMapper.map(orderEntity, OrderServiceModel.class);
    }

    @Override
    public boolean setOrderAddress(OrderServiceModel orderServiceModel, AddressServiceModel addressServiceModel) {
        orderServiceModel.setAddress(addressServiceModel);

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean addPizzaToOrder(OrderServiceModel orderServiceModel, PizzaServiceModel pizzaServiceModel) {
        return false;
    }

    @Override
    public boolean removePizzaFromOrder(OrderServiceModel orderServiceModel, PizzaServiceModel pizzaServiceModel) {
        return false;
    }

    @Override
    public boolean addPastaToOrder(OrderServiceModel orderServiceModel, PastaServiceModel pastaServiceModel) {
        orderServiceModel.getPastas().add(pastaServiceModel);

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean removePastaFromOrder(OrderServiceModel orderServiceModel, PastaServiceModel pastaServiceModel) {
        orderServiceModel.setPastas(
                orderServiceModel.getPastas()
                .stream()
                .filter(pasta -> !pasta.getName().equals(pastaServiceModel.getName()))
                .collect(Collectors.toList())
        );

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean addDipToOrder(OrderServiceModel orderServiceModel, DipServiceModel dipServiceModel) {
        orderServiceModel.getDips().add(dipServiceModel);

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean removeDipFromOrder(OrderServiceModel orderServiceModel, DipServiceModel dipServiceModel) {
        orderServiceModel.setDips(
                orderServiceModel.getDips()
                        .stream()
                        .filter(dip -> !dip.getName().equals(dipServiceModel.getName()))
                        .collect(Collectors.toList())
        );

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean addDrinkToOrder(OrderServiceModel orderServiceModel, DrinkServiceModel drinkServiceModel) {
        orderServiceModel.getDrinks().add(drinkServiceModel);

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean removeDrinkFromOrder(OrderServiceModel orderServiceModel, DrinkServiceModel drinkServiceModel) {
        orderServiceModel.setDrinks(
                orderServiceModel.getDrinks()
                        .stream()
                        .filter(drink -> !drink.getName().equals(drinkServiceModel.getName()))
                        .collect(Collectors.toList())
        );

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean finishOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setFinished(true);

        this.orderRepository.save(this.modelMapper.map(orderServiceModel, Order.class));

        return true;
    }

    private OrderServiceModel startOrder(String email) {
        User userEntity = this.userRepository.findByUsername(email).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        Order orderEntity = new Order();
        orderEntity.setUser(userEntity);

        this.orderRepository.save(orderEntity);

        return this.modelMapper.map(orderEntity, OrderServiceModel.class);
    }


}

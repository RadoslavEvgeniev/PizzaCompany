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

import java.util.ArrayList;
import java.util.List;
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
        boolean isFound = false;
        List<PastaServiceModel> pastaServiceModels = new ArrayList<>();

        for (PastaServiceModel pasta : orderServiceModel.getPastas()) {
            if (pasta.getName().equals(pastaServiceModel.getName()) && !isFound) {
                isFound = true;
            } else {
                pastaServiceModels.add(pasta);
            }
        }

        orderServiceModel.setPastas(pastaServiceModels);

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
        boolean isFound = false;
        List<DipServiceModel> dipServiceModels = new ArrayList<>();

        for (DipServiceModel dip : orderServiceModel.getDips()) {
            if (dip.getName().equals(dipServiceModel.getName()) && !isFound) {
                isFound = true;
            } else {
                dipServiceModels.add(dip);
            }
        }

        orderServiceModel.setDips(dipServiceModels);

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
        boolean isFound = false;
        List<DrinkServiceModel> drinkServiceModels = new ArrayList<>();

        for (DrinkServiceModel drink : orderServiceModel.getDrinks()) {
            if (drink.getName().equals(drinkServiceModel.getName()) && !isFound) {
                isFound = true;
            } else {
                drinkServiceModels.add(drink);
            }
        }
        orderServiceModel.setDrinks(drinkServiceModels);

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

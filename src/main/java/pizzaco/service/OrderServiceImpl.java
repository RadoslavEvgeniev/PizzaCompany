package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pizzaco.common.Constants;
import pizzaco.domain.entities.Order;
import pizzaco.domain.entities.User;
import pizzaco.domain.entities.order.OrderedPizza;
import pizzaco.domain.models.service.AddressServiceModel;
import pizzaco.domain.models.service.order.OrderServiceModel;
import pizzaco.domain.models.service.menu.DipServiceModel;
import pizzaco.domain.models.service.menu.DrinkServiceModel;
import pizzaco.domain.models.service.menu.PastaServiceModel;
import pizzaco.domain.models.service.order.OrderedPizzaServiceModel;
import pizzaco.errors.IdNotFoundException;
import pizzaco.repository.OfferRepository;
import pizzaco.repository.OrderRepository;
import pizzaco.repository.OrderedPizzaRepository;
import pizzaco.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderedPizzaRepository orderedPizzaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, OrderedPizzaRepository orderedPizzaRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderedPizzaRepository = orderedPizzaRepository;
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

        this.checkOrderExistence(orderEntity);

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
    public boolean addPizzaToOrder(OrderServiceModel orderServiceModel, OrderedPizzaServiceModel orderedPizzaServiceModel) {
        OrderedPizza orderedPizzaEntity = this.orderedPizzaRepository
                .findByDescription(orderedPizzaServiceModel.getDescription())
                .orElse(null);

        if (orderedPizzaEntity == null) {
            orderedPizzaEntity = this.modelMapper.map(orderedPizzaServiceModel, OrderedPizza.class);
            this.orderedPizzaRepository.save(orderedPizzaEntity);
        }

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        orderEntity.getPizzas().add(this.orderedPizzaRepository.findByDescription(orderedPizzaEntity.getDescription()).orElse(null));

        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean removePizzaFromOrder(OrderServiceModel orderServiceModel, OrderedPizzaServiceModel orderedPizzaServiceModel) {
        boolean isFound = false;
        List<OrderedPizzaServiceModel> pizzaServiceModels = new ArrayList<>();

        for (OrderedPizzaServiceModel pizza : orderServiceModel.getPizzas()) {
            if (pizza.getDescription().equals(orderedPizzaServiceModel.getDescription()) && !isFound) {
                isFound = true;
            } else {
                pizzaServiceModels.add(pizza);
            }
        }

        orderServiceModel.setPizzas(pizzaServiceModels);

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(orderEntity);

        return true;
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
        orderServiceModel.setFinishDateTime(LocalDateTime.now());
        this.calculateOrderTotalPrice(orderServiceModel);

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);
        this.orderRepository.save(orderEntity);

        return true;
    }

    @Override
    public boolean cancelOrder(OrderServiceModel orderServiceModel) {
        this.orderRepository.delete(this.modelMapper.map(orderServiceModel, Order.class));

        return true;
    }

    @Override
    public List<OrderServiceModel> getUserFinishedOrdersOrderedByDate(String email) {
        return this.orderRepository.findAllByUserUsernameAndFinishedOrderByFinishDateTimeDesc(email, true)
                .stream()
                .map(order -> this.modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> getAllFinishedOrdersOrderedByDate() {
        return this.orderRepository.findAllOrderedByFinishDateDesc()
                .stream()
                .map(order -> this.modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    private OrderServiceModel startOrder(String email) {
        return this.modelMapper.map(this.prepareOrderEntity(email), OrderServiceModel.class);
    }

    private Order prepareOrderEntity(String email) {
        User userEntity = this.userRepository.findByUsername(email).orElse(null);

        this.checkUserExistence(userEntity);

        Order orderEntity = new Order();
        orderEntity.setUser(userEntity);

        this.orderRepository.save(orderEntity);

        return orderEntity;
    }

    private void checkUserExistence(User userEntity) {
        if (userEntity == null) {
            throw new UsernameNotFoundException(Constants.WRONG_NON_EXISTENT_EMAIL);
        }
    }

    private void checkOrderExistence(Order orderEntity) {
        if (orderEntity == null) {
            throw new IdNotFoundException(Constants.WRONG_NON_EXISTENT_ID);
        }
    }

    private void calculateOrderTotalPrice(OrderServiceModel orderServiceModel) {
        orderServiceModel.setTotalPrice(BigDecimal.ZERO);
        for (OrderedPizzaServiceModel pizza : orderServiceModel.getPizzas()) {
            orderServiceModel.setTotalPrice(orderServiceModel.getTotalPrice().add(pizza.getPrice()));
        }

        for (PastaServiceModel pasta : orderServiceModel.getPastas()) {
            orderServiceModel.setTotalPrice(orderServiceModel.getTotalPrice().add(pasta.getPrice()));
        }

        for (DipServiceModel dip : orderServiceModel.getDips()) {
            orderServiceModel.setTotalPrice(orderServiceModel.getTotalPrice().add(dip.getPrice()));
        }

        for (DrinkServiceModel drink : orderServiceModel.getDrinks()) {
            orderServiceModel.setTotalPrice(orderServiceModel.getTotalPrice().add(drink.getPrice()));
        }
    }
}

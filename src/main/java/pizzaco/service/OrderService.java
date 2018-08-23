package pizzaco.service;

import pizzaco.domain.models.service.OrderServiceModel;

public interface OrderService {

    boolean startOrder(OrderServiceModel orderServiceModel);

    boolean finishOrder(OrderServiceModel orderServiceModel);

    boolean deleteAllOrder();
}

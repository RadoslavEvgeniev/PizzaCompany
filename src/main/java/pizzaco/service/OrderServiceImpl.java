package pizzaco.service;

import org.springframework.stereotype.Service;
import pizzaco.domain.models.service.OrderServiceModel;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public boolean startOrder(OrderServiceModel orderServiceModel) {
        return false;
    }

    @Override
    public boolean finishOrder(OrderServiceModel orderServiceModel) {
        return false;
    }

    @Override
    public boolean deleteAllOrder() {
        return false;
    }
}

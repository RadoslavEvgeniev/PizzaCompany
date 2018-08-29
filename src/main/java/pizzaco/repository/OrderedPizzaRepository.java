package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.order.OrderedPizza;

import java.util.Optional;

@Repository
public interface OrderedPizzaRepository extends JpaRepository<OrderedPizza, String> {

    Optional<OrderedPizza> findByDescription(String description);
}

package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByUserUsernameAndFinished(String email, boolean isFinished);

    List<Order> findAllByUserUsernameAndFinishedOrderByFinishDateTimeDesc(String email, boolean isFinished);

    @Query("SELECT o FROM Order o ORDER BY o.finishDateTime DESC ")
    List<Order> findAllOrderedByFinishDateDesc();
}

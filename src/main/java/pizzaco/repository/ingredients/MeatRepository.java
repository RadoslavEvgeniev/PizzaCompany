package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Meat;

import java.util.Optional;

@Repository
public interface MeatRepository extends JpaRepository<Meat, String> {

    Optional<Meat> findByName(String name);
}

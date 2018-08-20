package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Vegetable;

import java.util.Optional;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable, String> {

    Optional<Vegetable> findByName(String name);
}

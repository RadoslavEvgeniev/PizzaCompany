package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Cheese;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheeseRepository extends JpaRepository<Cheese, String> {

    Optional<Cheese> findByName(String name);

    @Query("SELECT c FROM Cheese c ORDER BY c.name")
    List<Cheese> findAllOrderedAlphabetically();
}

package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Dough;

import java.util.Optional;

@Repository
public interface DoughRepository extends JpaRepository<Dough, String> {

    Optional<Dough> findByName(String name);
}

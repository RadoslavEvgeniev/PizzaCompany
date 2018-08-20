package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Sauce;

import java.util.Optional;

@Repository
public interface SauceRepository extends JpaRepository<Sauce, String> {

    Optional<Sauce> findByName(String name);
}

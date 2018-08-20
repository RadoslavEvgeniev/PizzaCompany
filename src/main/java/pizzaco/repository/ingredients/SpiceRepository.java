package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Spice;

import java.util.Optional;

@Repository
public interface SpiceRepository extends JpaRepository<Spice, String> {

    Optional<Spice> findByName(String name);
}

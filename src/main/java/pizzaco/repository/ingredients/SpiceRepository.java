package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.pizza.Spice;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpiceRepository extends JpaRepository<Spice, String> {

    Optional<Spice> findByName(String name);

    @Query("SELECT s FROM Spice s ORDER BY s.name")
    List<Spice> findAllOrderedAlphabetically();
}

package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.ingredients.Sauce;

import java.util.List;
import java.util.Optional;

@Repository
public interface SauceRepository extends JpaRepository<Sauce, String> {

    Optional<Sauce> findByName(String name);

    @Query("SELECT s FROM Sauce s ORDER BY s.name")
    List<Sauce> findAllOrderedAlphabetically();
}

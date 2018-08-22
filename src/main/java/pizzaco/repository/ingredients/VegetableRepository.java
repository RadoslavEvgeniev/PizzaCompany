package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.ingredients.Vegetable;

import java.util.List;
import java.util.Optional;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable, String> {

    Optional<Vegetable> findByName(String name);

    @Query("SELECT v FROM Vegetable v ORDER BY v.name")
    List<Vegetable> findAllOrderedAlphabetically();
}

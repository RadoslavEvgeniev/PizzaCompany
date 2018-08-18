package pizzaco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.menu.Drink;

import java.util.Optional;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, String> {

    Optional<Drink> findByName(String name);
}

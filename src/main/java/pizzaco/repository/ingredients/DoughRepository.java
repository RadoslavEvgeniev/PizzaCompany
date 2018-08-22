package pizzaco.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pizzaco.domain.entities.ingredients.Dough;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoughRepository extends JpaRepository<Dough, String> {

    Optional<Dough> findByName(String name);

    @Query("SELECT d from Dough d order by d.name")
    List<Dough> findAllOrderedAlphabetically();
}
